package view.customized;

import javax.swing.JLabel;

public class TransparentLabel extends JLabel {
	public TransparentLabel() {
		setOpaque(false);
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
