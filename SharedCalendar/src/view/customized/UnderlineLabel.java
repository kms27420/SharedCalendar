package view.customized;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import util.FontUtil;

public class UnderlineLabel extends TransparentLabel {
	private float lineThickness = 4f;
	private Stroke lineStroke = new BasicStroke(lineThickness);
	
	public UnderlineLabel() {
		setFont(FontUtil.createDefaultFont(15f));
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		setForeground(new Color(165, 165, 165));
	}
	
	public UnderlineLabel(String text) {
		this();
		setText(text);
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(getForeground());
		((Graphics2D)g).setStroke(lineStroke);
		g.drawLine(getWidth()/10, getHeight()-(int)(lineThickness), getWidth()*9/10, (int)(getHeight()-lineThickness));
		super.paint(g);
	}
}
