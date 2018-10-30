package view.customized;

import javax.swing.JLabel;

public class TransparentLabel extends JLabel {
	public TransparentLabel() {
		setOpaque(false);
	}
	
	public TransparentLabel(String text) {
		this();
		setText(text);
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	@Override
	public void setOpaque(boolean isOpaque) {
		super.setOpaque(false);
	}
}
