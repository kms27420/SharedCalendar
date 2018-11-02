package view.list;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import listener.view.ListMenuSelectListener;
import util.ColorUtil;
import view.abv.LoginStatusView;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.customized.TransparentPanel;
import view.list.SelectedListViewPanel.MenuType;

public class ListMenuSelectPanel extends PaddingView implements ActionListener, LoginStatusView {
	private ListMenuSelectListener listMenuSelectListener = m->{};
	
	public ListMenuSelectPanel() {
		setOpaque(false);
		getContentPane().add(createMainView());
		setPadding(1/3f, 0, 0, 0);
		setMargin(0);
		initButtons();
	}

	public void showMenuTypeView(MenuType menuType) {
		for(MenuType menu : MenuType.values()) {
			if(menu==menuType)	
				((Container)getContentPane().getComponent(0)).getComponent(menuType.ordinal()).setBackground(ColorUtil.getClickedViewColor());
			else	
				((Container)getContentPane().getComponent(0)).getComponent(menu.ordinal()).setBackground(ColorUtil.getDarkColor());
		}
	}
	
	@Override
	public void showLoginStatus(boolean isLogin) {
		((Container)getContentPane().getComponent(0)).getComponent(1).setEnabled(isLogin);
		if(!isLogin)	listMenuSelectListener.onListMenuSelect(MenuType.EVENTS);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0; i<((Container)getContentPane().getComponent(0)).getComponentCount(); i++) {
			if(e.getSource()==((Container)getContentPane().getComponent(0)).getComponent(i)) {
				listMenuSelectListener.onListMenuSelect(MenuType.values()[i]);
				return;
			}
		}
	}
	
	public void setListMenuSelectListener(ListMenuSelectListener l) {
		this.listMenuSelectListener = l;
	}
	
	private Container createMainView() {
		Container mainView = new TransparentPanel();
		mainView.setLayout(new GridLayout(1,MenuType.values().length));
		for(MenuType menu : MenuType.values())
			mainView.add(new RoundRectButton(menu.name()));
		return mainView;
	}
	
	private void initButtons() {
		for(Component comp : ((Container)getContentPane().getComponent(0)).getComponents()) {
			((RoundRectButton)comp).setEnableEffect(false);
			((RoundRectButton)comp).setForeground(ColorUtil.getOrangeColor());
			((RoundRectButton)comp).setBackground(ColorUtil.getDarkColor());
			((RoundRectButton)comp).addActionListener(this);
		}
	}
}
