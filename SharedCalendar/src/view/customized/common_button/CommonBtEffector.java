package view.customized.common_button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CommonBtEffector extends MouseAdapter {
	private boolean isPressed;
	
	CommonBtEffector() {}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!(e.getComponent() instanceof CommonButton) || !((CommonButton)e.getComponent()).isEnableEffect())	return;
		e.getComponent().setBackground(((CommonButton)e.getComponent()).getNormalBg());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!(e.getComponent() instanceof CommonButton) || !((CommonButton)e.getComponent()).isEnableEffect())	return;
		isPressed = true;
		e.getComponent().setBackground(((CommonButton)e.getComponent()).getPressedBg());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!(e.getComponent() instanceof CommonButton) || !((CommonButton)e.getComponent()).isEnableEffect())	return;
		if(!isPressed)	return;
		e.getComponent().setBackground(((CommonButton)e.getComponent()).getEnteredBg());
		isPressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(!(e.getComponent() instanceof CommonButton) || !((CommonButton)e.getComponent()).isEnableEffect())	return;
		if(isPressed)	return;
		e.getComponent().setBackground(((CommonButton)e.getComponent()).getEnteredBg());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(!(e.getComponent() instanceof CommonButton) || !((CommonButton)e.getComponent()).isEnableEffect())	return;
		e.getComponent().setBackground(((CommonButton)e.getComponent()).getNormalBg());
		isPressed = false;
	}
}
