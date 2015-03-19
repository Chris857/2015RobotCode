package team857.yetiRobot.record;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.DriverStation;
import team857.yetiRobot.PeriodController;

public class Playback implements PeriodController {
	private final int BUTTON_BLOCK_SIZE = 2;
	private final int AXIS_BLOCK_SIZE = 12;
	
	private final TreeMap<Long, byte[]> events = new TreeMap<Long, byte[]>();
	private PlaybackController controller;
	private final short[] buttons = new short[6];
	private final byte[][] sticks = new byte[6][12];
	private boolean DO_NOT_PROCEED = false;
	private double offsetTime = 0;
	
	public void setOffset(double time){
		offsetTime = time;
	}
	
	private Playback(String auton){
		BufferedInputStream file = null;
		byte[] overhead = new byte[10], data = new byte[84];
		int dataLength;
		ByteBuffer event = ByteBuffer.allocate(86);
		try {
			file = new BufferedInputStream(new FileInputStream(new File(System.getProperty("user.home"), auton+".rta")));
		} catch(Exception e){
			DriverStation.reportError(e.getMessage(),true);
			DO_NOT_PROCEED = true;
		} finally {
			try {
				if(!DO_NOT_PROCEED) while(file.read(overhead,0,10)==10){
					dataLength = 2*Integer.bitCount(overhead[8])+12*Integer.bitCount(overhead[9]);
					if(file.read(data,0,dataLength)!=dataLength) break;
					event.clear();
					event.put(overhead[8]).put(overhead[9]).put(data);
					events.put(m_bytesToLong(overhead), Arrays.copyOf(event.array(), event.position()));
				}
			} catch(IOException e){
				DriverStation.reportError(e.getMessage(),true);
				DO_NOT_PROCEED = true;
			}
		}
	}
	public static Playback create(String auton, PlaybackController controller){
		Playback play = new Playback(auton);
		play.setController(controller);
		return play;
	}
	private void setController(PlaybackController controller){
		this.controller = controller;
	}
	private static long m_bytesToLong(byte[] b){
		return b[7]+(b[6]<<8)+(b[5]<<16)+(b[4]<<24)+(b[3]<<32)+(b[2]<<40)+(b[1]<<48)+(b[0]<<56);
	}
	private static short m_bytesToShort(byte a, byte b){
		return (short) ((a<<8)+b);
	}
	
	public void init(){}
	public void end(){/*likewise here*/}
	public void run(double time){
		time-=offsetTime;
		if(DO_NOT_PROCEED || time<0) return;
		
		byte[] inputs = events.get(events.floorKey((long)Math.floor(time*1000)));
		int bc = 2, sc = 2; // offset
		if((inputs[0]&1)==1){
			buttons[0] = m_bytesToShort(inputs[bc], inputs[bc+1]);
			bc+=BUTTON_BLOCK_SIZE;
			sc+=BUTTON_BLOCK_SIZE;
		}
		if(((inputs[0]>>1)&1)==1){
			buttons[1] = m_bytesToShort(inputs[bc],inputs[bc+1]);
			bc+=BUTTON_BLOCK_SIZE;
			sc+=BUTTON_BLOCK_SIZE;
		}
		if(((inputs[0]>>2)&1)==1){
			buttons[2] = m_bytesToShort(inputs[bc],inputs[bc+1]);
			bc+=BUTTON_BLOCK_SIZE;
			sc+=BUTTON_BLOCK_SIZE;
		}
		if(((inputs[0]>>3)&1)==1){
			buttons[3] = m_bytesToShort(inputs[bc],inputs[bc+1]);
			bc+=BUTTON_BLOCK_SIZE;
			sc+=BUTTON_BLOCK_SIZE;
		}
		if(((inputs[0]>>4)&1)==1){
			buttons[4] = m_bytesToShort(inputs[bc],inputs[bc+1]);
			bc+=BUTTON_BLOCK_SIZE;
			sc+=BUTTON_BLOCK_SIZE;
		}
		if(((inputs[0]>>5)&1)==1){
			buttons[5] = m_bytesToShort(inputs[bc],inputs[bc+1]);
			sc+=BUTTON_BLOCK_SIZE;//no more buttons. no need to increment button offset
		}
		if((inputs[1]&1)==1){
			sticks[0] = Arrays.copyOfRange(inputs, sc, sc+AXIS_BLOCK_SIZE);
			sc+=AXIS_BLOCK_SIZE;
		}
		if(((inputs[1]>>1)&1)==1){
			sticks[1] = Arrays.copyOfRange(inputs, sc, sc+AXIS_BLOCK_SIZE);
			sc+=AXIS_BLOCK_SIZE;
		}
		if(((inputs[1]>>2)&1)==1){
			sticks[2] = Arrays.copyOfRange(inputs, sc, sc+AXIS_BLOCK_SIZE);
			sc+=AXIS_BLOCK_SIZE;
		}
		if(((inputs[1]>>3)&1)==1){
			sticks[3] = Arrays.copyOfRange(inputs, sc, sc+AXIS_BLOCK_SIZE);
			sc+=AXIS_BLOCK_SIZE;
		}
		if(((inputs[4]>>1)&1)==1){
			sticks[4] = Arrays.copyOfRange(inputs, sc, sc+AXIS_BLOCK_SIZE);
			sc+=AXIS_BLOCK_SIZE;
		}
		if(((inputs[1]>>5)&1)==1){
			sticks[5] = Arrays.copyOfRange(inputs, sc, sc+AXIS_BLOCK_SIZE);
			// all done
		}
		
		// and finally...
		controller.run();
	}
	
	public short getButtons(int stick){
		if(stick<0 || stick>5) throw new RuntimeException("Joystick index is out of range, should be 0-5");
		return buttons[stick];
	}
	public boolean getButton(int stick,int button){
		if(stick<0 || stick>5) throw new RuntimeException("Joystick index is out of range, should be 0-5");
		if(button<=0 || button>16) return false;
		return ((0x1 << (button - 1)) & buttons[stick]) != 0;
	}

	public double getAxis(int stick, int axis){
		if(stick<0 || stick>5) throw new RuntimeException("Joystick index is out of range, should be 0-5");
		if(axis<0 || axis>11) throw new RuntimeException("Joystick axis is out of range");
		byte value = (byte)sticks[stick][axis];
		if(value<0) return value / 128.0; else return value/127.0;
	}
	public double getX(int stick){
		return getAxis(stick,0);
	}
	public double getY(int stick){
		return getAxis(stick,1);
	}
}
