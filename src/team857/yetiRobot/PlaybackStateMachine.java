package team857.yetiRobot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;

public class PlaybackStateMachine extends ArrayList<PlaybackStateMachine.State> implements PeriodController {
	private static final long serialVersionUID = -566236115018531520L;//stop complaining eclipse
	public interface FileSelector {
		public File getFile();
	}
	public interface PlaybackController {
		public void init(PlaybackStateMachine machine);
		public void run(double time);
		public void end();
	}
	public class State {
		protected ByteBuffer data = ByteBuffer.allocate(92);
		State(double time, byte[][] axes, short[] buttons){
			byte c = 0;
			data.putDouble(time).put(axes[c++]).put(axes[c++]).put(axes[c++]).put(axes[c++]).put(axes[c++]).put(axes[c]);
			c = 0;
			data.putShort(buttons[c]).putShort(buttons[c]).putShort(buttons[c]).putShort(buttons[c]).putShort(buttons[c]).putShort(buttons[c]);
		}
		State(byte[] bytes) throws IOException {
			if(bytes.length!=92) throw new IOException("Incorrect byte count");
			data.put(bytes);
		}
		public boolean equals(State state){
			return state.getBuf().equals(data);
		}
		ByteBuffer getBuf(){
			return data;
		}
		byte[] getBytes(){
			return data.array();
		}
		double getAxis(byte stick, byte axis){
			byte value = data.get(8+stick*12+axis);
			return value / ((value<0)?128.0:127.0);
		}
		boolean getButton(byte stick, int button){
			return ((data.getShort(80+stick)>>(button-1))&1)==1;
		}
		double getTime(){
			return data.getLong(0);
		}
		public Joystick asJoystick(int port){
			return ((Joystick) this).setPort(port);
		}
		public Controller asController(int port){
			return ((Controller) this).setPort(port);
		}
	}
	public abstract class Joystick extends State {
		private byte port;
		private Joystick(byte[] bytes) throws IOException {super(bytes);}
		public Joystick setPort(int port){
			this.port = (byte) port;
			return this;
		}
		public double getAxis(int axis){return super.getAxis(port, (byte) axis);}
		public boolean getButton(int button){return super.getButton(port,(byte)button);}
		
		public double getX(){return getAxis(0);}
		public double getY(){return getAxis(1);}
		public double getZ(){return getAxis(2);}
		public double getTwist(){return getAxis(3);}
		public double getThrottle(){return getAxis(4);}
		public double getMagnitude(){return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));}
		public double getDirectionRadians(){return Math.atan2(getX(), -getY());}
		public double getDirectionDegrees(){return Math.toDegrees(getDirectionRadians());}
		
		public boolean getTrigger(){return getButton(0);}
		public boolean getTop(){return getButton(1);}
	}
	public abstract class Controller extends State {
		private byte port;
		private Controller(byte[] bytes) throws IOException {super(bytes);}
		public Controller setPort(int port){
			this.port = (byte) port;
			return this;
		}
		public double getAxis(int axis){return super.getAxis(port, (byte) axis);}
		public boolean getButton(int button){return super.getButton(port,(byte)button);}
		
		public double getLeftAxisX(){return getAxis(0);}
		public double getLeftAxisY(){return getAxis(1);}
		public double getLeftTrigger(){return getAxis(2);}
		public double getRightTrigger(){return getAxis(3);}
		public double getRightAxisX(){return getAxis(4);}
		public double getRightAxisY(){return getAxis(5);}
		
		public boolean getButtonA(){return getButton(1);}
		public boolean getButtonB(){return getButton(2);}
		public boolean getButtonX(){return getButton(3);}
		public boolean getButtonY(){return getButton(4);}
		public boolean getLeftBumper(){return getButton(5);}
		public boolean getRightBumper(){return getButton(6);}
		public boolean getBack(){return getButton(7);}
		public boolean getStart(){return getButton(8);}
		public boolean getLeftStickDown(){return getButton(9);}
		public boolean getRightStickDown(){return getButton(10);}
	}
	
	private final boolean isRecording;
	private final PeriodController controller;
	private final PlaybackController controllerp;
	private final DriverStation ds = DriverStation.getInstance();
	private boolean haveLoaded = false;
	private double nextTime = 0;
	private State currentState = null;
	private int nextIndex = 1;
	private Field axes = null;
	private FileSelector file;
	
	public PlaybackStateMachine(PeriodController controller, FileSelector selector){
		isRecording = true;
		this.controller = controller;
		controllerp = null;
		file = selector;
	}
	
	public PlaybackStateMachine(PlaybackController controller, FileSelector selector){
		isRecording = false;
		this.controller = null;
		controllerp = controller;
		file = selector;
	}
	
	public State getCurrentState(double time){
		return currentState;
	}
	
	public PlaybackStateMachine load() throws IOException {
		return loadFile(file.getFile());
	}
	
	public PlaybackStateMachine loadFile(File input) throws IOException {
		if(isRecording) throw new RuntimeException("Am recording. Cannot load a file.");
		clear();
		byte[] data = new byte[92];
		RandomAccessFile f = new RandomAccessFile(input, "r");
		MappedByteBuffer file = f.getChannel().map(MapMode.READ_ONLY, 0, input.length());
		while(file.remaining()>91){
			file.get(data);
			add(new State(data));
		}
		f.close();
		haveLoaded = true;
		return this;
	}
	
	private byte[][] getAxes(){
		byte[][] out = new byte[6][12];
		try {
			if(axes==null){
				axes = ds.getClass().getDeclaredField("m_joystickAxes");
				axes.setAccessible(true);
			}
			short[][] tmp = (short[][]) axes.get(ds);
			for(byte port = 0;port<DriverStation.kJoystickPorts;port++)
				for(byte axis = 0;axis<FRCNetworkCommunicationsLibrary.kMaxJoystickAxes;axis++)
					out[port][axis] = (byte) tmp[port][axis];
		} catch(Exception e){
			out = new byte[6][12];
		}
		return out;
	}
	
	public void init(){
		if(isRecording){
			add(new State(0,getAxes(), new short[]{
				(short) ds.getStickButtons(0),
				(short) ds.getStickButtons(1),
				(short) ds.getStickButtons(2),
				(short) ds.getStickButtons(3),
				(short) ds.getStickButtons(4),
				(short) ds.getStickButtons(5)
			}));
			controller.init();
		} else {
			if(!haveLoaded) throw new RuntimeException("Have not loaded file. Cannot playback.");
			nextIndex = 1;
			currentState = get(0);
			nextTime = get(nextIndex).getTime();
			controllerp.init(this);
		}
	}
	public void run(double time){
		if(isRecording){
			add(new State(time,getAxes(), new short[]{
				(short) ds.getStickButtons(0),
				(short) ds.getStickButtons(1),
				(short) ds.getStickButtons(2),
				(short) ds.getStickButtons(3),
				(short) ds.getStickButtons(4),
				(short) ds.getStickButtons(5)
			}));
		} else if(time>=nextTime){
			currentState = get(nextIndex);
			nextIndex++;
			nextTime = get(nextIndex).getTime();
		}
		controller.run(time);
	}
	public void end(){
		controller.end();
		if(isRecording){
			try {
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file.getFile()));
				for(State state: this){
					out.write(state.getBytes());
				}
				out.flush();
				out.close();
			} catch(IOException e) {
				DriverStation.reportError(e.getMessage(), false);
			}
		}
	}
}
