package view.popup.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import view.customized.TransparentPanel;

public class SearchIconView extends TransparentPanel {
	public SearchIconView() {}
	
	private Stroke stroke = new BasicStroke(4.5f);
	private int x, y, r;
	private float horizontalPadding=0.2f, verticalPadding=0.2f;
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		((Graphics2D)g).setStroke(stroke);
		x = getWidth()<getHeight() ? (int)(getWidth()*horizontalPadding) : (int)((getWidth()-getHeight()*(1-verticalPadding*2))/2);
		y = getWidth()<getHeight() ? (int)((getHeight()-getWidth()*(1-horizontalPadding*2))/2) : (int)(getHeight()*verticalPadding);
		r = getWidth()<getHeight() ? (int)(getWidth()*(1-horizontalPadding*2)) : (int)(getHeight()*(1-verticalPadding*2));
		r = r*3/4;
		g.drawOval(x, y, r, r);
		g.drawLine((int)(x+1.707f*r/2), (int)(y+1.707f*r/2), x+r*4/3, y+r*4/3);
		super.paint(g);
	}
}
