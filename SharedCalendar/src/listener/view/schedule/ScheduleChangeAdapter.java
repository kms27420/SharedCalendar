package listener.view.schedule;

import data.Time;
import data.YMD;

public class ScheduleChangeAdapter implements ScheduleChangeListener {
	@Override
	public void onScheduleUpdate(int idx, String title, String content, YMD ymd, Time startTime, Time endTime) {}
	@Override
	public void onScheduleInsert(String title, String content, YMD ymd, Time startTime, Time endTime) {}
	@Override
	public void onScheduleDelete(int idx) {}
	@Override
	public void onScheduleSelect(int idx) {}
	@Override
	public void onScheduleSearch(String keyword) {}
	@Override
	public void onSearchedScheduleSelect(int idx) {}
}
