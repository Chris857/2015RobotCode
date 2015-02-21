package team857.robot2015;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class RobotDrive {
	public Victor m_fr, m_fl, m_br, m_bl;
	public Talon m_wl, m_wr, m_rl, m_rr;
	public edu.wpi.first.wpilibj.RobotDrive drive;
	
	public DigitalInput top, bottom;
	
	public Compressor compressor;
	public DoubleSolenoid sLeft, sRight, sGrabber;
	
	public RobotDrive(){
		m_fr = new Victor(0);
		m_fl = new Victor(3);
		m_br = new Victor(1);
		m_bl = new Victor(2);
		m_wl = new Talon (4);
		m_wr = new Talon (5);
		m_rl = new Talon (6);
		m_rr = new Talon (7);
		compressor = new Compressor();
		sLeft = new DoubleSolenoid(0,1);
		sRight = new DoubleSolenoid(7,6);
		sGrabber = new DoubleSolenoid(2,3);
		top = new DigitalInput(0);
		bottom = new DigitalInput(1);
		
		drive = new edu.wpi.first.wpilibj.RobotDrive(m_bl, m_fl, m_br, m_fr);
	}
	public void init(){compressor.start();}
	
	public double limit(double value){
		if(value>1) return 1;
		if(value<-1) return -1;
		return value;
	}
	
	public RobotDrive drive(double left, double right, boolean half, boolean squared){
		if(half)
			drive.tankDrive(left*0.6, right*0.6, false);
		else drive.tankDrive(left,right,squared);
		return this;
	}
	
	public RobotDrive drive(double left, double right){
		return drive(-left, -right, false, false);
	}
	
	public RobotDrive drive(double speed){
		return drive(-speed, -speed);
	}
	
	public RobotDrive stop(){
		return drive(0,0);
	}
	
	public RobotDrive winch(double power){
		if(!top.get() && power < 0) power = 0;
		if(!bottom.get() && power > 0) power = 0;
		m_wl.set(power);
		m_wr.set(-power);
		return this;
	}
	
	public RobotDrive roller(double left, double right){
		m_rl.set(left);
		m_rr.set(right);
		return this;
	}
	
	public RobotDrive arms(boolean out){
		if(out){
			sLeft.set(DoubleSolenoid.Value.kReverse);
			sRight.set(DoubleSolenoid.Value.kReverse);
		} else {
			sLeft.set(DoubleSolenoid.Value.kForward);
			sRight.set(DoubleSolenoid.Value.kForward);
		}
		return this;
	}
	
	public RobotDrive grab(boolean closed){
		if(closed)
			sGrabber.set(DoubleSolenoid.Value.kForward);
		else sGrabber.set(DoubleSolenoid.Value.kReverse);
		return this;
	}
}
