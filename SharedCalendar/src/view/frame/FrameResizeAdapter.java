package view.frame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class FrameResizeAdapter extends MouseAdapter implements MouseMotionListener {
	private int previousX, previousY;
	private boolean isPressed;
	
	FrameResizeAdapter() {}
	 
	private int x, y;
	private boolean isWestSide, isEastSide, isNorthSide, isSouthSide;
	private Cursor def = new Cursor(Cursor.DEFAULT_CURSOR);
	private Cursor NW = new Cursor(Cursor.NW_RESIZE_CURSOR);
	private Cursor SW = new Cursor(Cursor.SW_RESIZE_CURSOR);
	private Cursor W = new Cursor(Cursor.W_RESIZE_CURSOR);
	private Cursor NE = new Cursor(Cursor.NE_RESIZE_CURSOR);
	private Cursor SE = new Cursor(Cursor.SE_RESIZE_CURSOR);
	private Cursor E = new Cursor(Cursor.E_RESIZE_CURSOR);
	private Cursor N = new Cursor(Cursor.N_RESIZE_CURSOR);
	private Cursor S = new Cursor(Cursor.S_RESIZE_CURSOR);
	@Override
	public void mouseMoved(MouseEvent e) {
		JFrame frame = getFrame(e);
		if(!frame.isResizable()) {
			frame = null;
			return;
		}
		x = e.getX();
		y = e.getY();
		isWestSide = x>=0 && x<=5;
		isEastSide = x>=frame.getWidth()-5 && x<=frame.getWidth();
		isNorthSide = y>=0 && y<=5;
		 isSouthSide = y>=frame.getHeight()-5 && y<=frame.getHeight();
		if(!isWestSide && !isEastSide && !isNorthSide && !isSouthSide) {
			if(frame.getCursor().getType()!=Cursor.DEFAULT_CURSOR)	frame.setCursor(def);
			frame = null;
			return;
		}
		
		if(isWestSide&&isNorthSide)		frame.setCursor(NW);
		else if(isWestSide&&isSouthSide)	frame.setCursor(SW);
		else if(isWestSide)	frame.setCursor(W);
		else if(isEastSide&&isNorthSide)	frame.setCursor(NE);
		else if(isEastSide&&isSouthSide)	frame.setCursor(SE);
		else if(isEastSide)	frame.setCursor(E);
		else if(isNorthSide)	frame.setCursor(N);
		else if(isSouthSide)	frame.setCursor(S);
		
		frame = null;
	}
	private int cursorType;
	private int movedX, movedY, xToChange, yToChange;
	private int widthToChange, heightToChange;
	@Override
	public void mouseDragged(MouseEvent e) {
		JFrame frame = getFrame(e);
		if(!frame.isResizable()) {
			frame = null;
			return;
		}
		cursorType = frame.getCursor().getType();
		if(cursorType==Cursor.DEFAULT_CURSOR || cursorType==Cursor.TEXT_CURSOR || cursorType==Cursor.HAND_CURSOR
				|| cursorType==Cursor.MOVE_CURSOR || cursorType==Cursor.WAIT_CURSOR || cursorType==Cursor.CROSSHAIR_CURSOR) {
			frame = null;
			return;
		}
		
		movedX = e.getXOnScreen()-previousX;
		movedY = e.getYOnScreen()-previousY;
		xToChange=frame.getX(); 
		yToChange=frame.getY();
		widthToChange=frame.getWidth();
		heightToChange=frame.getHeight();
		
		switch(cursorType) {
		case Cursor.N_RESIZE_CURSOR :
			yToChange+=movedY;
			heightToChange-=movedY;
			break;
		case Cursor.S_RESIZE_CURSOR :
			heightToChange+=movedY;
			break;
		case Cursor.W_RESIZE_CURSOR :
			xToChange+=movedX;
			widthToChange-=movedX;
			break;
		case Cursor.E_RESIZE_CURSOR :
			widthToChange+=movedX;
			break;
		case Cursor.NW_RESIZE_CURSOR :
			xToChange+=movedX;
			yToChange+=movedY;
			widthToChange-=movedX;
			heightToChange-=movedY;
			break;
		case Cursor.SE_RESIZE_CURSOR :
			widthToChange+=movedX;
			heightToChange+=movedY;
			break;
		case Cursor.NE_RESIZE_CURSOR :
			yToChange+=movedY;
			widthToChange+=movedX;
			heightToChange-=movedY;
			break;
		case Cursor.SW_RESIZE_CURSOR :
			xToChange+=movedX;
			widthToChange-=movedX;
			heightToChange+=movedY;
			break;
		default : break;
		}
		
		Dimension minimumSize = frame.getMinimumSize();
		xToChange = widthToChange<minimumSize.width ? frame.getX() : xToChange;
		yToChange = heightToChange<minimumSize.height ? frame.getY() : yToChange;
		widthToChange = widthToChange<minimumSize.width ? frame.getWidth() : widthToChange;
		heightToChange = heightToChange<minimumSize.height ? frame.getHeight() : heightToChange;
		frame.setBounds(xToChange, yToChange, widthToChange, heightToChange);
		
		previousX = e.getXOnScreen();
		previousY = e.getYOnScreen();
		
		frame = null;
		minimumSize = null;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		previousX = e.getXOnScreen();
		previousY = e.getYOnScreen();
		isPressed = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		isPressed = false;
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		JFrame frame = getFrame(e);
		if(!frame.isResizable() || isPressed) {
			frame = null;
			return;
		}
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		frame = null;
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
