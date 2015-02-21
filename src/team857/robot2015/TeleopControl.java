package team857.robot2015;

import team857.yetiRobot.*;

public class TeleopControl implements PeriodControl {
	RobotDrive robot;
	Joystick left, right;
	XboxController operator;
	public TeleopControl(RobotDrive drive){
		robot = drive;
		left = new Joystick(1,0.01);
		right = new Joystick(2,0.01);
		operator = new XboxController(0,0.1,0.01);
	}
	public void init(){}
	public void run(double time){
		if(time<3);else if(time<3.5) operator.setRumble(0.5);else operator.setRumble(0);
		
		robot.drive(left.getY(), right.getY(), left.getTrigger()||right.getTrigger(),true);
		if(operator.getButtonY()) robot.arms(true);
		if(operator.getButtonA()) robot.arms(false);
		robot.winch(-operator.getLeftAxisY());
		robot.roller(operator.getRightTrigger()*(operator.getRightBumper()?1:-1),
				     operator.getLeftTrigger()*(operator.getLeftBumper()?-1:1));
		if(operator.getButtonX()) robot.grab(true);
		if(operator.getButtonB()) robot.grab(false);
	}
}
