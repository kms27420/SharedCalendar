package view.popup;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;

import view.customized.TransparentPanel;

public class SearchIconView extends TransparentPanel {
	public SearchIconView() {}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(new SearchIconView());
		f.setBounds(400, 400, 500, 400);
		f.setVisible(true);
	}
	
	private Stroke stroke = new BasicStroke(3.5f);
	private int x, y, r;
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		((Graphics2D)g).setStroke(stroke);
		x = getWidth()<getHeight() ? 0 : (getWidth()-getHeight())/2;
		y = getWidth()<getHeight() ? (getHeight()-getWidth())/2 : 0;
		r = getWidth()<getHeight() ? getWidth() : getHeight();
		r = r*3/4;
		g.drawOval(x, y, r, r);
		g.drawLine((int)(x+1.707f*r/2), (int)(y+1.707f*r/2), x+r*4/3, y+r*4/3);
		super.paint(g);
	}
}
