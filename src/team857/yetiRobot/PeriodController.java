package team857.yetiRobot;

public interface PeriodController {
	/**
	 * This function is run at the beginning of the period.
	 */
	public void init();
	/**
	 * This function is run periodically throughout the period.
	 * @param time The current time of the period.
	 */
	public void run(double time);
	
	public class NoOperation implements PeriodController {
		public void init(){}
		public void run(double time){}
	}
}
