package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import util.ColorUtil;
import util.FontUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.abv.LoginStatusView;
import view.customized.PaddingView;
import view.customized.RoundRectButton;

public class BottomNaviView extends JPanel implements ActionListener, LoginStatusView {
	public BottomNaviView() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		add(createMainPane());
	}
	
	@Override
	public void showLoginStatus(boolean isLogin) {
		((JLabel)((PaddingView)getComponent(0)).getContentPane().getComponent(1)).setEnabled(!isLogin);
		((JLabel)((PaddingView)getComponent(0)).getContentPane().getComponent(2)).setText(isLogin?"LOGOUT":"LOGIN");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof JLabel))	return;
		switch(((JLabel)e.getSource()).getText()) {
		case "LOGIN" :
			WindowShower.INSTANCE.showSubWindow(SubViewType.LOGIN);
			break;
		case "LOGOUT" :
			WindowShower.INSTANCE.showSubWindow(SubViewType.LOGOUT);
			break;
		case "SEARCH" :
			WindowShower.INSTANCE.showSubWindow(SubViewType.SEARCH);
			break;
		case "JOIN" :
			WindowShower.INSTANCE.showSubWindow(SubViewType.JOIN);
			break;
		}
	}
	
	private Container createMainPane() {
		PaddingView mainPane = new PaddingView();
		mainPane.setOpaque(false);
		mainPane.setPadding(0.25f, 0.15f, 0.25f, 0.15f);
		mainPane.setMargin(0.05f);
		mainPane.getContentPane().add(createButton("SEARCH"));
		mainPane.getContentPane().add(createButton("JOIN"));
		mainPane.getContentPane().add(createButton("LOGIN"));
		return mainPane;
	}
	
	private JLabel createButton(String text) {
		RoundRectButton button = new RoundRectButton(text);
		button.addActionListener(this);
		button.setFont(FontUtil.createDefaultFont(15f));
		button.setEnableOpposingFgEffect(false);
		button.setNormalFg(ColorUtil.getOrangeColor());
		button.setEffectedFg(ColorUtil.getDarkerColor(ColorUtil.getOrangeColor()));
		button.setEnableEffect(true);
		return button;
	}
}
