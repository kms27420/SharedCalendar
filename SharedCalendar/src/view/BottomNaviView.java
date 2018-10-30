package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import listener.event.SelectEventListener;
import listener.join.JoinListener;
import listener.login.LoginListener;
import util.ColorUtil;
import util.FontUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.popup.JoinPanel;
import view.popup.LoginPanel;

public class BottomNaviView extends JPanel implements ActionListener, LoginStatusView {
	public BottomNaviView() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		add(createMainPane());
	}
	
	public void setJoinListener(JoinListener l) {
		joinPanel.setJoinListener(l);
	}
	
	public void setLoginListener(LoginListener l) {
		loginPanel.setLoginListener(l);
	}
	
	public void setSelectEventListener(SelectEventListener l) {
		
	}
	@Override
	public void showLoginStatus(boolean isLogin) {
		((JLabel)((PaddingView)getComponent(0)).getContentPane().getComponent(1)).setEnabled(!isLogin);
		((JLabel)((PaddingView)getComponent(0)).getContentPane().getComponent(2)).setText(isLogin?"LOGOUT":"LOGIN");
	}
	
	private LoginPanel loginPanel = new LoginPanel();
	private JoinPanel joinPanel = new JoinPanel();
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof JLabel))	return;
		switch(((JLabel)e.getSource()).getText()) {
		case "LOGIN" :
			WindowShower.INSTANCE.showSubWindow(loginPanel, SubViewType.LOGIN);
			break;
		case "LOGOUT" :
			break;
		case "SEARCH" :
			break;
		case "JOIN" :
			WindowShower.INSTANCE.showSubWindow(joinPanel, SubViewType.JOIN);
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
