package listener.friend;

import data.Friend;

public class FriendChangeAdapter implements FriendChangeListener {
	@Override
	public void onInsertFriend(Friend insert) {}
	@Override
	public void onDeleteFriend(Friend delete) {}
}
