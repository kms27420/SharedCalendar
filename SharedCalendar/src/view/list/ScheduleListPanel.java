package view.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Schedule;
import listener.view.friend.UnsharedFriendSearchListener;
import listener.view.schedule.ScheduleSelectListener;
import model.DataStore;
import util.ColorUtil;
import util.FontUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.abv.LoginStatusView;
import view.abv.SchedulesView;
import view.customized.ActionCallablePanel;
import view.customized.CommonScrollPane;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.UnderlineLabel;
import view.popup.schedule.ScheduleDeletePanel;
import view.popup.schedule.ScheduleInsertPanel;
import view.popup.schedule.ScheduleUpdatePanel;

public class ScheduleListPanel extends TransparentPanel implements SchedulesView, ActionListener, LoginStatusView {
	private List<Schedule> schedules;
	private ScheduleSelectListener scheduleSelectListener = i->{};
	private UnsharedFriendSearchListener unsharedFriendSearchListener = i->{};
	
	public ScheduleListPanel() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(new CommonScrollPane(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		add(new ButtonsView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
	}
	
	public void setScheduleSelectListener(ScheduleSelectListener l) {
		this.scheduleSelectListener = l;
	}
	
	public void setUnsharedFriendSearchListener(UnsharedFriendSearchListener l) {
		unsharedFriendSearchListener = l;
	}
	
	@Override
	public void showSchedules(List<Schedule> schedules) {
		clickedIdx = -1;
		this.schedules = schedules;
		((CommonScrollPane)getComponent(0)).getContentPane().removeAll();
		for(Schedule s : schedules)
			((CommonScrollPane)getComponent(0)).getContentPane().add(new ScheduleView(s.getStartTime().getValidString(), s.getTitle()));
		for(Component c : ((CommonScrollPane)getComponent(0)).getContentPane().getComponents())
			((ScheduleView)c).addActionListener(this);
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof ScheduleView)	
			onScheduleViewClick((ScheduleView)e.getSource()); 
		else if(e.getSource() instanceof DeleteButton)	
			onDeleteButtonClick();
		else if(e.getSource()==((PaddingView)getComponent(1)).getContentPane().getComponent(0))	
			onUpdateButtonClick();
		else if(e.getSource()==((PaddingView)getComponent(1)).getContentPane().getComponent(1))	
			onInsertButtonClick();
		else if(e.getSource()==((PaddingView)getComponent(1)).getContentPane().getComponent(2))	
			onShareButtonClick();
		else	return;
	}
	
	@Override
	public void showLoginStatus(boolean isLogin) {
		((PaddingView)getComponent(1)).getContentPane().getComponent(2).setEnabled(isLogin);
	}

	private int clickedIdx=-1;
	private void onScheduleViewClick(ScheduleView clickedView) {
		for(int i=0; i<((CommonScrollPane)getComponent(0)).getContentPane().getComponentCount(); i++) {
			if(clickedView==getScheduleView(i)) {
				if(clickedIdx==i)	scheduleSelectListener.onScheduleSelect(clickedIdx);
				else {
					if(clickedIdx>=0)	getScheduleView(clickedIdx).showClickedState(false);
					getScheduleView(i).showClickedState(true);
					revalidate();
					repaint();
					clickedIdx = i;
				}
				return;
			}
		}
	}
	
	private void onDeleteButtonClick() {
		((ScheduleDeletePanel)SubViewType.SCHEDULE_DELETE.VIEW).setScheduleIdx(clickedIdx);
		((ScheduleDeletePanel)SubViewType.SCHEDULE_DELETE.VIEW).setAlert("정말 '" + getScheduleView(clickedIdx).getTitle()+"' 일정을 삭제하시겠습니까?");
		WindowShower.INSTANCE.showSubWindow(SubViewType.SCHEDULE_DELETE);
	}
	
	private void onUpdateButtonClick() {
		if(clickedIdx<0)	return;
		((ScheduleUpdatePanel)SubViewType.SCHEDULE_UPDATE.VIEW).showInitedView(clickedIdx, schedules.get(clickedIdx));
		WindowShower.INSTANCE.showSubWindow(SubViewType.SCHEDULE_UPDATE);
	}
	
	private void onInsertButtonClick() {
		((ScheduleInsertPanel)SubViewType.SCHEDULE_INSERT.VIEW).showInitedView(DataStore.getBeingShownYMD());
		WindowShower.INSTANCE.showSubWindow(SubViewType.SCHEDULE_INSERT);
	}
	
	private void onShareButtonClick() {
		if(clickedIdx<0)	return;
		unsharedFriendSearchListener.onUnsharedFriendSearch(clickedIdx);
	}
	
	private ScheduleView getScheduleView(int idx) {
		return (ScheduleView)((CommonScrollPane)getComponent(0)).getContentPane().getComponent(idx);
	}
	
	private class ScheduleView extends ActionCallablePanel {
		private ScheduleView(String time, String title) {
			setBackground(ColorUtil.getClickedViewColor());
			setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			add(createTimeView(time), new LinearConstraints(3, LinearSpace.MATCH_PARENT));
			add(createTitleView(title), new LinearConstraints(7, LinearSpace.MATCH_PARENT));
			add(createDeleteButtonView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		}
		
		private void showClickedState(boolean isClicked) {
			setOpaque(isClicked);
			((DeleteButton)((PaddingView)getComponent(2)).getContentPane().getComponent(0)).setVisible(isClicked);
		}
		
		private String getTitle() {
			return ((JLabel)getComponent(1)).getText();
		}
		
		private JLabel createTimeView(String time) {
			JLabel timeView = new TransparentLabel(time);
			timeView.setFont(FontUtil.createDefaultFont(15f));
			timeView.setHorizontalAlignment(JLabel.RIGHT);
			timeView.setVerticalAlignment(JLabel.CENTER);
			timeView.setForeground(ColorUtil.getLightGray());
			return timeView;
		}
		
		private JLabel createTitleView(String title) {
			JLabel titleView = new UnderlineLabel(title);
			titleView.setFont(FontUtil.createDefaultFont(15f));
			titleView.setHorizontalAlignment(JLabel.CENTER);
			titleView.setVerticalAlignment(JLabel.CENTER);
			titleView.setForeground(ColorUtil.getLightGray());
			return titleView;
		}
		
		private Container createDeleteButtonView() {
			PaddingView dbv = new PaddingView();
			dbv.getContentPane().add(new DeleteButton());
			((DeleteButton)dbv.getContentPane().getComponent(0)).setVisible(false);
			((DeleteButton)dbv.getContentPane().getComponent(0)).addActionListener(ScheduleListPanel.this);
			dbv.setOpaque(false);
			dbv.setPadding(0.2f, 0.2f, 0.5f, 0.2f);
			dbv.setMargin(0);
			return dbv;
		}
	}
	
	private class ButtonsView extends PaddingView {
		private ButtonsView() {
			getContentPane().add(createCommonBt("EDIT"));
			getContentPane().add(createCommonBt("+"));
			getContentPane().add(createCommonBt("SHARE"));
			setOpaque(false);
			setPadding(0.25f, 1/8f, 0.5f, 1/8f);
			setMargin(0.03f);
		}
		
		private RoundRectButton createCommonBt(String text) {
			RoundRectButton commonBt = new RoundRectButton(text);
			commonBt.setHorizontalAlignment(JLabel.CENTER);
			commonBt.setVerticalAlignment(JLabel.CENTER);
			commonBt.setNormalFg(Color.BLACK);
			commonBt.setFont(FontUtil.createDefaultFont(12f));
			commonBt.addActionListener(ScheduleListPanel.this);
			return commonBt;
		}
	}
}
