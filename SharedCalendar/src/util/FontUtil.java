package util;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

public final class FontUtil {
	private static final Font DEFAULT_FONT = new Font(null, Font.BOLD, 15);
	private static final Map<Float, Font> DEFAULT_FONTS = new HashMap<>();
	static { 
		DEFAULT_FONTS.put(DEFAULT_FONT.getSize2D(), DEFAULT_FONT);
	}
	
	private FontUtil() {}
	
	public static Font createDefaultFont(float fontSize) {
		if(DEFAULT_FONTS.get(fontSize)==null)	DEFAULT_FONTS.put(fontSize, DEFAULT_FONT.deriveFont(fontSize));
		return DEFAULT_FONTS.get(fontSize);
	}
}
