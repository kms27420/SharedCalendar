package view.customized.text_button;

import java.awt.Color;
import java.awt.event.MouseListener;

import util.ColorUtil;
import view.customized.ActionCallableLabel;

public class TextButton extends ActionCallableLabel {
	public static final String PRESSED_TIMING= "pressed_timing";
	public static final String ENTERED_TIMING = "entered_timing";
	 
	private Color originalFg = Color.BLACK;
	private Color opposingFg = ColorUtil.getOpposingColor(originalFg);
	private Color effectedFg = opposingFg;
	
	private String effectTiming = PRESSED_TIMING;
	private boolean enableOpposingFgEffect = true;
	private boolean enableEffect = true;
	
	public TextButton() {
		addMouseListener(new TextEffector());
	}
	
	@Override
	public void removeMouseListener(MouseListener ml) {
		if(ml instanceof TextEffector)	return;
		super.removeMouseListener(ml);
	}

	public void setEffectTiming(String effectTiming) {
		this.effectTiming = effectTiming;
	}
	
	public void setEnableOppositeColorEffect(boolean enableOpposingFgEffect) {
		this.enableOpposingFgEffect = enableOpposingFgEffect;
	}
	
	public void setEnableEffect(boolean enableEffect) {
		this.enableEffect = enableEffect;
	}
	
	public void setOriginalFg(Color originalFg) {
		this.originalFg = originalFg;
		this.opposingFg = ColorUtil.getOpposingColor(this.originalFg);
	}
	
	public void setEffectedFg(Color effectedFg) {
		this.effectedFg = effectedFg;
	}
	
	boolean isEnableEffect() {
		return enableEffect;
	}
	
	String getEffectTiming() {
		return effectTiming;
	}
	
	public Color getOriginalFg() {
		return originalFg;
	}
	
	public Color getEffectedFg() {
		return enableOpposingFgEffect ? opposingFg : effectedFg;
	}
}
