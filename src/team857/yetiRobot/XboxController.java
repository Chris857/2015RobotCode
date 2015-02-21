package team857.yetiRobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class XboxController {
	private final Joystick joystick;
	private double deadbandLeft, deadbandRight, deadbandTrigger;
	
	public XboxController(int port, double deadbandLeft, double deadbandRight, double deadbandTrigger){
		joystick = new Joystick(port);
		this.deadbandLeft = Math.abs(deadbandLeft);
		this.deadbandRight = Math.abs(deadbandRight);
		this.deadbandTrigger = Math.abs(deadbandTrigger);
	}
	public XboxController(int port, double deadbandStick, double deadbandTrigger){this(port,deadbandStick,deadbandStick,deadbandTrigger);}
	public XboxController(int port){this(port,0.05,0.05);}
	
	public void setStickDeadband(double amount){deadbandLeft = Math.abs(amount);deadbandRight = Math.abs(amount);}
	public void setLeftStickDeadband(double amount){deadbandLeft = Math.abs(amount);}
	public void setRightStickDeadband(double amount){deadbandRight = Math.abs(amount);}
	public void setTriggerDeadband(double amount){deadbandTrigger = Math.abs(amount);}
	private double limit(double value, double deadband){
		if(Math.abs(value)-deadband<0) return 0; else return value;
	}
	
	public boolean getButtonA(){return joystick.getRawButton(1);}
	public boolean getButtonB(){return joystick.getRawButton(2);}
	public boolean getButtonX(){return joystick.getRawButton(3);}
	public boolean getButtonY(){return joystick.getRawButton(4);}
	
	public boolean getLeftBumper(){return joystick.getRawButton(5);}
	public boolean getRightBumper(){return joystick.getRawButton(6);}
	
	public boolean getBackButton(){return joystick.getRawButton(7);}
	public boolean getStartButton(){return joystick.getRawButton(8);}
	public boolean getLeftStickDown(){return joystick.getRawButton(9);}
	public boolean getRightStickDown(){return joystick.getRawButton(10);}
	
	public double getLeftAxisX(){return getLeftAxisX(deadbandLeft);}
	public double getLeftAxisX(double deadband){return limit(joystick.getRawAxis(0),deadband);}
	public double getRightAxisX(){return getRightAxisX(deadbandRight);}
	public double getRightAxisX(double deadband){return limit(joystick.getRawAxis(4),deadband);}
	public double getLeftAxisY(){return getLeftAxisY(deadbandLeft);}
	public double getLeftAxisY(double deadband){return limit(joystick.getRawAxis(1),deadband);}
	public double getRightAxisY(){return getRightAxisY(deadbandRight);}
	public double getRightAxisY(double deadband){return limit(joystick.getRawAxis(5),deadband);}
	public double getRightTrigger(){return getRightTrigger(deadbandTrigger);}
	public double getRightTrigger(double deadband){return limit(joystick.getRawAxis(3),deadband);}
	public double getLeftTrigger(){return getLeftTrigger(deadbandTrigger);}
	public double getLeftTrigger(double deadband){return limit(joystick.getRawAxis(2),deadband);}
	
	public void setRumble(RumbleType type, float value){joystick.setRumble(type, value);}
	public void setRumble(double value){setRumble((float)value);}
	public void setRumble(float value){
		joystick.setRumble(RumbleType.kLeftRumble, value);
		joystick.setRumble(RumbleType.kRightRumble, value);
	}
}
