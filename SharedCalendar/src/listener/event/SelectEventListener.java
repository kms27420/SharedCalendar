package listener.event;

import data.YMD;

public interface SelectEventListener {
	public void onSchedulesSelectByYMD(YMD ymd);
	public void onSchedulesSelectBySearch(String search);
	public void onSelectFriends();
	public void onSelectFriend(String id);
}
