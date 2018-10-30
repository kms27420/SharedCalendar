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
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.frame.CustomDialog;
import view.frame.CustomFrame;
import view.frame.CustomWindow.LocationType;

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
		} else	MAIN_WINDOW.setVisible(true);
		
	}
	
	private Map<SubViewType, WindowView> SUB_VIEW_MAP = new HashMap<>();
	/**
	 * Show the view filled in sub window
	 * 
	 * @param view  View to show
	 */
	public void showSubWindow(WindowView view, SubViewType type) {
		switch(type) {
		case ALERT :
			new CustomDialog() {
				{
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					setTitleBarExist(true);
					getTitleBar().setExistOfButtonsView(false);
					getTitleBar().setBackground(new Color(64,64,64));
					getContentPane().setBackground(getTitleBar().getBackground());
					setResizable(false);
					getContentPane().removeAll();
					getContentPane().add(view);
					setContentPaneSize(view.getWindowSize());
					setLocation(LocationType.CENTER);
					setVisible(true);
				}
			};
			break;
		default :
			if(SUB_VIEW_MAP.containsKey(type))	SUB_VIEW_MAP.get(type).getWindow().setVisible(true);
			else {
				SUB_VIEW_MAP.put(type, view);
				new CustomDialog() {
					{
						setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						setTitleBarExist(true);
						getTitleBar().setExistOfButtonsView(false);
						getTitleBar().setBackground(new Color(64,64,64));
						getContentPane().setBackground(getTitleBar().getBackground());
						setResizable(false);
						getContentPane().removeAll();
						getContentPane().add(view);
						setContentPaneSize(view.getWindowSize());
						setLocation(LocationType.CENTER);
						setVisible(true);
					}
				};
			}
			break;
		}
	}
	
	public void hideSubWindow(SubViewType type) {
		if(!SUB_VIEW_MAP.containsKey(type)) return;
		SUB_VIEW_MAP.get(type).hideWindowView();
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
		JOIN, LOGIN, LOGOUT,
		YMD_INPUT, SEARCH,
		SCHEDULE_DETAIL, SCHEDULE_INSERT, SCHEDULE_UPDATE, SCHEDULE_DELETE,
		FRIEND_INSERT, FRIEND_DELETE,
		SHARE, UNSHARE,
		PROGRAM_EXIT,
		ALERT;
	}
}
