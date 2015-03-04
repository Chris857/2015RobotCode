package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.*;

public class AutonControl implements PeriodController {
	public void init(){
		RobotDrive.lights(false);
	}
	public void run(double time){
		if(time<3){}
		else if(time<7) RobotDrive.drive(0.4);
		else RobotDrive.stop();
	}
}
