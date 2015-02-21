package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodControl;

public class DefaultAuton implements PeriodControl {
	RobotDrive robot;
	public DefaultAuton(RobotDrive drive){
		robot = drive;
	}
	public void init(){}
	public void run(double time){}
}
