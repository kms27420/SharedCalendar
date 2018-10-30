package view.popup;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Schedule;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;

public class ScheduleDetailView extends WindowView implements CommonButtonsListener {
	public ScheduleDetailView() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(new InfoView("TITLE"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(new InfoView("DATE"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(new InfoView("BEGIN"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(new InfoView("FINISH"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(new InfoView("CONTENT"), new LinearConstraints(3, LinearSpace.MATCH_PARENT));
		add(createCommonButtonsView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		
		((CommonButtonsView)getComponent(5)).setCommonButtonsListener(this);
	}
	
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {}

	public void showScheduleDetail(Schedule schedule) {
		((InfoView)getComponent(0)).showContent(schedule.getTitle());
		((InfoView)getComponent(1)).showContent(schedule.getYMD().getString());
		((InfoView)getComponent(2)).showContent(schedule.getStartTime().getValidString());
		((InfoView)getComponent(3)).showContent(schedule.getEndTime().getValidString());
		((InfoView)getComponent(4)).showContent(schedule.getContent());
	}
	
	private Container createCommonButtonsView() {
		CommonButtonsView cv = new CommonButtonsView();
		cv.setPadding(0.1f, 0.4f, 0.3f, 0.4f);
		cv.setButtonsType(ButtonsType.CHECK_ONLY);
		return cv;
	}
	
	private class InfoView extends TransparentPanel {
		private InfoView(String title) {
			setLayout(new LinearLayout(Orientation.HORIZONTAL,0));
			add(createLabel(title, JLabel.RIGHT), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
			add(createLabel("", JLabel.CENTER), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		}
		
		private JLabel createLabel(String text, int horizontalAlignment) {
			JLabel l = new TransparentLabel(text);
			l.setHorizontalAlignment(horizontalAlignment);
			l.setVerticalAlignment(JLabel.CENTER);
			l.setForeground(Color.WHITE);
			l.setFont(FontUtil.createDefaultFont(15f));
			return l;
		}
		
		private void showContent(String content) {
			((JLabel)getComponent(1)).setText(content);
		}
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.3f, 0.4f);
	}
}
