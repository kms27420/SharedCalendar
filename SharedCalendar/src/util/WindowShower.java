package util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.Window.Type;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.customized.CommonButtonsView.ButtonsType;
import view.frame.CustomDialog;
import view.frame.CustomFrame;
import view.frame.CustomWindow.LocationType;
import view.popup.FriendDeletePanel;
import view.popup.FriendSearchPanel;
import view.popup.JoinPanel;
import view.popup.LoginPanel;
import view.popup.ProgramExitPanel;
import view.popup.YMDSelectPanel;
import view.popup.common.AlertView;
import view.popup.schedule.ScheduleDeletePanel;
import view.popup.schedule.ScheduleDetailView;
import view.popup.schedule.ScheduleInsertPanel;
import view.popup.schedule.ScheduleSearchPanel;
import view.popup.schedule.ScheduleSharePanel;
import view.popup.schedule.ScheduleUnsharePanel;
import view.popup.schedule.ScheduleUpdatePanel;

public class WindowShower {
	public static final WindowShower INSTANCE = new WindowShower();

	private final CustomFrame MAIN_WINDOW = new CustomFrame();
	
	private WindowShower() {
		displayTrayIcon();
		MAIN_WINDOW.setType(Type.UTILITY); // remove MainFrame's icon from task bar
		MAIN_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MAIN_WINDOW.setTitleBarExist(true);
		MAIN_WINDOW.setResizable(true);
		MAIN_WINDOW.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				MAIN_WINDOW.setOpacity(0.5f);
			}
			@Override
			public void focusGained(FocusEvent e) {
				MAIN_WINDOW.setOpacity(1.0f);
			}
		});
	}

	/**
	 * Show the view filled in main window
	 * 
	 * @param view  View to show
	 */
	public void showMainWindow(WindowView view) {
		MAIN_WINDOW.getContentPane().removeAll();
		MAIN_WINDOW.getContentPane().add(view);
		MAIN_WINDOW.setContentPaneSize(view.getWindowSize());
		MAIN_WINDOW.setLocation(LocationType.NORTH_EAST);
		
		if (MAIN_WINDOW.isVisible()) {
			MAIN_WINDOW.getContentPane().revalidate();
			MAIN_WINDOW.getContentPane().repaint();
		} else {
			SwingUtilities.invokeLater(()->{
				MAIN_WINDOW.setVisible(true);
			});
		}
	}
	
	/**
	 * Show the view filled in sub window
	 * 
	 * @param view  View to show
	 */
	public void showSubWindow(SubViewType type) {
		SwingUtilities.invokeLater(()->{
			new CustomDialog() {
				{
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					setTitleBarExist(true);
					getTitleBar().setExistOfButtonsView(false);
					getTitleBar().setBackground(new Color(64,64,64));
					getContentPane().setBackground(getTitleBar().getBackground());
					setResizable(false);
					getContentPane().removeAll();
					getContentPane().add(type.VIEW);
					setContentPaneSize(type.VIEW.getWindowSize());
					setLocation(LocationType.CENTER);
					((JPanel)getSuperContentPane()).setBorder(null);
					setVisible(true);
				}
			};
		});
	}
	
	public void hideSubWindow(SubViewType type) {
		type.VIEW.hideWindowView();
	}
	
	private void displayTrayIcon() {
		if (!SystemTray.isSupported())	return;
		
		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem = new MenuItem("Exit");
		defaultItem.addActionListener(e -> System.exit(0));
		popup.add(defaultItem);
		
		TrayIcon trayIcon = new TrayIcon(new ImageIcon("c:/users/kwon/desktop/line.png").getImage(), "Shared Calendar", popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(e->MAIN_WINDOW.setVisible(true));

		try { SystemTray.getSystemTray().add(trayIcon); } catch (AWTException e) {}
	}
	
	public static abstract class WindowView extends JPanel {
		protected WindowView() {
			addHierarchyListener(new HierarchyListener() {
				@Override
				public void hierarchyChanged(HierarchyEvent e) {
					if(e.getChangeFlags() != HierarchyEvent.SHOWING_CHANGED)	return;
					if(!getWindow().isVisible())	return;
					showInitedView();
				}
			});
		}
		/**
		 * If you want to show initialized view when this view shown in window then override this method.
		 */
		protected void showInitedView(){};
		protected abstract Dimension getWindowSize();
		protected final void hideWindowView() {
			Window window = getWindow();
			if(window==null)	return;
			getWindow().setVisible(false);
			window = null;
		}
		private Window getWindow() {
			Component window = getParent();
			while(window!=null && !(window instanceof Window)) {
				window = window.getParent();
			}
			if(window==null)	return null;
			return (Window)window;
		}
	}
	
	public static enum SubViewType {
		JOIN(new JoinPanel()), LOGIN(new LoginPanel()), LOGOUT(new AlertView("정말 로그아웃 하시겠습니까?", ButtonsType.BOTH)),
		YMD_SELECT(new YMDSelectPanel()), SEARCH(new ScheduleSearchPanel()),
		SCHEDULE_DETAIL(new ScheduleDetailView()), SCHEDULE_INSERT(new ScheduleInsertPanel()), 
		SCHEDULE_UPDATE(new ScheduleUpdatePanel()), SCHEDULE_DELETE(new ScheduleDeletePanel()),
		FRIEND_INSERT(new FriendSearchPanel()), FRIEND_DELETE(new FriendDeletePanel()),
		SHARE(new ScheduleSharePanel()), UNSHARE(new ScheduleUnsharePanel()),
		PROGRAM_EXIT(new ProgramExitPanel()),
		ALERT(new AlertView(ButtonsType.CHECK_ONLY));
		
		public final WindowView VIEW;
		private SubViewType(WindowView view) {
			VIEW = view;
		}
	}
}
