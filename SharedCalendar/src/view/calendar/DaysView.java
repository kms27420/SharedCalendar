package view.calendar;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

import calendar.DayInEnglish;
import util.FontUtil;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;

public class DaysView extends TransparentPanel {
	DaysView() {
		setLayout(new GridLayout(1, DayInEnglish.values().length));
		for (DayInEnglish day : DayInEnglish.values())	add(createDayView(day.name().toUpperCase()));
	}
 
	private JLabel createDayView(String day) {
		JLabel dayView = new TransparentLabel(day);
		dayView.setHorizontalAlignment(JLabel.CENTER);
		dayView.setVerticalAlignment(JLabel.CENTER);
		dayView.setFont(FontUtil.createDefaultFont(25));
		dayView.setForeground(Color.BLACK);
		return dayView;
	}
}
