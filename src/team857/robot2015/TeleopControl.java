package team857.robot2015;

import team857.yetiRobot.*;

public class TeleopControl extends PeriodController.NoOperation {
	RobotDrive robot;
	Joystick left, right;
	XboxController operator;
	public TeleopControl(){
		left = new Joystick(1,0.01);
		right = new Joystick(2,0.01);
		operator = new XboxController(0,0.1,0.01);
	}
	public void init(){
		RobotDrive.getInstance().lights(true);
	}
	public void end(){
		RobotDrive.getInstance().lights(false);
	}
	public void run(double time){
		RobotDrive.getInstance().drive(left.getY(), right.getY(), left.getTrigger() || right.getTrigger(), true)
			.winch(operator.getLeftAxisY())
			.grab(operator.getButtonX()?true:null)
			.grab(operator.getButtonB()?false:null)
			.arms(operator.getButtonY()?true:null)
			.arms(operator.getButtonA()?false:null)
			.kicker(operator.getLeftBumper()||operator.getRightBumper());
	}
}
