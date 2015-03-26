package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class ToteContainerOverPlatformAuton extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time>6.45) RobotDrive.getInstance().stop();
		else if(time> 3.6) RobotDrive.getInstance().drive(0.4);
		else if(time> 2.6) RobotDrive.getInstance().drive(-0.2,0.65);
		else if(time> 2.2) RobotDrive.getInstance().stop().arms(true);
		else if(time> 1.7) RobotDrive.getInstance().winch(0).drive(0.6);
		else if(time> 0.5) RobotDrive.getInstance().winch(-0.6); 
		else /*1st to do*/ RobotDrive.getInstance().grab(true);
	}
}
