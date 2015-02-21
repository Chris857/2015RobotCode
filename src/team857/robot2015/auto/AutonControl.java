package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.*;

public class AutonControl implements PeriodControl {
	RobotDrive robot;
	public AutonControl(RobotDrive drive){robot = drive;}
	public void init(){}
	public void run(double time){
		if(time<3){}
		else if(time<7) robot.drive(0.4);
		else robot.stop();
	}
}
