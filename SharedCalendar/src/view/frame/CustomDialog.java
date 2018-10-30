package view.frame;

import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JDialog;

import util.ScreenSizeUtil;

public class CustomDialog extends CustomWindow {
	public CustomDialog() {
		super(new JDialog() {
			private RoundRectangle2D shape = new RoundRectangle2D.Double();
			
			@Override
			public Shape getShape() {
				shape.setRoundRect(0, 0, getContentPane().getPreferredSize().width, getContentPane().getPreferredSize().height, 50, 50);
				return shape;
			}
		});
		WINDOW.setMaximumSize(ScreenSizeUtil.getScreenSize());
		WINDOW.setMinimumSize(ScreenSizeUtil.getScaledSize(1/8f, 1/8f));
		((JDialog)WINDOW).setModalityType(ModalityType.APPLICATION_MODAL);
	}

	@Override
	public void setDefaultCloseOperation(int operation) {
		((JDialog)WINDOW).setDefaultCloseOperation(operation);
	}

	@Override
	protected Container getSuperContentPane() {
		return ((JDialog)WINDOW).getContentPane();
	}

	@Override
	protected void setUndecorated(boolean undecorated) {
		((JDialog)WINDOW).setUndecorated(undecorated);
	}
}
