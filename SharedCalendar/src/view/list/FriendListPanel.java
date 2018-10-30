package view.list;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Friend;
import listener.event.SelectEventListener;
import listener.event.UpdateEventListener;
import util.ColorUtil;
import util.FontUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.FriendsView;
import view.customized.ActionCallablePanel;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonScrollPane;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.customized.TransparentPanel;
import view.customized.UnderlineLabel;
import view.popup.AlertView;
import view.popup.FriendSearchPanel;

public class FriendListPanel extends TransparentPanel implements FriendsView, ActionListener {
	private List<Friend> friends;
	
	public FriendListPanel() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(new CommonScrollPane(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		add(new PaddingView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
	}
	
	public void setUpdateEventListener(UpdateEventListener l) {
		deleteButtonView.setAlertCheckListener(()->l.onFriendDelete(friends.get(clickedIdx).getID()));
		friendSearchPanel.setUpdateEventListener(l);
	}
	
	public void setSelectEventListener(SelectEventListener l) {
		friendSearchPanel.setSelectEventListener(l);
	}
	
	@Override
	public void showFriends(List<Friend> friends) {
		clickedIdx = -1;
		this.friends = friends;
		((CommonScrollPane)getComponent(0)).getContentPane().removeAll();
		for(Friend f : friends)
			((CommonScrollPane)getComponent(0)).getContentPane().add(new FriendView(f.getID(), f.getName()));
		for(Component comp : ((CommonScrollPane)getComponent(0)).getContentPane().getComponents())
			((FriendView)comp).addActionListener(this);
		revalidate();
		repaint();
	}
	
	private int clickedIdx = -1;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof FriendView)	onFriendViewClick((FriendView)e.getSource());
		else if(e.getSource() instanceof DeleteButton)	onDeleteButtonClick();
		else if(e.getSource() instanceof RoundRectButton)	onAddButtonClick();
		else return;
	}
	
	private void onFriendViewClick(FriendView clickedView) {
		if(clickedIdx>=0)	getFriendView(clickedIdx).showClickedState(false);
		clickedView.showClickedState(true);
		for(int idx=0; idx<((CommonScrollPane)getComponent(0)).getContentPane().getComponentCount(); idx++)
			if(clickedView==getFriendView(idx)) {
				clickedIdx = idx;
				return;
			}
	}
	
	private AlertView deleteButtonView = new AlertView(ButtonsType.BOTH);
	private void onDeleteButtonClick() {
		deleteButtonView.setAlert("정말 '" + friends.get(clickedIdx) + "' 님을 삭제하시겠습니까?");
		WindowShower.INSTANCE.showSubWindow(deleteButtonView, SubViewType.FRIEND_DELETE);
	}
	
	private FriendSearchPanel friendSearchPanel = new FriendSearchPanel();
	private void onAddButtonClick() {
		WindowShower.INSTANCE.showSubWindow(friendSearchPanel, SubViewType.FRIEND_INSERT);
	}
	
	private FriendView getFriendView(int idx) {
		return (FriendView)((CommonScrollPane)getComponent(0)).getContentPane().getComponent(idx);
	}
	
	private class FriendView extends ActionCallablePanel {
		private FriendView(String id, String name) {
			setBackground(ColorUtil.getClickedViewColor());
			setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			add(createUnderlineLabel(id, name), new LinearConstraints(9, LinearSpace.MATCH_PARENT));
			add(createDeleteButtonView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		}
		
		private void showClickedState(boolean isClicked) {
			setOpaque(isClicked);
			((DeleteButton)((PaddingView)getComponent(1)).getContentPane().getComponent(0)).setVisible(isClicked);
		}
		
		private JLabel createUnderlineLabel(String id, String name) {
			JLabel ul = new UnderlineLabel();
			ul.setText(id+" (" + name + ")");
			ul.setFont(FontUtil.createDefaultFont(15f));
			ul.setVerticalAlignment(JLabel.CENTER);
			ul.setHorizontalAlignment(JLabel.CENTER);
			ul.setForeground(ColorUtil.getLightGray());
			return ul;
		}
		
		private Container createDeleteButtonView() {
			PaddingView dbv = new PaddingView();
			dbv.setOpaque(false);
			dbv.setPadding(0.2f, 0.2f, 0.5f, 0.2f);
			dbv.setMargin(0);
			dbv.getContentPane().add(new DeleteButton());
			((DeleteButton)dbv.getContentPane().getComponent(0)).setVisible(false);
			((DeleteButton)dbv.getContentPane().getComponent(0)).addActionListener(FriendListPanel.this);
			return dbv;
		}
	}
}
