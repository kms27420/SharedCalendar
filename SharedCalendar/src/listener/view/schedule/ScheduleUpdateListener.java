package listener.view.schedule;

import data.Time;
import data.YMD;

public interface ScheduleUpdateListener {
	public void onScheduleUpdate(int idx, String title, String content, YMD ymd, Time startTime, Time endTime);
}
