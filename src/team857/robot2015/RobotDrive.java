package team857.robot2015;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class RobotDrive {
	private static RobotDrive instance = null;
	
	public edu.wpi.first.wpilibj.RobotDrive drive;
	public Talon winchLeft, winchRight, armWinch;
	public Compressor compressor;
	public DigitalInput top, bottom;
	public DoubleSolenoid armLeft, armRight, grabber, kicker;
	public Relay lights, containerRelay;
	private boolean container = false;
	
	/**
	 * for internal use
	 */
	private RobotDrive(){
		winchLeft = new Talon(4);
		winchRight = new Talon(5);
		compressor = new Compressor();
		drive = new edu.wpi.first.wpilibj.RobotDrive(new Victor(2),//back left
								new Victor(3), //front left
								new Victor(1), //back right
								new Victor(0));//front right
		top = new DigitalInput(0);
		bottom = new DigitalInput(1);
		armLeft = new DoubleSolenoid(0,1);
		grabber = new DoubleSolenoid(2,3);
		armRight= new DoubleSolenoid(7,6);
		kicker = new DoubleSolenoid(4,5);
		lights = new Relay(0);
		lights.setDirection(Relay.Direction.kForward);
		containerRelay = new Relay(2);
		containerRelay.setDirection(Relay.Direction.kBoth);
		armWinch = new Talon(6);
	}
	
	public static RobotDrive getInstance(){
		if(null==instance) instance = new RobotDrive();
		return instance;
	}
	
	/**
	 * limits output to motor controller
	 * @param value to check
	 * @return value to set motor controller to
	 */
	public static double limit(double value){
		if(value>1) return 1;
		if(value<-1) return -1;
		return value;
	}
	
	public RobotDrive drive(double left, double right, boolean half, boolean squared){
		if(half)
			drive.tankDrive(left*0.3, right*0.3, false);
		else drive.tankDrive(left, right, squared);
		return this;
	}
	
	public RobotDrive drive(double left, double right){
		return drive(-left, -right, false, false);
	}
	
	public RobotDrive drive(double speed){
		return drive(-speed, -speed, false, false);
	}
	
	public RobotDrive stop(){
		return drive(0);
	}
	
	public RobotDrive winch(double power){
		if(!top.get() && power > 0) power = 0;
		if(!bottom.get() && power < 0) power = 0;
		winchLeft.set(-power);
		winchRight.set(power);
		return this;
	}
	
	public RobotDrive arms(Boolean out){
		if(null == out) return this;//safety
		if(out){
			armLeft.set(DoubleSolenoid.Value.kReverse);
			armRight.set(DoubleSolenoid.Value.kReverse);
		} else {
			armLeft.set(DoubleSolenoid.Value.kForward);
			armRight.set(DoubleSolenoid.Value.kForward);
		}
		return this;
	}
	
	public RobotDrive kicker(boolean out){
		if(out)
			kicker.set(DoubleSolenoid.Value.kReverse);
		else kicker.set(DoubleSolenoid.Value.kForward);
		return this;
	}
	
	public RobotDrive grab(Boolean closed){
		if(null == closed) return this;//safety
		if(closed)
			grabber.set(DoubleSolenoid.Value.kForward);
		else grabber.set(DoubleSolenoid.Value.kReverse);
		return this;
	}
	
	public RobotDrive lights(Boolean on){
		if(null == on) return this;//safety
		if(on) instance.lights.set(Relay.Value.kOn); else instance.lights.set(Relay.Value.kOff);
		return this;
	}
	
	public RobotDrive container(boolean toggle){
		if(toggle){
			container = !container;
			if(container)
				containerRelay.set(Relay.Value.kForward);
			else
				containerRelay.set(Relay.Value.kReverse);
		}
		return this;
	}
}
