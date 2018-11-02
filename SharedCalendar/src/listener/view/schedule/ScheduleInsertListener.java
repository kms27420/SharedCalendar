package listener.view.schedule;

import data.Time;
import data.YMD;

public interface ScheduleInsertListener {
	public void onScheduleInsert(String title, String content, YMD ymd, Time startTime, Time endTime);
}
