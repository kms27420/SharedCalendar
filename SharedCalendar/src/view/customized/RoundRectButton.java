package view.customized;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JFrame;

import view.customized.common_button.CommonButton;

public class RoundRectButton extends CommonButton {
	private boolean opaque;
	
	public RoundRectButton() {
		super.setOpaque(false);
		setOpaque(true);
		setNormalBg(new Color(191,191,191));
		setEnteredBg(new Color(181,181,181));
		setPressedBg(new Color(171,171,171));
		setNormalFg(Color.BLACK);
		setEffectedFg(getNormalFg());
		setEnableOpposingFgEffect(false);
	}
	
	public RoundRectButton(String text) {
		this();
		setText(text);
	}
	
	public RoundRectButton(Icon icon) {
		this();
		setIcon(icon);
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
		f.setMinimumSize(new Dimension(0,0));
		f.setBounds(0,0,1000,800);
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(new RoundRectButton());
		f.setVisible(true);
	}
}
