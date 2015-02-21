package team857.robot2015;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import team857.robot2015.auto.AutonControl;
import team857.robot2015.auto.DefaultAuton;
import team857.robot2015.auto.FancyAuton;
import team857.robot2015.auto.SecondAuton;
import team857.yetiRobot.PeriodControl;
import team857.yetiRobot.YetiRobot;

public class Robot extends YetiRobot implements PeriodControl {
	CameraServer camera;
	RobotDrive robotDrive;
	Relay lights;
	
	Joystick ctr;
	int set = 0;
	
	public void robotInit(){
		ctr = new Joystick(3);
		lights = new Relay(0);
		lights.setDirection(Direction.kForward);
		robotDrive = new RobotDrive();
		teleopControl = new TeleopControl(robotDrive);
		disabledControl = this;
		
		camera = CameraServer.getInstance();
    	camera.setQuality(50);
    	camera.startAutomaticCapture("cam0");
    	
    	put("Happy Space Day! Ready to go! :D");
    	
    	robotDrive.init();
    	init();
    	run(0);
	}
	
	public void setAuton(PeriodControl auton){
		autonControl = auton;
	}
	public int get(){
		int c=0;
		if(ctr.getRawButton(1)) c+=1;
		if(ctr.getRawButton(2)) c+=2;
		if(ctr.getRawButton(3)) c+=4;
		if(ctr.getRawButton(4)) c+=8;
		if(ctr.getRawButton(5)) c+=16;
		return c;
	}
	public void init(){
		set = 0;
	}
	public void run(double time){
		lights.setDirection(Direction.kForward);
		if(set!=get()){
			set = get();
			switch(set){
				case 1:
					setAuton(new AutonControl(robotDrive));break;
				case 2:
					setAuton(new SecondAuton(robotDrive));break;
				case 22:
					setAuton(new FancyAuton(robotDrive));break;
				default:
					setAuton(new DefaultAuton(robotDrive));
			}
			Robot.put("Now using autonomous #"+set);
		}
	}
}