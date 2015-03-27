package team857.robot2015;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import team857.robot2015.auto.*;
import team857.yetiRobot.*;

public class Robot extends YetiRobot implements PeriodController {
	CameraServer camera;
	int set;
	private RecorderSelector selector;
	
	private class RecorderSelector implements PlaybackStateMachine.FileSelector {
		public File getFile(){
			return new File(System.getProperty("user.home"), ""+m_ds.getStickButtons(3)+".rta");
		}
	}
	
	public void start(){
		selector = new RecorderSelector();
		setTeleopController(new TeleopControl());
		setDisabledController(this);
		setTestController(new PlaybackStateMachine(getTeleopController(), selector));
		
		//camera = CameraServer.getInstance();
		//camera.setQuality(50);
		//camera.startAutomaticCapture("cam0");
		
		put("Happy Space Day. I am ready! :D");
		
		// Forcing an Autonomous to be selected.
		set = 0;
		run(0);
	}
	
	/*-- These are the PeriodController functions --*/
	public void init(){
		//RobotDrive.getInstance().lights(true);
	}
	public void end(){
		//RobotDrive.getInstance().lights(false);
	}
	public void run(double time){
		if(set != m_ds.getStickButtons(3)){
			set = m_ds.getStickButtons(3);
			switch(set){
				case 1:
					setAutonomousController(new AutonControl());break; //pause, move
				case 2:
					setAutonomousController(new SecondAuton());break; //move
				case 3:
					setAutonomousController(new ToteContainerAuton());break; //starts on Container, stacks on tote, takes both.
				case 4:
					setAutonomousController(new FancyAuton());break; //non-touched grab & move
				case 5:
					setAutonomousController(new FancyAutonTwo());break; //touching grab & move
				case 6:
					setAutonomousController(new ToteContainerOverPlatformAuton());break;
				case 7:
					setAutonomousController(new ToteContainerFlatFieldAuton());break;
				default:
					if(set != 0 && selector.getFile().exists()){
						try {
							setAutonomousController(new PlaybackStateMachine(new PlaybackTeleopController(), selector).load());
						} catch(IOException e){
							DriverStation.reportError("Woops! Failed to read auton file. Falling back to no-op.", false);
							setAutonomousController(new PeriodController.NoOperation());
						}
					} else  setAutonomousController(new PeriodController.NoOperation());
			}
			put("Now using auton #"+set);
		}
	}
}