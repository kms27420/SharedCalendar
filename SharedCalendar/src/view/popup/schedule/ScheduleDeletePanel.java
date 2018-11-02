package view.popup.schedule;

import listener.view.schedule.ScheduleDeleteListener;
import view.customized.CommonButtonsView.ButtonsType;
import view.popup.common.AlertView;

public class ScheduleDeletePanel extends AlertView {
	private int scheduleIdx;
	private ScheduleDeleteListener scheduleDeleteListener = i->{};
	
	public ScheduleDeletePanel() {
		super(ButtonsType.BOTH);
		setAlertCheckListener(()->scheduleDeleteListener.onScheduleDelete(scheduleIdx));
	}
	
	public void setScheduleIdx(int scheduleIdx) {
		this.scheduleIdx = scheduleIdx;
	}
	
	public void setScheduleDeleteListener(ScheduleDeleteListener l) {
		scheduleDeleteListener = l;
	}
}
