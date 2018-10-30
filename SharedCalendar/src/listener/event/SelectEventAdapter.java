package listener.event;

import data.YMD;

public class SelectEventAdapter implements SelectEventListener {
	@Override
	public void onSchedulesSelectByYMD(YMD ymd) {}
	@Override
	public void onSchedulesSelectBySearch(String search) {}
	@Override
	public void onSelectFriends() {}
	@Override
	public void onSelectFriend(String id) {}
}
