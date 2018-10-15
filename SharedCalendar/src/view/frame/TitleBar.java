package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.customized.TransparentLabel;
import view.customized.TransparentPanel;

public class TitleBar extends JPanel {
	private static final int BUTTONS_VIEW_WIDTH = 80;
	private static final int ICON_VIEW_WIDTH = 50;
	 
	TitleBar() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		add(createFrameMovableView());
		add(createButtonsView(), BorderLayout.EAST);
		setExistOfButtonsView(true);
	}
	/**
	 * Set exist of the button view of this frame.
	 * @param exist if true show button view else hide button view
	 */
	public void setExistOfButtonsView(boolean exist) {
		getComponent(1).setPreferredSize(new Dimension(exist?BUTTONS_VIEW_WIDTH:0, 0));
		revalidate();
		repaint();
	}
	/**
	 * Set the title text.
	 * @param title Title what you want to set.
	 */
	void setTitle(String title) {
		getTextView().setText(title);
	}
	/**
	 * Get the title text.
	 * @return Current setted title.
	 */
	String getTitle() {
		return getTextView().getText();
	}
	/**
	 * Set the title image icon.
	 * @param titleIcon Title icon what you want to set.
	 */
	public void setTitleIcon(ImageIcon titleIcon) {
		getIconView().setIcon(titleIcon);
	}
	/**
	 * Get the title image icon.
	 * @return Title image icon.
	 */
	public Icon getTitleIcon() {
		return getIconView().getIcon();
	}
	
	void addFrameMoveAdapter(MouseAdapter frameMoveAdapter) {
		getFrameMovableView().addMouseListener(frameMoveAdapter);
		getFrameMovableView().addMouseMotionListener(frameMoveAdapter);
	}
	
	void addNavigationBtAdapter(ActionListener navigationBtListener) {
		getMinimizingButton().addActionListener(navigationBtListener);
		getExitButton().addActionListener(navigationBtListener);
	}
	
	private Container getFrameMovableView() {
		return (Container)getComponent(0);
	}
	
	private JLabel getIconView() {
		return (JLabel)getFrameMovableView().getComponent(0);
	}
	
	private JLabel getTextView() {
		return (JLabel)getFrameMovableView().getComponent(1);
	}
	
	private NavigationButton getMinimizingButton() {
		return (NavigationButton)((Container)getComponent(1)).getComponent(0);
	}
	
	private NavigationButton getExitButton() {
		return (NavigationButton)((Container)getComponent(1)).getComponent(1);
	}
	
	// 여기 아래부터 뷰 구성
	
	private JPanel createFrameMovableView() {
		JPanel frameMovableView = new TransparentPanel();
		frameMovableView.setLayout(new BorderLayout());
		frameMovableView.add(new IconView(), BorderLayout.WEST);
		frameMovableView.add(new TextView());
		((IconView)frameMovableView.getComponent(0)).setPreferredSize(new Dimension(ICON_VIEW_WIDTH, 0));
		return frameMovableView;
	}
	
	private Container createButtonsView() {
		JPanel buttonsView = new TransparentPanel();
		buttonsView.setLayout(new GridLayout(1, 2));
		buttonsView.add(new MinimizingButton("ㅡ"));
		buttonsView.add(new ExitButton("X"));
		
		return buttonsView;
	}
	
	private class IconView extends TransparentLabel {
		private IconView() {
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(CENTER);
		}
	}
	
	private class TextView extends TransparentLabel {
		private TextView() {
			setVerticalAlignment(CENTER);
		}
	}
	
	private class MinimizingButton extends NavigationButton {
		private MinimizingButton() {
			setChangedFg(getForeground());
		}
		
		private MinimizingButton(String text) {
			this();
			setText(text);
		}
		
		private MinimizingButton(Icon icon) {
			this();
			setIcon(icon);
		}
	}
	
	private class ExitButton extends NavigationButton {
		private ExitButton() {
			setPressedBg(new Color(241, 112, 122));
			setEnteredBg(new Color(232, 17, 35));
		}
		
		private ExitButton(String text) {
			this();
			setText(text);
		}
		
		private ExitButton(Icon icon) {
			this();
			setIcon(icon);
		}
	}
}
