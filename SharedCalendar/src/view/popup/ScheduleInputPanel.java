package view.popup;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Schedule;
import data.Time;
import data.YMD;
import util.FontUtil;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.PaddingView;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.TransparentTextField;

public class ScheduleInputPanel extends TransparentPanel {
	public ScheduleInputPanel() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(createInputView(), new LinearConstraints(5, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
	}
	
	public void showInitedView(Schedule request) {
		((TransparentTextField)getPartition(0).getComponent(1)).setText(request.getTitle());
		((DateInputView)getPartition(1)).showInitedView(request.getYMD());
		((TimeInputView)getPartition(2)).showInitedView(request.getStartTime());
		((TimeInputView)getPartition(3)).showInitedView(request.getEndTime());
		((TransparentTextField)getPartition(4).getComponent(1)).setText(request.getContent());
	}
	
	public Schedule getInputedSchedule() {
		String title = (String)getInput(0);
		YMD ymd = (YMD)getInput(1);
		Time startTime = (Time)getInput(2);
		Time endTime = (Time)getInput(3);
		String content = (String)getInput(4);
		return new Schedule(title, content, ymd, startTime, endTime);
	}
	
	public void setCommonButtonsListener(CommonButtonsListener l) {
		((CommonButtonsView)getComponent(1)).setCommonButtonsListener(l);
	}
	
	private Container getPartition(int idx) {
		return (Container)((Container)(Container)((PaddingView)getComponent(0)).getContentPane().getComponent(0)).getComponent(idx);
	}
	
	private Object getInput(int idx) {
		Container  src = getPartition(idx);
		if(src instanceof DateInputView)	return ((DateInputView)src).getInputedYMD();
		if(src instanceof TimeInputView)	return ((TimeInputView)src).getInputedTime();
		return ((TransparentTextField)src.getComponent(1)).getText();
	}
	
	private Container createCommonView(String title) {
		TransparentPanel cv = new TransparentPanel();
		cv.setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		cv.add(createTitleLabel(title), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		cv.add(new TransparentTextField(false), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		return cv;
	}
	
	private JLabel createTitleLabel(String title) {
		JLabel tl = new TransparentLabel(title);
		tl.setHorizontalAlignment(JLabel.CENTER);
		tl.setVerticalAlignment(JLabel.CENTER);
		tl.setForeground(Color.WHITE);
		tl.setFont(FontUtil.createDefaultFont(15f));
		return tl;
	}
	
	private Container createButtonsView() {
		CommonButtonsView bv = new CommonButtonsView();
		bv.setOpaque(false);
		bv.setButtonsType(ButtonsType.BOTH);
		bv.setPadding(0.2f, 0.2f, 0.5f, 0.2f);
		bv.setMargin(0.1f);
		return bv;
	}
	
	private Container createInputView() {
		PaddingView iv = new PaddingView();
		iv.setOpaque(false);
		iv.setPadding(0.1f, 0.1f, 0.1f, 0.1f);
		TransparentPanel pane = new TransparentPanel();
		pane.setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		pane.add(createCommonView("TITLE"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		pane.add(new DateInputView("DATE"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		pane.add(new TimeInputView("BEGIN"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		pane.add(new TimeInputView("FINISH"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		pane.add(createCommonView("CONTENT"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		iv.getContentPane().add(pane);
		return iv;
	}
	
	private class DateInputView extends TransparentPanel {
		private DateInputView(String title) {
			setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			add(createTitleLabel(title), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
			add(new YMDInputPanel(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		}
		
		private YMD getInputedYMD() {
			return ((YMDInputPanel)getComponent(1)).getInputedYMD();
		}
		
		private void showInitedView(YMD ymd) {
			((YMDInputPanel)getComponent(1)).showInitedView();
		}
	}
	
	private class TimeInputView extends TransparentPanel {
		private TimeInputView(String title) {
			setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			add(createTitleLabel(title), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
			add(new TimeInputPanel(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		}
		
		private Time getInputedTime() {
			return ((TimeInputPanel)getComponent(1)).getInputedTime();
		}
		
		private void showInitedView(Time time) {
			((TimeInputPanel)getComponent(1)).showInitedView(time);
		}
	}
}
