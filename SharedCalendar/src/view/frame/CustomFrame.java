package view.frame;

import java.awt.Container;

import javax.swing.JFrame;

import util.ScreenSizeUtil;

public class CustomFrame extends CustomWindow {
	public CustomFrame() {
		super(new JFrame());
		// set frame's size & location
		WINDOW.setMinimumSize(ScreenSizeUtil.getScaledSize(1/8f, 1/8f));
		WINDOW.setMaximumSize(ScreenSizeUtil.getScreenSize());
	}

	@Override
	public void setDefaultCloseOperation(int operation) {
		((JFrame)WINDOW).setDefaultCloseOperation(operation);
	}

	@Override
	protected Container getSuperContentPane() {
		return ((JFrame)WINDOW).getContentPane();
	}

	@Override
	protected void setUndecorated(boolean undecorated) {
		((JFrame)WINDOW).setUndecorated(undecorated);
	}
}
