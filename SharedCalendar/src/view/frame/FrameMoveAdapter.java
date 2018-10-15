package view.frame;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class FrameMoveAdapter extends MouseAdapter {
	private int previousX, previousY;
	 
	@Override
	public void mouseDragged(MouseEvent e) {
		JFrame frame = getFrame(e);
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
	
	private JFrame getFrame(MouseEvent e) {
		Component frame = e.getComponent();
		if(frame==null || !(frame instanceof Container))	return null;
		while(frame!=null && !(frame instanceof JFrame)) {
			frame = frame.getParent();
		}
		return (JFrame)frame;
	}
}
