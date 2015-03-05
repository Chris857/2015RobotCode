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
	public Talon winchLeft, winchRight;
	public Compressor compressor;
	public DigitalInput top, bottom;
	public DoubleSolenoid armLeft, armRight, grabber;
	public Relay lights;
	
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
		lights = new Relay(0);
		lights.setDirection(Relay.Direction.kForward);
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
	
	public static RobotDrive drive(double left, double right, boolean half, boolean squared){
		if(half)
			getInstance().drive.tankDrive(left*0.3, right*0.3, false);
		else getInstance().drive.tankDrive(left, right, squared);
		return getInstance();
	}
	
	public static RobotDrive drive(double left, double right){
		return drive(-left, -right, false, false);
	}
	
	public static RobotDrive drive(double speed){
		return drive(-speed, -speed, false, false);
	}
	
	public static RobotDrive stop(){
		return drive(0);
	}
	
	public static RobotDrive winch(double power){
		getInstance();
		if(!instance.top.get() && power > 0) power = 0;
		if(!instance.bottom.get() && power < 0) power = 0;
		instance.winchLeft.set(-power);
		instance.winchRight.set(power);
		return instance;
	}
	
	public static RobotDrive arms(boolean out){
		getInstance();
		if(out){
			instance.armLeft.set(DoubleSolenoid.Value.kReverse);
			instance.armRight.set(DoubleSolenoid.Value.kReverse);
		} else {
			instance.armLeft.set(DoubleSolenoid.Value.kForward);
			instance.armRight.set(DoubleSolenoid.Value.kForward);
		}
		return instance;
	}
	
	public static RobotDrive grab(boolean closed){
		getInstance();
		if(closed)
			instance.grabber.set(DoubleSolenoid.Value.kForward);
		else instance.grabber.set(DoubleSolenoid.Value.kReverse);
		return instance;
	}
	
	public static RobotDrive lights(boolean on){
		getInstance();
		//if(on) instance.lights.set(Relay.Value.kOn);// else instance.lights.set(Relay.Value.kOff);
		return instance;
	}
}
