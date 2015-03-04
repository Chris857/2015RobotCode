package team857.robot2015;

import team857.yetiRobot.*;

public class TeleopControl implements PeriodController {
	RobotDrive robot;
	Joystick left, right;
	XboxController operator;
	public TeleopControl(){
		left = new Joystick(1,0.01);
		right = new Joystick(2,0.01);
		operator = new XboxController(0,0.1,0.01);
	}
	public void init(){}
	public void run(double time){
		if(time<3);else if(time<3.5) operator.setRumble(0.5);else operator.setRumble(0);
		
		RobotDrive.drive(left.getY(), right.getY(), left.getTrigger()||right.getTrigger(),true);
		if(operator.getButtonY()) RobotDrive.arms(true);
		if(operator.getButtonA()) RobotDrive.arms(false);
		RobotDrive.winch(-operator.getLeftAxisY());
		if(operator.getButtonX()) RobotDrive.grab(true);
		if(operator.getButtonB()) RobotDrive.grab(false);
	}
}
