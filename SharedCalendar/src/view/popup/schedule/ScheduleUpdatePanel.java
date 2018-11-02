package view.popup.schedule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import data.Schedule;
import listener.view.schedule.ScheduleUpdateListener;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.popup.common.AlertView;
import view.popup.common.ScheduleInputPanel;

public class ScheduleUpdatePanel extends WindowView implements CommonButtonsListener {
	private ScheduleUpdateListener scheduleUpdateListener = (i,t,c,y,s,e)->{};
	private int idx;
	
	public ScheduleUpdatePanel() {
		setOpaque(false);
		setLayout(new BorderLayout());
		add(new ScheduleInputPanel());
		((ScheduleInputPanel)getComponent(0)).setCommonButtonsListener(this);
	}
	
	public void showInitedView(int idx, Schedule request) {
		this.idx = idx;
		((ScheduleInputPanel)getComponent(0)).showInitedView(request);
	}
	
	public void setScheduleUpdateListener(ScheduleUpdateListener l) {
		scheduleUpdateListener = l;
	}
	
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		Schedule input = ((ScheduleInputPanel)getComponent(0)).getInputedSchedule();
		if(!input.isValid()) {
			if(!input.getYMD().isValid())	((AlertView)SubViewType.ALERT.VIEW).setAlert("년도가 올바르지 않습니다. 다시 입력해주세요.");
			else if(!input.getStartTime().isValid())	((AlertView)SubViewType.ALERT.VIEW).setAlert("시작 시간이 올바르지 않습니다. 다시 입력해주세요.");
			else	((AlertView)SubViewType.ALERT.VIEW).setAlert("종료 시간이 올바르지 않습니다. 다시 입력해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
		} else scheduleUpdateListener.onScheduleUpdate(idx, input.getTitle(), input.getContent(), input.getYMD(), input.getStartTime(), input.getEndTime());
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.3f, 0.4f);
	}
}
