package view.list;

import java.awt.Container;

import javax.swing.JPanel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import listener.view.ListMenuSelectListener;
import util.ColorUtil;
import view.abv.LoginStatusView;

public class SelectedListViewPanel extends JPanel implements ListMenuSelectListener, LoginStatusView {
	private MenuType selectedMenu=MenuType.EVENTS;
	
	public SelectedListViewPanel() {
		setBackground(ColorUtil.getDarkColor());
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(new ListMenuSelectPanel(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		showSelectedMenuView();
		((ListMenuSelectPanel)getComponent(0)).setListMenuSelectListener(this);
	}
	
	public ScheduleListPanel getSchedulesView() {
		return (ScheduleListPanel)MenuType.EVENTS.VIEW;
	}
	
	public FriendListPanel getFriendsView() {
		return (FriendListPanel)MenuType.FRIENDS.VIEW;
	}
	
	@Override
	public void showLoginStatus(boolean isLogin) {
		((ListMenuSelectPanel)getComponent(0)).showLoginStatus(isLogin);
		((ScheduleListPanel)MenuType.EVENTS.VIEW).showLoginStatus(isLogin);
	}

	private void showSelectedMenuView() {
		if(getComponentCount()>1)	remove(1);
		((ListMenuSelectPanel)getComponent(0)).showMenuTypeView(selectedMenu);
		add(selectedMenu.VIEW, new LinearConstraints(4, LinearSpace.MATCH_PARENT));
		revalidate();
		repaint();
	}
	
	@Override
	public void onListMenuSelect(MenuType menu) {
		if(selectedMenu==menu)	return;
		this.selectedMenu = menu;
		showSelectedMenuView();
	}
	
	public static enum MenuType {
		EVENTS(new ScheduleListPanel()), FRIENDS(new FriendListPanel());
		
		private final Container VIEW;
		private MenuType(Container view) {
			VIEW = view;
		}
	}
}
