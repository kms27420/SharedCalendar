package view.frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;

import view.customized.ActionCallableLabel;

public class NavigationButton extends ActionCallableLabel {
	private Color enteredBg = new Color(229, 229, 229);
	private Color pressedBg = new Color(202, 202, 202);
	
	private Color changedFg = Color.WHITE;
	 
	NavigationButton() {
		addMouseListener(new ButtonEffector());
		setOpaque(true);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}
	
	NavigationButton(Icon icon) {
		this();
		setIcon(icon);
	}
	
	NavigationButton(String text) {
		this();
		setText(text);
	}
	/**
	 * Set background color when mouse entered on the button.
	 * @param enteredBg Color what you want to set. If null, it will be set to default color.
	 */
	void setEnteredBg(Color enteredBg) {
		this.enteredBg = enteredBg;
	}
	/**
	 * Set background color when mouse pressed on the button.
	 * @param pressedBg Color what you want to set. If null, it will be set to default color.
	 */
	void setPressedBg(Color pressedBg) {
		this.pressedBg = pressedBg;
	}
	/**
	 * Set text color when mouse entered on the button.
	 * @param mousePressedColor Color what you want to set. If null, it will be set to default color.
	 */
	void setChangedFg(Color changedFg) {
		this.changedFg = changedFg;
	}
	
	private class ButtonEffector extends MouseAdapter {
		private Color normalBg;
		private Color normalFg;
		
		@Override
		public void mousePressed(MouseEvent e) {
			e.getComponent().setBackground(pressedBg);
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getComponent().getMousePosition()==null)	return;
			e.getComponent().setBackground(enteredBg);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			normalBg = e.getComponent().getBackground();
			normalFg = e.getComponent().getForeground();
			e.getComponent().setBackground(enteredBg);
			e.getComponent().setForeground(changedFg);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			e.getComponent().setBackground(normalBg);
			e.getComponent().setForeground(normalFg);
		}
	}
}
