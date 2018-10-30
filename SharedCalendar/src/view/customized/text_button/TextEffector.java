package view.customized.text_button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.customized.text_button.TextButton.EffectTiming;

public class TextEffector extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(((TextButton)e.getComponent()).getEffectTiming().equals(EffectTiming.PRESSED_TIMING)) 
			((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getEffectedFg());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(((TextButton)e.getComponent()).getEffectTiming().equals(EffectTiming.PRESSED_TIMING)) 
			((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getNormalFg());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(!((TextButton)e.getComponent()).getEffectTiming().equals(EffectTiming.ENTERED_TIMING))	return;
		((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getEffectedFg());
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if(!(e.getComponent() instanceof TextButton)||!((TextButton)e.getComponent()).isEnableEffect())	return;
		if(!((TextButton)e.getComponent()).getEffectTiming().equals(EffectTiming.ENTERED_TIMING))	return;
		((TextButton)e.getComponent()).setForeground(((TextButton)e.getComponent()).getNormalFg());
	}
}
