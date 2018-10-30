package view.popup;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import util.ColorUtil;
import view.customized.common_button.CommonButton;

public class AddButton extends CommonButton {
	public AddButton() {
		setOpaque(false);
		setNormalBg(ColorUtil.getOrangeColor());
		setEnteredBg(ColorUtil.getDarkerColor(ColorUtil.getOrangeColor()));
		setPressedBg(ColorUtil.getDarkerColor(ColorUtil.getDarkerColor(ColorUtil.getOrangeColor())));
		setEnableEffect(true);
	}
	
	private int x, y, r;
	private Stroke stroke = new BasicStroke(2f);
	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		r = getWidth()<getHeight() ? getWidth() : getHeight();
		x = (getWidth()-r)/2;
		y = (getHeight()-r)/2;
		g.fillOval(x, y, r, r);
		g.setColor(Color.WHITE);
		((Graphics2D)g).setStroke(stroke);
		g.drawLine(getWidth()/2, (getHeight()-r/2)/2, getWidth()/2, (getHeight()+r/2)/2);
		g.drawLine((getWidth()-r/2)/2, getHeight()/2, (getWidth()+r/2)/2, getHeight()/2);
		super.paint(g);
	}
}
