package team857.yetiRobot.record;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import team857.yetiRobot.PeriodController;

public class RecorderOld implements PeriodController {
	public static interface Selector {
		public boolean recordButton(int stick);
		public boolean recordAxes(int stick);
		public File getOutputFile();
	}
	private Selector selector;
	
	private BufferedOutputStream out = null;
	private PeriodController teleopController;
	private short[] buttons = new short[6];
	private byte[][] axis = new byte[6][12];
	
	private short tmp_short;
	private byte[] tmp_sha;
	
	private byte e_axis, e_buto;
	private ByteBuffer e_update = ByteBuffer.allocate(84).order(ByteOrder.BIG_ENDIAN),
						e_over = ByteBuffer.allocate(10).order(ByteOrder.BIG_ENDIAN);
	
	public RecorderOld(PeriodController teleopController, RecorderOld.Selector selector){
		this.teleopController = teleopController;
		this.selector = selector;
	}
	private byte[] m_stb(short[] array){
		byte[] x = new byte[array.length];
		for(byte c = 0; c<array.length;c++) x[c] = (byte)array[c];
		return x;
	}
	public void init(){
		axis[0] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)0));
		axis[1] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)1));
		axis[2] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)2));
		axis[3] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)3));
		axis[4] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)4));
		axis[5] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)5));
		buttons[0] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)0, ByteBuffer.allocateDirect(1));
		buttons[1] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)1, ByteBuffer.allocateDirect(1));
		buttons[2] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)2, ByteBuffer.allocateDirect(1));
		buttons[3] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)3, ByteBuffer.allocateDirect(1));
		buttons[4] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)4, ByteBuffer.allocateDirect(1));
		buttons[5] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)5, ByteBuffer.allocateDirect(1));
		try {
			out = new BufferedOutputStream(new FileOutputStream(selector.getOutputFile()));
		} catch (FileNotFoundException e){
			throw new RuntimeException("Output file can't be created");
		} finally {
			try {
				e_update.putShort(buttons[0]).putShort(buttons[1]).putShort(buttons[2])
					.putShort(buttons[3]).putShort(buttons[4]).putShort(buttons[5])
					.put(axis[0]).put(axis[1]).put(axis[2]).put(axis[3]).put(axis[4]).put(axis[5]);
				out.write(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, (byte)0xFF, (byte)0xFF});
				out.write(e_update.array());
				out.flush();
			} catch (IOException e){
				DriverStation.reportError(e.getMessage(), false);
			}
		}
		teleopController.init();
	}
	public void run(double time){
		teleopController.run(time);
		
		e_axis = 0;
		e_buto = 0;
		e_update.clear();
		e_over.clear();
		e_over.putLong((long)Math.floor(time*1000));
		if(selector.recordButton(0) && buttons[0]!=(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)0, ByteBuffer.allocateDirect(1)))){
			buttons[0] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 1;
		}
		if(selector.recordButton(1) && buttons[1]!=(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)1, ByteBuffer.allocateDirect(1)))){
			buttons[1] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 2;
		}
		if(selector.recordButton(2) && buttons[2]!=(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)2, ByteBuffer.allocateDirect(1)))){
			buttons[2] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 4;
		}
		if(selector.recordButton(3) && buttons[3]!=(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)3, ByteBuffer.allocateDirect(1)))){
			buttons[3] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 8;
		}
		if(selector.recordButton(4) && buttons[4]!=(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)4, ByteBuffer.allocateDirect(1)))){
			buttons[4] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 16;
		}
		if(selector.recordButton(5) && buttons[5]!=(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)5, ByteBuffer.allocateDirect(1)))){
			buttons[5] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 32;
		}
		if(selector.recordAxes(0) && !Arrays.equals(axis[0], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)0))))){
			axis[0] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 1;
		}
		if(selector.recordAxes(1) && !Arrays.equals(axis[1], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)1))))){
			axis[1] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 2;
		}
		if(selector.recordAxes(2) && !Arrays.equals(axis[2], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)2))))){
			axis[2] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 4;
		}
		if(selector.recordAxes(3) && !Arrays.equals(axis[3], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)3))))){
			axis[3] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 8;
		}
		if(selector.recordAxes(4) && !Arrays.equals(axis[4], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)4))))){
			axis[4] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 16;
		}
		if(selector.recordAxes(5) && !Arrays.equals(axis[5], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)5))))){
			axis[5] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 32;
		}
		if((e_axis > 0) || (e_buto > 0)){
			e_over.put(e_buto);
			e_over.put(e_axis);
			try {	
				out.write(e_over.array());
				out.write(Arrays.copyOfRange(e_update.array(),0,e_update.position()));
				out.flush();
			} catch(IOException e){
				DriverStation.reportError("Failed to write line input sequence", false);
			}
		}
	}
	public void end(){
		teleopController.end();
		try {
			out.flush();
			out.close();
		} catch(IOException e){
			throw new RuntimeException("Could not finish recording session.");
		}
	}
}
