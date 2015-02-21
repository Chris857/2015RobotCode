package team857.robot2015.auto;

import team857.robot2015.RobotDrive;
import team857.yetiRobot.PeriodControl;

public class FancyAuton implements PeriodControl {
	RobotDrive robot;
	public FancyAuton(RobotDrive drive){robot = drive;}
	public void init(){}
	public void run(double time){
		/**/ if(time>10.0) robot.stop();
		else if(time> 5.1) robot.winch(0).grab(false);
		else if(time> 4.7) robot.winch(-0.6);
		else if(time> 4.6) robot.stop();
		else if(time> 3.0) robot.drive(0.74);
		else if(time> 2.9) robot.stop();
		else if(time> 2.0) robot.winch(0).drive(0.8,-0.8);
		else if(time> 1.3) robot.winch(0.7);
		else if(time> 1.1) robot.winch(0.4);
		else if(time> 1.0) robot.stop().grab(true);
		else if(time> 0.5) robot.drive(0.6);
		else /*1st to do*/ robot.grab(false);
		
		
		// if(time>10.0) robot.stop();                   else
		// if(time> 5.1) robot.winch(0).grab(false);     else
		// if(time> 4.7) robot.winch(-0.6);              else
		// if(time> 4.6) robot.stop();                   else
		// if(time> 3.0) robot.drive(0.74);              else
		// if(time> 2.9) robot.stop();                   else
		// if(time> 2.0) robot.winch(0).drive(0.8,-0.8); else
		// if(time> 1.3) robot.winch(0.7);               else
		// if(time> 1.1) robot.winch(0.4);               else
		// if(time> 1.0) robot.stop().grab(true);        else
		// if(time> 0.5) robot.drive(0.6);               else
		// /*1st to do*/ robot.grab(false);
	}
}
