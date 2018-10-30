package view.frame;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

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
	
	private boolean isResizable;
	
	public void setResizable(boolean resizable) {
		this.isResizable = resizable;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		Window window = getWindow(e);
		if(!isResizable) {
			window = null;
			return;
		}
		x = e.getX();
		y = e.getY();
		isWestSide = x>=0 && x<=5;
		isEastSide = x>=window.getWidth()-5 && x<=window.getWidth();
		isNorthSide = y>=0 && y<=5;
		 isSouthSide = y>=window.getHeight()-5 && y<=window.getHeight();
		if(!isWestSide && !isEastSide && !isNorthSide && !isSouthSide) {
			if(window.getCursor().getType()!=Cursor.DEFAULT_CURSOR)	window.setCursor(def);
			window = null;
			return;
		}
		
		if(isWestSide&&isNorthSide)		window.setCursor(NW);
		else if(isWestSide&&isSouthSide)	window.setCursor(SW);
		else if(isWestSide)	window.setCursor(W);
		else if(isEastSide&&isNorthSide)	window.setCursor(NE);
		else if(isEastSide&&isSouthSide)	window.setCursor(SE);
		else if(isEastSide)	window.setCursor(E);
		else if(isNorthSide)	window.setCursor(N);
		else if(isSouthSide)	window.setCursor(S);
		
		window = null;
	}
	private int cursorType;
	private int movedX, movedY, xToChange, yToChange;
	private int widthToChange, heightToChange;
	@Override
	public void mouseDragged(MouseEvent e) {
		Window window = getWindow(e);
		if(!isResizable) {
			window = null;
			return;
		}
		cursorType = window.getCursor().getType();
		if(cursorType==Cursor.DEFAULT_CURSOR || cursorType==Cursor.TEXT_CURSOR || cursorType==Cursor.HAND_CURSOR
				|| cursorType==Cursor.MOVE_CURSOR || cursorType==Cursor.WAIT_CURSOR || cursorType==Cursor.CROSSHAIR_CURSOR) {
			window = null;
			return;
		}
		
		movedX = e.getXOnScreen()-previousX;
		movedY = e.getYOnScreen()-previousY;
		xToChange=window.getX(); 
		yToChange=window.getY();
		widthToChange=window.getWidth();
		heightToChange=window.getHeight();
		
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
		
		Dimension minimumSize = window.getMinimumSize();
		xToChange = widthToChange<minimumSize.width ? window.getX() : xToChange;
		yToChange = heightToChange<minimumSize.height ? window.getY() : yToChange;
		widthToChange = widthToChange<minimumSize.width ? window.getWidth() : widthToChange;
		heightToChange = heightToChange<minimumSize.height ? window.getHeight() : heightToChange;
		window.setBounds(xToChange, yToChange, widthToChange, heightToChange);
		
		previousX = e.getXOnScreen();
		previousY = e.getYOnScreen();
		
		window = null;
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
		Window window = getWindow(e);
		if(!isResizable || isPressed) {
			window = null;
			return;
		}
		window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		window = null;
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
