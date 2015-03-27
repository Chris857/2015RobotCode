package team857.robot2015;

import team857.yetiRobot.PlaybackStateMachine;

public class PlaybackTeleopController implements PlaybackStateMachine.PlaybackController {
	PlaybackStateMachine machine;
	PlaybackStateMachine.Joystick left, right;
	PlaybackStateMachine.Controller operator;
	public void init(PlaybackStateMachine machine){
		this.machine = machine;
	}
	public void run(double time){
		operator = machine.getCurrentState(time).asController(0);
		left = machine.getCurrentState(time).asJoystick(1);
		right = machine.getCurrentState(time).asJoystick(2);
		RobotDrive.getInstance().drive(left.getY(), right.getY(), left.getTrigger() || right.getTrigger(), true)
			.winch(operator.getLeftAxisY())
			.grab(operator.getButtonX()?true:null)
			.grab(operator.getButtonB()?false:null)
			.arms(operator.getButtonY()?true:null)
			.arms(operator.getButtonA()?false:null)
			.kicker(operator.getLeftBumper()||operator.getRightBumper());
	}
	public void end(){
		
	}
}
