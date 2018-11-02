package view.customized.text_button;

import java.awt.Color;
import java.awt.event.MouseListener;

import util.ColorUtil;
import view.customized.ActionCallableLabel;

public class TextButton extends ActionCallableLabel {
	private Color normalFg = ColorUtil.getOrangeColor();
	private Color opposingFg = ColorUtil.getOpposingColor(normalFg);
	private Color effectedFg = opposingFg;
	
	private EffectTiming effectTiming = EffectTiming.PRESSED_TIMING;
	private boolean enableOpposingFgEffect = true;
	private boolean enableEffect = true;
	
	public TextButton() {
		addMouseListener(new TextEffector());
		setOpaque(false);
	}
	
	@Override
	public void removeMouseListener(MouseListener ml) {
		if(ml instanceof TextEffector)	return;
		super.removeMouseListener(ml);
	}

	public void setEffectTiming(EffectTiming effectTiming) {
		this.effectTiming = effectTiming;
	}
	
	public void setEnableOpposingFgEffect(boolean enableOpposingFgEffect) {
		this.enableOpposingFgEffect = enableOpposingFgEffect;
	}
	
	public void setEnableEffect(boolean enableEffect) {
		this.enableEffect = enableEffect;
	}
	
	public void setNormalFg(Color normalFg) {
		setForeground(normalFg);
		this.normalFg = normalFg;
		this.opposingFg = ColorUtil.getOpposingColor(this.normalFg);
	}
	
	public void setEffectedFg(Color effectedFg) {
		this.effectedFg = effectedFg;
	}
	
	public boolean isEnableEffect() {
		return enableEffect;
	}
	
	public EffectTiming getEffectTiming() {
		return effectTiming;
	}
	
	public Color getNormalFg() {
		return normalFg;
	}
	
	public Color getEffectedFg() {
		return enableOpposingFgEffect ? opposingFg : effectedFg;
	}
	
	public static enum EffectTiming {
		PRESSED_TIMING, ENTERED_TIMING;
	}
}
