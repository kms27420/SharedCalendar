package view.popup.common;

import util.ColorUtil;
import util.FontUtil;
import view.customized.TransparentLabel;

public class AlertLabel extends TransparentLabel {
	public AlertLabel(String text) {
		super(text);
		setFont(FontUtil.createDefaultFont(15f));
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		setForeground(ColorUtil.getLightGray());
	}
}
