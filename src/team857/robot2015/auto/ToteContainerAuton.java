package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodController;

public class ToteContainerAuton extends PeriodController.NoOperation {
	public void run(double time){
		/**/ if(time> 8.0) RobotDrive.getInstance().stop(); //stop
		else if(time> 5.0) RobotDrive.getInstance().drive(0.74); //move
		else if(time> 3.1) RobotDrive.getInstance().winch(0).drive(0.8,-0.8); //winch stop, turn
		else if(time> 2.7) RobotDrive.getInstance().winch(0.7); //winch up
		else if(time> 2.4) RobotDrive.getInstance().winch(0).grab(true); //winch stop, grab
		else if(time> 1.9) RobotDrive.getInstance().grab(false); //ungrab, winch keeps down
		else if(time> 1.4) RobotDrive.getInstance().stop().winch(-0.7); //stop, winch down
		else if(time> 0.9) RobotDrive.getInstance().winch(0).drive(0.8); //winch stop, forward
		else if(time> 0.2) RobotDrive.getInstance().winch(0.7); //winch up
		else /*1st to do*/ RobotDrive.getInstance().grab(true); //grab
	}
}
