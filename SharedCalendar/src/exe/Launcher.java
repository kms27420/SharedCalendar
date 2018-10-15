package exe;

import controler.YMDControler;
import view.calendar_panel.CalendarPanel;
import view.frame.FrameShower;

public class Launcher {
	public static void main(String[] args) {
		CalendarPanel calendarPanel = new CalendarPanel();
		calendarPanel.setYMDSelectListener(new YMDControler(calendarPanel));
		FrameShower.INSTANCE.showMainFrame(calendarPanel);
	} 
}
