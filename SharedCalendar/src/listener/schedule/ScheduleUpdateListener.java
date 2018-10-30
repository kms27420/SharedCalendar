package listener.schedule;

import data.Schedule;

public interface ScheduleUpdateListener {
	public boolean onScheduleUpdate(Schedule update);
}
