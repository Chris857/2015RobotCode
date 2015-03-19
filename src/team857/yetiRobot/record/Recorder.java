package team857.yetiRobot.record;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import team857.yetiRobot.PeriodController;

public class Recorder implements PeriodController {
	private short[] m_jButtons = new short[6];
	private byte[][] m_jAxis = new byte[6][12];
	private FileOutputStream out = null;
	private RecordController teleopController;
	
	private byte e_axis, e_buto; // keeps track of changes
	private ByteBuffer e_update = ByteBuffer.allocate(84).order(ByteOrder.BIG_ENDIAN), // changed stuff.
			e_over = ByteBuffer.allocate(10); // quick stuff for time and overhead (changes)
	
	private short tmp_short;
	private byte[] tmp_sha;
	
	private String outputFile;
	
	public Recorder(RecordController teleopController){
		this.teleopController = teleopController;
		try {
			out = new FileOutputStream(new File(System.getProperty("user.home"),"out.rta"));
		} catch(Exception e){
			//DriverStation.reportError("Could not instantiate output in recorder", false);
			throw new RuntimeException("Could not instantiate output in recorder");
		} finally {
			try {
				//puts new message, time zero (8 bytes), all changed (2 bytes), all zero (84 bytes)
				out.write(ByteBuffer.allocate(94).putLong(0L).put((byte) 0xFF).put((byte) 0xFF).put(new byte[84]).array());
			} catch(IOException e){
				//DriverStation.reportError("Could not write first state", false);
				throw new RuntimeException("Could not write first state");
			}
		}
	}
	
	/**
	 * Remember to supply a filename! The .rta extension will be provided for you.
	 * @param filenameWithoutExtension
	 */
	public void provideOutputFile(String filenameWithoutExtension){
		outputFile = filenameWithoutExtension + ".rta";
	}
	private byte[] m_stb(short[] array){
		byte[] x = new byte[array.length];
		for(byte c = 0; c<array.length;c++) x[c] = (byte)array[c];
		return x;
	}
	
	public void init(){
		m_jAxis[0] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)0));
		m_jAxis[1] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)1));
		m_jAxis[2] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)2));
		m_jAxis[3] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)3));
		m_jAxis[4] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)4));
		m_jAxis[5] = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)5));
		m_jButtons[0] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)0, ByteBuffer.allocateDirect(1));
		m_jButtons[1] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)1, ByteBuffer.allocateDirect(1));
		m_jButtons[2] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)2, ByteBuffer.allocateDirect(1));
		m_jButtons[3] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)3, ByteBuffer.allocateDirect(1));
		m_jButtons[4] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)4, ByteBuffer.allocateDirect(1));
		m_jButtons[5] = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)5, ByteBuffer.allocateDirect(1));
		teleopController.provideRecorder(this);
		teleopController.init();
	}
	/**
	 * HEY! HEY! Anyone using this, change how you decide what to name these.
	 */
	public void end(){
		try {
			out.flush();
			out.close();
			// HEY! HEY! Anyone using this, change how you decide what to name these.
			if(!(new File(System.getProperty("user.home"),"out.rta").renameTo(new File(System.getProperty("user.home"),outputFile))))
				throw new IOException("fail");
		} catch(IOException e){
			throw new RuntimeException("Could not finish recording session.");
		}
		teleopController.end();
	}
	public void run(double time){
		// reset
		e_over.clear();
		e_update.clear();
		e_axis = 0;
		e_buto = 0;
		
		e_over.putLong((long) Math.floor(time*1000));
		// now update
		if(m_jButtons[0]==(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)0, ByteBuffer.allocateDirect(1)))){
			m_jButtons[0] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 1;
		}
		if(m_jButtons[1]==(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)1, ByteBuffer.allocateDirect(1)))){
			m_jButtons[1] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 2;
		}
		if(m_jButtons[2]==(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)2, ByteBuffer.allocateDirect(1)))){
			m_jButtons[2] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 4;
		}
		if(m_jButtons[3]==(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)3, ByteBuffer.allocateDirect(1)))){
			m_jButtons[3] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 8;
		}
		if(m_jButtons[4]==(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)4, ByteBuffer.allocateDirect(1)))){
			m_jButtons[4] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 16;
		}
		if(m_jButtons[5]==(tmp_short = (short) FRCNetworkCommunicationsLibrary.HALGetJoystickButtons((byte)5, ByteBuffer.allocateDirect(1)))){
			m_jButtons[5] = tmp_short;
			e_update.putShort(tmp_short);
			e_buto += 32;
		}
		if(Arrays.equals(m_jAxis[0], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)0))))){
			m_jAxis[0] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 1;
		}
		if(Arrays.equals(m_jAxis[1], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)1))))){
			m_jAxis[1] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 2;
		}
		if(Arrays.equals(m_jAxis[2], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)2))))){
			m_jAxis[2] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 4;
		}
		if(Arrays.equals(m_jAxis[3], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)3))))){
			m_jAxis[3] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 8;
		}
		if(Arrays.equals(m_jAxis[4], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)4))))){
			m_jAxis[4] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 16;
		}
		if(Arrays.equals(m_jAxis[5], (tmp_sha = m_stb(FRCNetworkCommunicationsLibrary.HALGetJoystickAxes((byte)5))))){
			m_jAxis[5] = tmp_sha;
			e_update.put(tmp_sha);
			e_axis += 32;
		}
		try {
			if(e_axis>0||e_buto>0){
				e_over.put(e_buto);
				e_over.put(e_axis);
				out.write(e_over.array());
				out.write(Arrays.copyOfRange(e_update.array(),0,e_update.position()));
			}
		} catch(IOException e){
			DriverStation.reportError("Failed to write line input sequence", false);
		}
		// and finally...
		teleopController.run(time);
	}
}
