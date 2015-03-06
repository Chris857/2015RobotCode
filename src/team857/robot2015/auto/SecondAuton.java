package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class SecondAuton extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time> 4.0) RobotDrive.getInstance().stop();
		else /*1st to do*/ RobotDrive.getInstance().drive(0.4);
	}
}
