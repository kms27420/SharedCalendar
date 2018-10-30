package listener.schedule;

import data.SharedSchedule;

public class ScheduleShareAdapter implements ScheduleShareListener {
	@Override
	public boolean onScheduleShare(SharedSchedule share) {return false;}
	@Override
	public boolean onScheduleUnshare(SharedSchedule unshare) {return false;}
}
