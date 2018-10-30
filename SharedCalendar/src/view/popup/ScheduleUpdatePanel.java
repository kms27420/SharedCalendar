package view.popup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import data.Schedule;
import data.Time;
import data.YMD;
import listener.event.UpdateEventAdapter;
import listener.event.UpdateEventListener;
import util.ColorUtil;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;

public class ScheduleUpdatePanel extends WindowView implements CommonButtonsListener {
	private UpdateEventListener updateEventListener = new UpdateEventAdapter();
	private Schedule request;
	
	public ScheduleUpdatePanel() {
		setOpaque(false);
		setLayout(new BorderLayout());
		add(new ScheduleInputPanel());
		((ScheduleInputPanel)getComponent(0)).setCommonButtonsListener(this);
	}
	
	public void showInitedView(Schedule request) {
		this.request = request;
		((ScheduleInputPanel)getComponent(0)).showInitedView(request);
	}
	
	public void setUdateEventListener(UpdateEventListener l) {
		updateEventListener = l;
	}
	
	private AlertView alertView = new AlertView(ButtonsType.CHECK_ONLY);
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		Schedule input = ((ScheduleInputPanel)getComponent(0)).getInputedSchedule();
		input.setScheduleID(request.getScheduleID());
		if(input.isContentsEquals(request)) {
			hideWindowView();
			return;
		}
		if(!input.isValid()) {
			if(!input.getYMD().isValid())	alertView.setAlert("년도가 올바르지 않습니다. 다시 입력해주세요.");
			else if(!input.getStartTime().isValid())	alertView.setAlert("시작 시간이 올바르지 않습니다. 다시 입력해주세요.");
			else	alertView.setAlert("종료 시간이 올바르지 않습니다. 다시 입력해주세요.");
			WindowShower.INSTANCE.showSubWindow(alertView, SubViewType.ALERT);
		} else updateEventListener.onScheduleUpdate(input);
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.3f, 0.4f);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(new ScheduleUpdatePanel());
		f.getContentPane().setBackground(ColorUtil.getDarkColor());
		((ScheduleUpdatePanel)f.getContentPane().getComponent(0)).showInitedView(new Schedule("제목없음", "내용없음", new YMD(2018,10,22), new Time(9,3), new Time(10,3)));
		f.setBounds(400, 400, 500, 600);
		f.setVisible(true);
	}
}
