package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.*;

public class ShortForward extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time> 2.00) RobotDrive.getInstance().stop();
		else /*1st to do*/ RobotDrive.getInstance().drive(0.4);
	}
}
