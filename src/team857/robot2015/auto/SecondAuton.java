package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodControl;

public class SecondAuton implements PeriodControl {
	RobotDrive robot;
	public SecondAuton(RobotDrive drive){
		robot = drive;
	}
	public void init(){
		//nop
	}
	public void run(double time){
		if(time<4)
			robot.drive(0.4);
		else robot.stop();
	}
}
