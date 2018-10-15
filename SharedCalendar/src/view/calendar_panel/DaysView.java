package view.calendar_panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;

import calendar.DayInEnglish;
import util.FontUtil;
import view.customized.TransparentPanel;

public class DaysView extends TransparentPanel {
	private final Font DEFAULT_FONT = FontUtil.createDefaultFont(25);
	private final Color DEFAULT_TEXT_COLOR = Color.BLACK;
	
	DaysView() {
		setLayout(new GridLayout(1, DayInEnglish.values().length));
		for (DayInEnglish day : DayInEnglish.values())	add(createDayView(day.name().toUpperCase()));
	}
 
	private JLabel createDayView(String day) {
		JLabel dayView = new JLabel(day);
		dayView.setHorizontalAlignment(JLabel.CENTER);
		dayView.setVerticalAlignment(JLabel.CENTER);
		dayView.setOpaque(false);
		dayView.setFont(DEFAULT_FONT);
		dayView.setForeground(DEFAULT_TEXT_COLOR);
		return dayView;
	}
}
