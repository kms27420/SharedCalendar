package view.customized.common_button;

import java.awt.Color;
import java.awt.event.MouseListener;

import view.customized.text_button.TextButton;

public class CommonButton extends TextButton {
	private Color normalBg = Color.WHITE;
	private Color enteredBg = Color.LIGHT_GRAY;
	private Color pressedBg = Color.GRAY;
	
	public CommonButton() {
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		setBackground(normalBg);
		addMouseListener(new CommonBtEffector());
	}
	
	public CommonButton(String text) {
		this();
		setText(text);
	}
	
	@Override
	public void removeMouseListener(MouseListener ml) {
		if(ml instanceof CommonBtEffector)	return;
		super.removeMouseListener(ml);
	}
	
	public void setNormalBg(Color normalBg) {
		this.normalBg = normalBg;
		setBackground(normalBg);
	}
	
	public void setEnteredBg(Color enteredBg) {
		this.enteredBg = enteredBg;
	}
	
	public void setPressedBg(Color pressedBg) {
		this.pressedBg = pressedBg;
	}
	
	public Color getNormalBg() {
		return normalBg;
	}
	
	public Color getEnteredBg() {
		return enteredBg;
	}
	
	public Color getPressedBg() {
		return pressedBg;
	}
}
