package view.list;

import listener.schedule.ScheduleChangeAdapter;
import listener.schedule.ScheduleChangeListener;

public class ScheduleEditView {
	private ScheduleChangeListener scheduleChangeListener = new ScheduleChangeAdapter();
	
	public void setScheduleChangeListener(ScheduleChangeListener l) {
		scheduleChangeListener = l;
	}
}
