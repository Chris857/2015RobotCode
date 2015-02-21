package team857.yetiRobot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.communication.HALControlWord;


public abstract class YetiRobot extends IterativeRobot {
	protected PeriodControl autonControl, teleopControl, disabledControl, testControl;
	private Timer autonTimer, teleopTimer, disabledTimer, testTimer;
	public YetiRobot(){
		super();
		resetTimers();
		autonControl = new NopControl();
		teleopControl = new NopControl();
		disabledControl = new NopControl();
		testControl = new NopControl();
	}
	public abstract void robotInit();
	
	public void autonomousInit(){
		autonControl.init();
		resetTimers();
		autonTimer.start();
	}
	public void teleopInit(){
		teleopControl.init();
		resetTimers();
		teleopTimer.start();
	}
	public void disabledInit(){
		disabledControl.init();
		resetTimers();
		disabledTimer.start();
	}
	public void testInit(){
		testControl.init();
		resetTimers();
		testTimer.start();
	}
	public void autonomousPeriodic(){
		Scheduler.getInstance().run();
		autonControl.run(autonTimer.get());
	}
	public void teleopPeriodic(){
		Scheduler.getInstance().run();
		teleopControl.run(teleopTimer.get());
	}
	public void disabledPeriodic(){
		Scheduler.getInstance().run();
		disabledControl.run(disabledTimer.get());
	}
	public void testPeriodic(){
		Scheduler.getInstance().run();
		testControl.run(testTimer.get());
	}
	
	public void resetTimers(){
		autonTimer = new Timer();
		teleopTimer = new Timer();
		disabledTimer = new Timer();
		testTimer = new Timer();
	}
	
	public static void put(String str){
    	HALControlWord controlWord = FRCNetworkCommunicationsLibrary.HALGetControlWord();
		if(controlWord.getDSAttached()) FRCNetworkCommunicationsLibrary.HALSetErrorData(str+"\n");
    }
}
