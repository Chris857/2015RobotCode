package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class ContainerOverStep extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time> 5.2) RobotDrive.getInstance().drive(-0.4).stop();
		else if(time> 3.1) RobotDrive.getInstance().drive(0.5);
		else if(time> 2.3) RobotDrive.getInstance().winch(0).drive(-0.5,0.5);
		else if(time> 1.5) RobotDrive.getInstance().winch(0.6);
		else if(time> 1.0) RobotDrive.getInstance().grab(true);
		else /*1st to do*/ RobotDrive.getInstance().winch(0.5);
	}
}
