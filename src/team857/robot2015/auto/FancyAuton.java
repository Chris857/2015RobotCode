package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class FancyAuton extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time> 9.5) RobotDrive.getInstance().stop();
		else if(time> 4.6) RobotDrive.getInstance().winch(0).grab(false);
		else if(time> 4.2) RobotDrive.getInstance().winch(-0.6);
		else if(time> 4.1) RobotDrive.getInstance().stop();
		else if(time> 2.3) RobotDrive.getInstance().drive(0.60);
		else if(time> 2.5) RobotDrive.getInstance().stop();
		else if(time> 1.7) RobotDrive.getInstance().winch(0).drive(-0.52,0.52);
		else if(time> 1.4) RobotDrive.getInstance().winch(0.7);
		else if(time> 1.2) RobotDrive.getInstance().winch(0.4);
		else if(time> 1.1) RobotDrive.getInstance().stop().grab(true);
		else if(time> 0.5) RobotDrive.getInstance().drive(0.37);
		else /*1st to do*/ RobotDrive.getInstance().grab(false);
	}
}
