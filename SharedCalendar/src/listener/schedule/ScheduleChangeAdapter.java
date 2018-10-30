package listener.schedule;

import data.Schedule;

public class ScheduleChangeAdapter implements ScheduleChangeListener {
	@Override
	public boolean onScheduleInsert(Schedule insert) {return false;}
	@Override
	public boolean onScheduleUpdate(Schedule update) {return false;}
	@Override
	public boolean onScheduleDelete(Schedule delete) {return false;}
}
