package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class DefaultAuton implements PeriodController {
	public void init(){
		RobotDrive.lights(false);
	}
	public void run(double time){
		//nop
	}
}
