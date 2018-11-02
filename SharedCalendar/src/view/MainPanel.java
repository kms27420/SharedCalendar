package view;

import java.awt.Container;
import java.awt.Dimension;
import java.util.List;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Friend;
import data.Schedule;
import data.YMD;
import listener.view.ViewEventListener;
import util.ScreenSizeUtil;
import util.WindowShower.WindowView;
import view.abv.MainView;
import view.calendar.CalendarPanel;
import view.customized.TransparentPanel;
import view.list.FriendListPanel;
import view.list.ScheduleListPanel;
import view.list.SelectedListViewPanel;

public class MainPanel extends WindowView implements MainView {
	public MainPanel() {
		setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		add(createLeftView(), new LinearConstraints(3, LinearSpace.MATCH_PARENT));
		add(new SelectedListViewPanel(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
	}
	
	@Override
	public void showLoginStatus(boolean isLogin) {
		((BottomNaviView)((Container)getComponent(0)).getComponent(1)).showLoginStatus(isLogin);
		((SelectedListViewPanel)getComponent(1)).showLoginStatus(isLogin);
	}
	
	@Override
	public void showYMD(YMD ymd) {
		getYMDView().showYMD(ymd);
	}
	
	@Override
	public void showSchedules(List<Schedule> schedules) {
		getSchedulesView().showSchedules(schedules);
	}

	@Override
	public void showFriends(List<Friend> friends) {
		getFriendsView().showFriends(friends);
	}

	private CalendarPanel getYMDView() {
		return (CalendarPanel)((Container)getComponent(0)).getComponent(0);
	}
	
	private ScheduleListPanel getSchedulesView() {
		return ((SelectedListViewPanel)getComponent(1)).getSchedulesView();
	}
	
	private FriendListPanel getFriendsView() {
		return ((SelectedListViewPanel)getComponent(1)).getFriendsView();
	}
	
	public void setViewEventListener(ViewEventListener l) {
		getYMDView().setYMDSelectListener(l);
		getSchedulesView().setScheduleSelectListener(l);
		getSchedulesView().setUnsharedFriendSearchListener(l);
	}
	
	private Container createLeftView() {
		TransparentPanel lv = new TransparentPanel();
		lv.setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		lv.add(new CalendarPanel(), new LinearConstraints(4, LinearSpace.MATCH_PARENT));
		lv.add(new BottomNaviView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		return lv;
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.5f, 0.5f);
	}
}
