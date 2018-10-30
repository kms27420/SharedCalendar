package listener.event;

import data.Schedule;
import data.SharedSchedule;

public interface UpdateEventListener {
	public void onScheduleInsert(Schedule s);
	public void onScheduleUpdate(Schedule s);
	public void onScheduleDelete(Schedule s);
	public void onShare(SharedSchedule s);
	public void onUnshare(SharedSchedule s);
	public void onFriendInsert(String id);
	public void onFriendDelete(String id);
}
