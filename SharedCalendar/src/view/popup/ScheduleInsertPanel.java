package view.popup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import data.Schedule;
import data.Time;
import data.YMD;
import listener.event.UpdateEventAdapter;
import listener.event.UpdateEventListener;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;

public class ScheduleInsertPanel extends WindowView implements CommonButtonsListener {
	private UpdateEventListener updateEventListener = new UpdateEventAdapter();
	
	public ScheduleInsertPanel() {
		setOpaque(false);
		setLayout(new BorderLayout());
		add(new ScheduleInputPanel());
		((ScheduleInputPanel)getComponent(0)).setCommonButtonsListener(this);
	}
	
	public void showInitedView(YMD ymd) {
		((ScheduleInputPanel)getComponent(0)).showInitedView(new Schedule("", "", ymd, new Time(0,0), new Time(0,0)));
	}
	
	public void setUpdateEventListener(UpdateEventListener l) {
		updateEventListener = l;
	}
	
	private AlertView alertView = new AlertView(ButtonsType.CHECK_ONLY);
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		Schedule input = ((ScheduleInputPanel)getComponent(0)).getInputedSchedule();
		if(!input.isValid()) {
			if(!input.getYMD().isValid())  alertView.setAlert("년도가 올바르지 않습니다. 다시 입력해주세요.");
			else if(!input.getStartTime().isValid())	alertView.setAlert("시작 시간이 올바르지 않습니다. 다시 입력해주세요.");
			else	alertView.setAlert("종료 시간이 올바르지 않습니다. 다시 입력해주세요.");
			WindowShower.INSTANCE.showSubWindow(alertView, SubViewType.ALERT);
		} else	updateEventListener.onScheduleInsert(input);
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.3f, 0.3f);
	}
}
