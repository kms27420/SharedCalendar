package processor;

import data.Friend;
import data.Schedule;

public interface DBProcessor {
	public void loadDBData();
	public void insertSchedule(Schedule insert);
	public void updateSchedules(Schedule update);
	public void deleteSchedules(Schedule delete);
	public void insertFriend(Friend friend);
	public void deleteFriend(Friend friend);
	public void searchFriend(String keyword);
	public void share(int scheduleID, String[] guests);
	public void unshare(int scheduleID, String guestID);
}
