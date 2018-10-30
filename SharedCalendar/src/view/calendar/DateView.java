package view.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import util.ColorUtil;
import util.FontUtil;
import view.customized.text_button.TextButton;

/**
 * View that can show data about date.
 * What date or information about date ex) Is this date holiday?, Is there something important plan in this date?
 * It will show with colored text about information. ex) Holiday will be shown with red, important planning date will be shown with blue.
 * When user clicks this view, it will notify focused statement to user by drawing circle around date text.
 * @author Kwon
 *
 */ 
public class DateView extends TextButton {
	private final Color CLICKED_CIRCLE_COLOR = ColorUtil.getOrangeColor();
	private final Color DEFAULT_FG = Color.GRAY;
	private final Color FOCUSED_FG = Color.WHITE;
	private final Color HOLIDAY_FG = Color.RED;
	private final Font DEFAULT_FONT = FontUtil.createDefaultFont(15);
	
	private boolean isFocused;
	private boolean isHoliday;
	
	DateView() {
		setOpaque(false);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		setFont(DEFAULT_FONT);
		setNormalFg(DEFAULT_FG);
		setEffectedFg(Color.BLACK);
		setEnableOpposingFgEffect(false);
		setEffectTiming(EffectTiming.ENTERED_TIMING);
	}
	/**
	 * Show date what you want to set. There is a holiday setting option.
	 * @param date Date what you want to set.
	 * @param isHoliday If true it will be shown with holiday color.
	 */
	void showDate(int date, boolean isHoliday) {
		this.isHoliday = isHoliday;
		setEnableEffect(true);
		setText(date+"");
	}
	/**
	 * Show focused state of the date view.
	 * @param isFocused If true it will be shown with focused state.
	 */
	void showFocusedView(boolean isFocused) {
		this.isFocused = isFocused;
		setEnableEffect(false);
		revalidate();
		repaint();
	}
	/**
	 * Show empty view.
	 */
	void showEmpty() {
		setEnableEffect(false);
		setText("");
	}
	
	@Override
	public void paint(Graphics g) {
		if(isFocused) {
			g.setColor(CLICKED_CIRCLE_COLOR);
			g.fillOval((getWidth()-40)/2, (getHeight()-40)/2, 40, 40);
			setForeground(FOCUSED_FG);
		} else	setForeground(isHoliday ? HOLIDAY_FG : getNormalFg());
		super.paint(g);
	}
}
