package util;

import java.awt.Color;

public final class ColorUtil {
	private ColorUtil() {}
	
	public static Color getOpposingColor(Color origin) { 
		return new Color(255-origin.getRed(), 255-origin.getGreen(), 255-origin.getBlue());
	}
}
