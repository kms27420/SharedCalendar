package view.popup;

import listener.view.friend.FriendDeleteListener;
import view.customized.CommonButtonsView.ButtonsType;
import view.popup.common.AlertView;

public class FriendDeletePanel extends AlertView {
	private int friendIdx;
	private FriendDeleteListener friendDeleteListener = i->{};
	
	public FriendDeletePanel() {
		super(ButtonsType.BOTH);
		setAlertCheckListener(()->friendDeleteListener.onFriendDelete(friendIdx));
	}
	
	public void setFriendDeleteListener(FriendDeleteListener l) {
		friendDeleteListener = l;
	}
	
	public void setFriendIdx(int friendIdx) {
		this.friendIdx = friendIdx;
	}
}
