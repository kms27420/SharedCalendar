package view.customized;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RoundRectPanel extends JPanel {
	private boolean opaque;
	
	public RoundRectPanel() {
		super.setOpaque(false);
		setOpaque(false);
	}
	
	@Override
	public void setOpaque(boolean opaque) {
		this.opaque = opaque;
	}
	
	@Override
	public void paint(Graphics g) {
		if(opaque) {
			g.setColor(getBackground());
			g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		}
		super.paint(g);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(400, 400, 600, 500);
		f.getContentPane().add(new RoundRectPanel());
		((JPanel)f.getContentPane().getComponent(0)).setOpaque(true);
		f.getContentPane().getComponent(0).setBackground(Color.BLACK);
		f.setVisible(true);
	}
}
