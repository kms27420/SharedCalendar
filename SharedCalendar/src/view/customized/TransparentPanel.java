package view.customized;

import javax.swing.JPanel;

public class TransparentPanel extends JPanel {
	public TransparentPanel() {
		setOpaque(false);
	}
	
	@Override
	public void setOpaque(boolean isOpaque) {
		super.setOpaque(false);
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
} 
