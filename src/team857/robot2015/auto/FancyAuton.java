package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class FancyAuton implements PeriodController {
	public void init(){
		RobotDrive.lights(false);
	}
	@SuppressWarnings("static-access") public void run(double time){
		/**/ if(time>10.0) RobotDrive.stop();
		else if(time> 5.1) RobotDrive.winch(0).grab(false);
		else if(time> 4.7) RobotDrive.winch(-0.6);
		else if(time> 4.6) RobotDrive.stop();
		else if(time> 3.0) RobotDrive.drive(0.74);
		else if(time> 2.9) RobotDrive.stop();
		else if(time> 2.0) RobotDrive.winch(0).drive(0.8,-0.8);
		else if(time> 1.3) RobotDrive.winch(0.7);
		else if(time> 1.1) RobotDrive.winch(0.4);
		else if(time> 1.0) RobotDrive.stop().grab(true);
		else if(time> 0.5) RobotDrive.drive(0.6);
		else /*1st to do*/ RobotDrive.grab(false);
	}
}
