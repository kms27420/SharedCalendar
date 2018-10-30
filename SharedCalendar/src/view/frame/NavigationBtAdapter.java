package view.frame;

import java.awt.Component;
import java.awt.SystemTray;
import java.awt.Window;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import util.WindowShower;
import util.WindowShower.SubViewType;
import view.frame.TitleBar.ExitButton;
import view.frame.TitleBar.MinimizingButton;
import view.popup.ProgramExitPanel;

public class NavigationBtAdapter implements ActionListener {
	NavigationBtAdapter() {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Window window = getWindow(e.getSource());
		if(e.getSource() instanceof ExitButton)	{
			if(window instanceof JFrame) {
				switch(((JFrame)window).getDefaultCloseOperation()) {
				case JFrame.EXIT_ON_CLOSE :
					WindowShower.INSTANCE.showSubWindow(new ProgramExitPanel(), SubViewType.PROGRAM_EXIT);
					break;
				default : window.dispose();
					break;
				}
			} else if(window instanceof JDialog) {
				switch(((JDialog)window).getDefaultCloseOperation()) {
				case JDialog.EXIT_ON_CLOSE :
					System.exit(0);
					break;
				default : window.dispose();
					break;
				}
			} else ;
		} else if(e.getSource() instanceof MinimizingButton && window instanceof JFrame) {
			if(window.getType()==Type.UTILITY && SystemTray.isSupported())	
				window.setVisible(false);
			else	((JFrame)window).setState(JFrame.ICONIFIED);
		} else ;
		window = null;
	}
	
	private Window getWindow(Object naviBt) {
		Component window = ((Component)naviBt).getParent();
		while(window!=null && !(window instanceof Window)) {
			window = window.getParent();
		}
		if(window==null)	return null;
		return (Window)window;
	}
}
