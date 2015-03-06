package team857.yetiRobot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.communication.HALControlWord;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * YetiRobot is based on IterativeRobot's code.
 * 
 * TODO: better documentation
 *
 */
public abstract class YetiRobot extends RobotBase {
	private boolean disabledInited, autonInited, teleopInited, testInited;
	private Timer disabledTimer, autonTimer, teleopTimer, testTimer, robotTimer;
	
	private PeriodController autonController, teleopController, testController, disabledController, robotController;
	
	/**
	 * Constructor for YetiRobot.
	 * 
	 * Initializes instance variables for indicating state.
	 */
	public YetiRobot(){
		disabledInited = false;
		autonInited = false;
		teleopInited = false;
		testInited = false;
		disabledTimer = new Timer();
    	autonTimer = new Timer();
    	teleopTimer = new Timer();
    	testTimer = new Timer();
		robotTimer = new Timer();
		autonController = new PeriodController.NoOperation();
		teleopController = new PeriodController.NoOperation();
		disabledController = new PeriodController.NoOperation();
		testController = new PeriodController.NoOperation();
		robotController = new PeriodController.NoOperation();
	}
	/**
	 * This is called when our robot is initialized.
	 */
	public abstract void start();
	/**
	 * Sets the Autonomous-Period controller.
	 * @param controller Our controller.
	 */
	public void setAutonomousController(PeriodController controller){autonController = controller;}
	/**
	 * Sets the Teleop-Period controller.
	 * @param controller Our controller.
	 */
	public void setTeleopController(PeriodController controller){teleopController = controller;}
	/**
	 * Sets the Disabled-Period controller.
	 * @param controller Our controller.
	 */
	public void setDisabledController(PeriodController controller){disabledController = controller;}
	/**
	 * Sets the Test-Period controller.
	 * @param controller Our controller.
	 */
	public void setTestController(PeriodController controller){testController = controller;}
	/**
	 * Sets the Controller that runs over the entire life of the robot.
	 * @param controller Our controller.
	 */
	public void setRobotController(PeriodController controller){robotController = controller;}
    
    protected void prestart(){/* Don't immediately say that the robot's ready to be enabled. // See below.*/}
    
    private void resetTimers(){
    	disabledTimer.stop();
    	disabledTimer.reset();
    	autonTimer.stop();
    	autonTimer.reset();
    	teleopTimer.stop();
    	teleopTimer.reset();
    	testTimer.stop();
    	testTimer.reset();
    }
    
    /**
     * Provide an alternate "main loop" via startCompetition(). For further documentation see IterativeRobot.
     *
     */
    public void startCompetition() {
        UsageReporting.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);

        start();
        robotController.init();
        robotTimer.start();
        
        FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramStarting();
        LiveWindow.setEnabled(false);
        while(true){//we are eternal
            if(isDisabled()){
            	if(!disabledInited){
            		if(testInited) testController.end();
            		if(autonInited) autonController.end();
            		if(teleopInited) teleopController.end();
            		LiveWindow.setEnabled(false);
            		disabledController.init();
            		disabledInited = true;
            		autonInited = false;
            		teleopInited = false;
            		testInited = false;
            		resetTimers();
            		disabledTimer.start();
            	}
            	if(okayToRun()){
            		FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramDisabled();
            		disabledController.run(disabledTimer.get());
            		robotController.run(robotTimer.get());
            		Scheduler.getInstance().run();
            	}
            } else if(isTest()){
            	if(!testInited){
            		if(autonInited) autonController.end();
            		if(teleopInited) teleopController.end();
            		if(disabledInited) disabledController.end();
            		LiveWindow.setEnabled(true);
            		testController.init();
            		disabledInited = false;
            		autonInited = false;
            		teleopInited = false;
            		testInited = true;
            		resetTimers();
            		testTimer.start();
            	}
            	if(okayToRun()){
            		FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramTest();
            		testController.run(testTimer.get());
            		robotController.run(robotTimer.get());
            		Scheduler.getInstance().run();
            	}
            } else if(isAutonomous()){
                if(!autonInited){
                	if(testInited) testController.end();
            		if(teleopInited) teleopController.end();
            		if(disabledInited) disabledController.end();
                    LiveWindow.setEnabled(false);
                    autonController.init();
                    disabledInited = false;
            		autonInited = true;
            		teleopInited = false;
            		testInited = false;
                    resetTimers();
                    autonTimer.start();
                }
                if (okayToRun()) {
                    FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramAutonomous();
                    autonController.run(autonTimer.get());
                    robotController.run(robotTimer.get());
                    Scheduler.getInstance().run();
                }
            } else {
            	if(!teleopInited){
            		if(testInited) testController.end();
            		if(autonInited) autonController.end();
            		if(disabledInited) disabledController.end();
            		LiveWindow.setEnabled(false);
            		teleopController.init();
            		disabledInited = false;
            		autonInited = false;
            		teleopInited = true;
            		testInited = false;
            		resetTimers();
            		teleopTimer.start();
            	}
            	if(okayToRun()){
            		FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramTeleop();
            		teleopController.run(teleopTimer.get());
            		robotController.run(robotTimer.get());
            		Scheduler.getInstance().run();
            	}
            }
            m_ds.waitForData();
        }
    }

    /**
     * Determine if the appropriate next periodic function should be called.
     * Call the periodic functions whenever a packet is received from the Driver Station, or about every 20ms.
     */
    private boolean okayToRun() {
        return m_ds.isNewControlData();
    }
    
    /**
     * Puts a message to the Driver Station
     */
    public static void put(String string){
    	HALControlWord controlWord = FRCNetworkCommunicationsLibrary.HALGetControlWord();
		if(controlWord.getDSAttached()) FRCNetworkCommunicationsLibrary.HALSetErrorData(string+"\n");
    }
}
