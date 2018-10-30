package listener.schedule;

import data.SharedSchedule;

public interface ScheduleShareListener {
	public boolean onScheduleShare(SharedSchedule share);
	public boolean onScheduleUnshare(SharedSchedule unshare);
}
