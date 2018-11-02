package view.popup.schedule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import data.Schedule;
import data.Time;
import data.YMD;
import listener.view.schedule.ScheduleInsertListener;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.popup.common.AlertView;
import view.popup.common.ScheduleInputPanel;

public class ScheduleInsertPanel extends WindowView implements CommonButtonsListener {
	private ScheduleInsertListener scheduleInsertListener = (t,c,y,s,e)->{};
	
	public ScheduleInsertPanel() {
		setOpaque(false);
		setLayout(new BorderLayout());
		add(new ScheduleInputPanel());
		((ScheduleInputPanel)getComponent(0)).setCommonButtonsListener(this);
	}
	
	public void showInitedView(YMD ymd) {
		((ScheduleInputPanel)getComponent(0)).showInitedView(new Schedule("", "", ymd, new Time(0,0), new Time(0,0)));
	}
	
	public void setScheduleInsertListener(ScheduleInsertListener l) {
		scheduleInsertListener = l;
	}
	
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		Schedule input = ((ScheduleInputPanel)getComponent(0)).getInputedSchedule();
		if(!input.isValid()) {
			if(!input.getYMD().isValid())  ((AlertView)SubViewType.ALERT.VIEW).setAlert("년도가 올바르지 않습니다. 다시 입력해주세요.");
			else if(!input.getStartTime().isValid())	((AlertView)SubViewType.ALERT.VIEW).setAlert("시작 시간이 올바르지 않습니다. 다시 입력해주세요.");
			else	((AlertView)SubViewType.ALERT.VIEW).setAlert("종료 시간이 올바르지 않습니다. 다시 입력해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
		} else	scheduleInsertListener.onScheduleInsert(input.getTitle(), input.getContent(), input.getYMD(), input.getStartTime(), input.getEndTime());
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.25f, 0.34f);
	}
}
