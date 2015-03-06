package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class ToteContainerAuton extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time> 7.0) RobotDrive.getInstance().stop(); //stop
		else if(time> 3.7) RobotDrive.getInstance().stop().drive(0.6); //move
		else if(time> 2.9) RobotDrive.getInstance().winch(0).drive(-0.5,0.5); //winch stop, turn
		else if(time> 2.7) RobotDrive.getInstance().winch(0.4); //winch up
		else if(time> 2.4) RobotDrive.getInstance().winch(0).grab(true); //winch stop, grab
		else if(time> 1.9) RobotDrive.getInstance().grab(false); //ungrab, winch keeps down
		else if(time> 1.4) RobotDrive.getInstance().stop().winch(-0.4); //stop, winch down
		else if(time> 0.9) RobotDrive.getInstance().winch(0).drive(0.6); //winch stop, forward
		else if(time> 0.1) RobotDrive.getInstance().winch(1); //winch up
		else /*1st to do*/ RobotDrive.getInstance().grab(true); //grab
	}
}
