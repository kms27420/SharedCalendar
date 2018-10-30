package view.frame;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameMoveAdapter extends MouseAdapter {
	private int previousX, previousY;
	
	FrameMoveAdapter() {}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Window frame = getWindow(e);
		if(frame == null)	return;
		int movedX = e.getXOnScreen() - previousX;
		int movedY = e.getYOnScreen() - previousY;
		previousX = e.getXOnScreen();
		previousY = e.getYOnScreen();
		frame.setLocation(frame.getX()+movedX, frame.getY()+movedY);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		previousX = e.getXOnScreen();
		previousY = e.getYOnScreen();
	}
	
	private Window getWindow(MouseEvent e) {
		Component window = e.getComponent();
		while(window!=null && !(window instanceof Window)) {
			window = window.getParent();
		}
		if(window==null)	return null;
		return (Window)window;
	}
}
