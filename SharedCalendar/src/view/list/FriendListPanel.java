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
import util.ColorUtil;
import util.FontUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.abv.FriendsView;
import view.customized.ActionCallablePanel;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonScrollPane;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.customized.TransparentPanel;
import view.customized.UnderlineLabel;
import view.popup.FriendDeletePanel;

public class FriendListPanel extends TransparentPanel implements FriendsView, ActionListener {
	public FriendListPanel() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(new CommonScrollPane(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
	}
	
	@Override
	public void showFriends(List<Friend> friends) {
		clickedIdx = -1;
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
	
	private void onDeleteButtonClick() {
		((FriendDeletePanel)SubViewType.FRIEND_DELETE.VIEW).setFriendIdx(clickedIdx);
		((FriendDeletePanel)SubViewType.FRIEND_DELETE.VIEW).setAlert("정말 '" + getFriendView(clickedIdx).getText() + "' 님을 삭제하시겠습니까?");
		WindowShower.INSTANCE.showSubWindow(SubViewType.FRIEND_DELETE);
	}
	
	private void onAddButtonClick() {
		WindowShower.INSTANCE.showSubWindow(SubViewType.FRIEND_INSERT);
	}
	
	private FriendView getFriendView(int idx) {
		return (FriendView)((CommonScrollPane)getComponent(0)).getContentPane().getComponent(idx);
	}
	
	private CommonButtonsView createButtonsView() {
		CommonButtonsView bv = new CommonButtonsView(ButtonsType.CHECK_ONLY);
		bv.setOpaque(false);
		bv.setFont(FontUtil.createDefaultFont(12f));
		bv.setCheckButtonText("ADD AS A FRIEND");
		bv.setPadding(0.35f, 0.15f, 0.35f, 0.15f);
		((RoundRectButton)bv.getContentPane().getComponent(0)).addActionListener(this);
		return bv;
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
			revalidate();
			repaint();
		}
		
		private String getText() {
			return ((JLabel)getComponent(0)).getText();
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
