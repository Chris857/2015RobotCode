package team857.yetiRobot.play;

import java.lang.reflect.Field;

import edu.wpi.first.wpilibj.DriverStation;

public class DSInterface {
	static DriverStation ds;
	static Field ds_axes;
	public static void doTerribleThings() throws Exception {
		ds = DriverStation.getInstance();
		ds_axes = DriverStation.class.getDeclaredField("m_joystickAxes");
		ds_axes.setAccessible(true);
	}
	
	public byte[][] getAxes(){
		return new byte[][]{
			new byte[]{
//				ds.getSt
			},
			new byte[]{
					
			},
			new byte[]{
					
			},
			new byte[]{
					
			},
			new byte[]{
					
			},
			new byte[]{
					
			}
		};
	}
	public short[][] getPOVs(){
		return new short[][]{
			new short[]{
				(short) ds.getStickAxis(0, 0),
				(short) ds.getStickAxis(0, 1),
				(short) ds.getStickAxis(0, 2),
				(short) ds.getStickAxis(0, 3),
				(short) ds.getStickAxis(0, 4),
				(short) ds.getStickAxis(0, 5),
				(short) ds.getStickAxis(0, 6),
				(short) ds.getStickAxis(0, 7),
				(short) ds.getStickAxis(0, 8),
				(short) ds.getStickAxis(0, 9),
				(short) ds.getStickAxis(0,10),
				(short) ds.getStickAxis(0,11)
			},
			new short[]{
				(short) ds.getStickAxis(1, 0),
				(short) ds.getStickAxis(1, 1),
				(short) ds.getStickAxis(1, 2),
				(short) ds.getStickAxis(1, 3),
				(short) ds.getStickAxis(1, 4),
				(short) ds.getStickAxis(1, 5),
				(short) ds.getStickAxis(1, 6),
				(short) ds.getStickAxis(1, 7),
				(short) ds.getStickAxis(1, 8),
				(short) ds.getStickAxis(1, 9),
				(short) ds.getStickAxis(1,10),
				(short) ds.getStickAxis(1,11)
			},
			new short[]{
				(short) ds.getStickAxis(2, 0),
				(short) ds.getStickAxis(2, 1),
				(short) ds.getStickAxis(2, 2),
				(short) ds.getStickAxis(2, 3),
				(short) ds.getStickAxis(2, 4),
				(short) ds.getStickAxis(2, 5),
				(short) ds.getStickAxis(2, 6),
				(short) ds.getStickAxis(2, 7),
				(short) ds.getStickAxis(2, 8),
				(short) ds.getStickAxis(2, 9),
				(short) ds.getStickAxis(2,10),
				(short) ds.getStickAxis(2,11)
			},
			new short[]{
				(short) ds.getStickAxis(3, 0),
				(short) ds.getStickAxis(3, 1),
				(short) ds.getStickAxis(3, 2),
				(short) ds.getStickAxis(3, 3),
				(short) ds.getStickAxis(3, 4),
				(short) ds.getStickAxis(3, 5),
				(short) ds.getStickAxis(3, 6),
				(short) ds.getStickAxis(3, 7),
				(short) ds.getStickAxis(3, 8),
				(short) ds.getStickAxis(3, 9),
				(short) ds.getStickAxis(3,10),
				(short) ds.getStickAxis(3,11)
			},
			new short[]{
				(short) ds.getStickAxis(4, 0),
				(short) ds.getStickAxis(4, 1),
				(short) ds.getStickAxis(4, 2),
				(short) ds.getStickAxis(4, 3),
				(short) ds.getStickAxis(4, 4),
				(short) ds.getStickAxis(4, 5),
				(short) ds.getStickAxis(4, 6),
				(short) ds.getStickAxis(4, 7),
				(short) ds.getStickAxis(4, 8),
				(short) ds.getStickAxis(4, 9),
				(short) ds.getStickAxis(4,10),
				(short) ds.getStickAxis(4,11)
			},
			new short[]{
				(short) ds.getStickAxis(5, 0),
				(short) ds.getStickAxis(5, 1),
				(short) ds.getStickAxis(5, 2),
				(short) ds.getStickAxis(5, 3),
				(short) ds.getStickAxis(5, 4),
				(short) ds.getStickAxis(5, 5),
				(short) ds.getStickAxis(5, 6),
				(short) ds.getStickAxis(5, 7),
				(short) ds.getStickAxis(5, 8),
				(short) ds.getStickAxis(5, 9),
				(short) ds.getStickAxis(5,10),
				(short) ds.getStickAxis(5,11)
			}
		};
	}
	public short[] getButtons(){
		return new short[]{
			(short) ds.getStickButtons(0),
			(short) ds.getStickButtons(1),
			(short) ds.getStickButtons(2),
			(short) ds.getStickButtons(3),
			(short) ds.getStickButtons(4),
			(short) ds.getStickButtons(5)
		};
	}
}
