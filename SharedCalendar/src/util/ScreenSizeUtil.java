package util;

import java.awt.Dimension;
import java.awt.Toolkit;

public final class ScreenSizeUtil {
	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	private ScreenSizeUtil() {}
	
	public static Dimension getScreenSize() {
		return new Dimension(SCREEN_SIZE);
	}
	
	public static int getScreenWidth() {
		return SCREEN_SIZE.width;
	}
	
	public static int getScreenHeight() {
		return SCREEN_SIZE.height;
	}
	
	public static Dimension getScaledSize(float widthRatio, float heightRatio) {
		return new Dimension((int)(SCREEN_SIZE.getWidth()*widthRatio), (int)(SCREEN_SIZE.getHeight()*heightRatio));
	}
}
