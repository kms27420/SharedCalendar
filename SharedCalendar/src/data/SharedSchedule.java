package data;

import java.util.List;

public class SharedSchedule extends Schedule {
	private List<Friend> sharingFriends;
	
	public SharedSchedule() {}
	
	public SharedSchedule(int scheduleID, String title, String content, YMD ymd, Time startTime, Time endTime, List<Friend> sharingFriends) {
		super(scheduleID, title, content, ymd, startTime, endTime);
		setSharingFriends(sharingFriends);
	}
	
	public void setSharingFriends(List<Friend> sharingFriends) {
		this.sharingFriends = sharingFriends;
	}
	
	public List<Friend> getSharingFriends() {
		return sharingFriends;
	}
}
