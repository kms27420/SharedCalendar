package util;

import java.awt.Color;

public final class ColorUtil {
	private ColorUtil() {}
	
	public static Color getOpposingColor(Color origin) { 
		return new Color(255-origin.getRed(), 255-origin.getGreen(), 255-origin.getBlue());
	}
	
	public static Color getLightColor(Color color) {
		return new Color(color.getRed()>240?255:color.getRed()+15, color.getGreen()>240?255:color.getGreen()+15, color.getBlue()>240?255:color.getBlue()+15);
	}
	
	public static Color getDarkerColor(Color color) {
		return new Color(color.getRed()<15?0:color.getRed()-15, color.getGreen()<15?0:color.getGreen()-15, color.getBlue()<15?0:color.getBlue()-15);
	}
	
	public static Color getDarkColor() {
		return new Color(64,64,64);
	}
	
	public static Color getOrangeColor() {
		return new Color(237, 125, 49);
	}
	
	public static Color getClickedViewColor() {
		return new Color(89, 89, 89);
	}
	
	public static Color getLightGray() {
		return new Color(165, 165, 165);
	}
}
