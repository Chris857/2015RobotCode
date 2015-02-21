package team857.yetiRobot;

import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

public class Joystick {
	private final edu.wpi.first.wpilibj.Joystick joystick;
	private double deadzone;
	
	public Joystick(int port, double deadzone){
		joystick = new edu.wpi.first.wpilibj.Joystick(port);
		this.deadzone = Math.abs(deadzone);
	}
	public Joystick(int port){this(port,0.05);}
	
	public void setDeadzone(double deadzone){
		this.deadzone = Math.abs(deadzone);
	}
	public double getDeadzone(){
		return deadzone;
	}
	private double limit(double value, double deadzone){
		if(Math.abs(value)-deadzone<0) return 0; else return value;
	}
	
	public double getX(double deadzone){return limit(joystick.getX(),deadzone);}
	public double getX(){return getX(deadzone);}
	
	public double getY(double deadzone){return limit(joystick.getY(),deadzone);}
	public double getY(){return getY(deadzone);}
	
	public double getTwist(double deadzone){return limit(joystick.getTwist(),deadzone);} 
	public double getTwist(){return getTwist(deadzone);}
	
	public double getThrottle(double deadzone){return limit(joystick.getThrottle(),deadzone);}
	public double getThrottle(){return getThrottle(deadzone);}
	
	public double getRawAxis(int axis, double deadzone){return limit(joystick.getRawAxis(axis),deadzone);}
	public double getRawAxis(int axis){return getRawAxis(axis, deadzone);}
	
	public double getAxis(AxisType axis, double deadzone){return limit(joystick.getAxis(axis),deadzone);}
	public double getAxis(AxisType axis){return getAxis(axis,deadzone);}
	
	public boolean getTrigger(){return joystick.getTrigger();}
	public boolean getTop(){return joystick.getTop();}
	public boolean getRawButton(int button){return joystick.getRawButton(button);}
	
	public int getAxisCount(){return joystick.getAxisCount();}
	public int getButtonCount(){return joystick.getButtonCount();}
	public int getPOVCount(){return joystick.getPOVCount();}
	
	public int getPOV(int pov){return joystick.getPOV(pov);}
	
	public boolean getButton(ButtonType button){return joystick.getButton(button);}
	
	public double getMagnitude(double deadzone){return limit(joystick.getMagnitude(),deadzone);}
	public double getMagnitude(){return getMagnitude(deadzone);}
	public double getDirectionRadians(){return joystick.getDirectionRadians();}
	public double getDirectionDegrees(){return joystick.getDirectionDegrees();}
	
	public int getAxisChannel(AxisType axis){return joystick.getAxisChannel(axis);}
	public void setAxisChannel(AxisType axis, int channel){joystick.setAxisChannel(axis, channel);}
	
	public void setRumble(RumbleType type, float value){joystick.setRumble(type, value);}
	public void setOutput(int outputNumber, boolean value){joystick.setOutput(outputNumber, value);}
	public void setOutputs(int value){joystick.setOutputs(value);}
}
