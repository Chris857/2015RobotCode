package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.*;

public class AutonControl extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time> 7.0) RobotDrive.getInstance().stop();
		else if(time> 3.0) RobotDrive.getInstance().drive(0.4);
		else /*1st to do*/;//don't do anything. wait.
	}
}
