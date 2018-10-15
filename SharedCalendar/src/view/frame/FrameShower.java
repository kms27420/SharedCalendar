package view.frame;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window.Type;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import view.frame.CustomFrame.LocationType;

public class FrameShower {
	public static final FrameShower INSTANCE = new FrameShower();

	private final CustomFrame MAIN_FRAME = new CustomFrame();
	private final CustomFrame SUB_FRAME = new CustomFrame();
 
	private FrameShower() {
		displayTrayIcon();
		MAIN_FRAME.setType(Type.UTILITY); // remove MainFrame's icon from task bar
		MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MAIN_FRAME.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				MAIN_FRAME.setOpacity(0.5f);
			}
			@Override
			public void focusGained(FocusEvent e) {
				MAIN_FRAME.setOpacity(1.0f);
			}
		});
		MAIN_FRAME.setSizeFromScreen(0.4f, 0.5f);
		MAIN_FRAME.setLocation(LocationType.NORTH_EAST);
	}

	/**
	 * Show the view filled in main frame
	 * 
	 * @param view  View to show
	 */
	public void showMainFrame(Component view) {
		MAIN_FRAME.getContentPane().removeAll();
		MAIN_FRAME.getContentPane().add(view);
		if (MAIN_FRAME.isVisible()) {
			MAIN_FRAME.getContentPane().revalidate();
			MAIN_FRAME.getContentPane().repaint();
		} else	MAIN_FRAME.setVisible(true);
	}
	/**
	 * Hide a main view
	 */
	public void hideMainFrame() {
		if (SystemTray.isSupported())	MAIN_FRAME.setVisible(false);
		else	MAIN_FRAME.setState(JFrame.ICONIFIED);
	}
	/**
	 * Show the view filled in sub frame
	 * 
	 * @param view  View to show
	 */
	public void showSubView(Component view) {
		SUB_FRAME.getContentPane().removeAll();
		SUB_FRAME.getContentPane().add(view);
		if (SUB_FRAME.isVisible()) {
			SUB_FRAME.getContentPane().revalidate();
			SUB_FRAME.getContentPane().repaint();
		} else	SUB_FRAME.setVisible(true);
	}
	
	private void displayTrayIcon() {
		if (!SystemTray.isSupported())	return;
		
		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem = new MenuItem("Exit");
		defaultItem.addActionListener(e -> System.exit(0));
		popup.add(defaultItem);
		
		TrayIcon trayIcon = new TrayIcon(new ImageIcon("c:/users/kwon/desktop/line.png").getImage(), "Shared Calendar", popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(e->MAIN_FRAME.setVisible(true));

		try { SystemTray.getSystemTray().add(trayIcon); } catch (AWTException e) {}
	}
}
