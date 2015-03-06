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
	public void run(double time){
		if(time<3);else if(time<3.5) operator.setRumble(0.5);else operator.setRumble(0);
		
		RobotDrive.getInstance().drive(left.getY(), right.getY(), left.getTrigger() || right.getTrigger(), true)
			.winch(operator.getLeftAxisY())
			.grab(operator.getButtonX()?true:null)
			.grab(operator.getButtonB()?false:null)
			.arms(operator.getButtonY()?true:null)
			.arms(operator.getButtonA()?false:null);
	}
}
