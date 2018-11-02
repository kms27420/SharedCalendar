package view.popup.schedule;

import listener.view.sharing.UnshareListener;
import view.customized.CommonButtonsView.ButtonsType;
import view.popup.common.AlertView;

public class ScheduleUnsharePanel extends AlertView {
	private UnshareListener unshareListener = (s, f)->{};
	private int scheduleIdx;
	private int friendIdx;
	public ScheduleUnsharePanel() {
		super("정말 일정 공유를 해제하시겠습니까?", ButtonsType.BOTH);
		setAlertCheckListener(()->unshareListener.onUnshare(scheduleIdx, friendIdx));
	}
	
	public void setScheduleIdx(int scheduleIdx) {
		this.scheduleIdx = scheduleIdx;
	}
	
	public void setFriendIdx(int friendIdx) {
		this.friendIdx = friendIdx;
	}
	
	public void setUnshareListener(UnshareListener l) {
		unshareListener = l;
	}
}
