package team857.yetiRobot.record;

import team857.yetiRobot.PeriodController;

public abstract class RecordController implements PeriodController {
	private Recorder recorder;
	public void provideRecorder(Recorder recorder){
		this.recorder = recorder;
	}
	public Recorder getRecorder(){
		return recorder;
	}
	public abstract void init();
	public abstract void end();
	public abstract void run(double time);
}
