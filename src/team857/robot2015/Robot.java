package team857.robot2015;

import edu.wpi.first.wpilibj.CameraServer;
import team857.robot2015.auto.*;
import team857.yetiRobot.*;

public class Robot extends YetiRobot implements PeriodController {
	CameraServer camera;
	int set;
	
	public void start(){
		setTeleopController(new TeleopControl());
		setDisabledController(this);
		
		camera = CameraServer.getInstance();
		camera.setQuality(50);
		camera.startAutomaticCapture("cam0");
		
		put("Happy Space Day. I am ready! :D");
		
		// Forcing an Autonomous to be selected.
		set = 0;
		run(0);
	}
	
	/*-- These are the PeriodController functions --*/
	public void init(){
		RobotDrive.getInstance().lights(true);
	}
	public void end(){
		RobotDrive.getInstance().lights(false);
	}
	public void run(double time){
		if(set != m_ds.getStickButtons(3)){
			set = m_ds.getStickButtons(3);
			switch(set){
				case 1:
					setAutonomousController(new AutonControl());break; //pause, move
				case 2:
					setAutonomousController(new SecondAuton());break; //move
				case 19:
					setAutonomousController(new ToteContainerAuton());break; //starts on Container, stacks on tote, takes both.
				case 22:
					setAutonomousController(new FancyAuton());break; //non-touched grab & move
				case 23:
					setAutonomousController(new FancyAutonTwo());break; //touching grab & move
				default:
					setAutonomousController(new PeriodController.NoOperation()); //nothing
			}
			put("Now using auton #"+set);
		}
	}
}