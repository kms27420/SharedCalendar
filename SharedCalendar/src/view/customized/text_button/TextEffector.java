package view.customized.text_button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextEffector extends MouseAdapter {
	TextEffector() {}
 
	@Override
	public void mousePressed(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(((TextButton)e.getComponent()).getEffectTiming().equals(TextButton.PRESSED_TIMING)) 
			((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getEffectedFg());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(((TextButton)e.getComponent()).getEffectTiming().equals(TextButton.PRESSED_TIMING)) 
			((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getOriginalFg());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(!((TextButton)e.getComponent()).getEffectTiming().equals(TextButton.ENTERED_TIMING))	return;
		((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getEffectedFg());
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(!((TextButton)e.getComponent()).getEffectTiming().equals(TextButton.ENTERED_TIMING))	return;
		((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getOriginalFg());
	}
}
