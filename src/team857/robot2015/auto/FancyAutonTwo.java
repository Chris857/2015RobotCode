package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;;

public class FancyAutonTwo implements PeriodController {
	public void init(){
		RobotDrive.lights(false);
	}

	@SuppressWarnings("static-access") public void run(double time){
		/**/ if(time> 4.2) RobotDrive.drive(-0.4).stop();
		else if(time> 2.1) RobotDrive.drive(0.5);
		else if(time> 1.3) RobotDrive.winch(0).drive(-0.5,0.5);
		else if(time> 0.5) RobotDrive.winch(0.6);
		else /*1st to do*/ RobotDrive.grab(true);
	}
}
