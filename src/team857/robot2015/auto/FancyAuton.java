package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class FancyAuton implements PeriodController {
	public void init(){
		RobotDrive.lights(false);
	}
	@SuppressWarnings("static-access") public void run(double time){
		/**/ if(time> 9.5) RobotDrive.stop();
		else if(time> 4.6) RobotDrive.winch(0).grab(false);
		else if(time> 4.2) RobotDrive.winch(-0.6);
		else if(time> 4.1) RobotDrive.stop();
		else if(time> 2.3) RobotDrive.drive(0.60);
		else if(time> 2.5) RobotDrive.stop();
		else if(time> 1.7) RobotDrive.winch(0).drive(-0.52,0.52);
		else if(time> 1.4) RobotDrive.winch(0.7);
		else if(time> 1.2) RobotDrive.winch(0.4);
		else if(time> 1.1) RobotDrive.stop().grab(true);
		else if(time> 0.5) RobotDrive.drive(0.37);
		else /*1st to do*/ RobotDrive.grab(false);
	}
}
