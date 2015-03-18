package team857.yetiRobot.record;

public abstract class PlaybackController {
	protected Playback m_pl;
	public PlaybackController(Playback engine){
		m_pl = engine;
	}
	public abstract void run();
}
