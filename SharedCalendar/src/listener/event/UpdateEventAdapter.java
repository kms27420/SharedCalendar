package listener.event;

import data.Friend;
import data.Schedule;
import data.SharedSchedule;

public class UpdateEventAdapter implements UpdateEventListener {
	@Override
	public void onScheduleInsert(Schedule s) {}
	@Override
	public void onScheduleUpdate(Schedule s) {}
	@Override
	public void onScheduleDelete(Schedule s) {}
	@Override
	public void onShare(SharedSchedule s) {}
	@Override
	public void onUnshare(SharedSchedule s) {}
	@Override
	public void onFriendInsert(String id) {}
	@Override
	public void onFriendDelete(String id) {}
}
