package view.calendar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import util.ColorUtil;
import view.customized.ActionCallableLabel;

public class MonthMoveButton extends ActionCallableLabel {
	private int arrowWidth = 10;
	private int arrowHeight = 10;
	private Stroke arrowThickness = new BasicStroke(2f);
	
	private Color circleColor = ColorUtil.getOrangeColor();
	private Color arrowColor = Color.WHITE;
	final Direction DIRECTION;
	
	MonthMoveButton(Direction arrowDirection) {	
		setOpaque(false);
		DIRECTION = arrowDirection;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(circleColor);
		g.fillOval(getWidth()/10, getHeight()/10, getWidth()*4/5, getHeight()*4/5);
		g.setColor(arrowColor);
		((Graphics2D)g).setStroke(arrowThickness);
		g.drawLine((getWidth()-arrowWidth)/2, getHeight()/2, (getWidth()+arrowWidth)/2, getHeight()/2);
		switch(DIRECTION) {
		case LEFT :
			g.drawLine((getWidth()-arrowWidth)/2, getHeight()/2, getWidth()/2, (getHeight()-arrowHeight)/2);
			g.drawLine((getWidth()-arrowWidth)/2, getHeight()/2, getWidth()/2, (getHeight()+arrowHeight)/2);
			break;
		case RIGHT :
			g.drawLine((getWidth()+arrowWidth)/2, getHeight()/2, getWidth()/2, (getHeight()-arrowHeight)/2);
			g.drawLine((getWidth()+arrowWidth)/2, getHeight()/2, getWidth()/2, (getHeight()+arrowHeight)/2);
			break;
			default : break;
		}
	}
	
	public static enum Direction {
		LEFT, RIGHT;
	}
}
