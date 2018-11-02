package view.popup;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Friend;
import listener.view.friend.FriendInsertListener;
import listener.view.friend.FriendSearchListener;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import view.abv.SearchedFriendsView;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.customized.TransparentPanel;
import view.popup.common.AddButton;
import view.popup.common.AlertLabel;
import view.popup.common.SearchPanel;

public class FriendSearchPanel extends SearchPanel implements ActionListener, SearchedFriendsView {
	private FriendInsertListener friendInsertListener = i->{};
	private FriendSearchListener friendSearchListener = s->{};
	
	public FriendSearchPanel() {}
	
	public void setFriendInsertListener(FriendInsertListener l) {
		friendInsertListener = l;
	}
	
	public void setFriendSearchListener(FriendSearchListener l) {
		friendSearchListener = l;
	}
	
	@Override
	protected void showInitedView() {
		getScrollPaneContentPane().removeAll();
		getSearchView().setText("");
	}

	@Override
	public void showSearchedFriends(List<Friend> searchedFriends) {
		getScrollPaneContentPane().removeAll();
		if(searchedFriends.isEmpty()) {
			getScrollPaneContentPane().add(new AlertLabel("검색 결과가 없습니다."));
			return;
		}
		for(Friend f : searchedFriends)	getScrollPaneContentPane().add(new FriendView(f.getID(), f.getName()));
		revalidate();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int idx=0; idx<getScrollPaneContentPane().getComponentCount(); idx++)
			if(e.getSource()==getAddButton(idx)) {	
				friendInsertListener.onFriendInsert(idx);
				return;
			}
	}

	@Override
	public void onCheckButtonClick(ActionEvent e) {
		friendSearchListener.onFriendSearch(getSearchView().getSearchText());
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}
	
	private AddButton getAddButton(int idx) {
		return (AddButton)((Container)getFriendView(idx).getContentPane().getComponent(0)).getComponent(1);
	}
	
	private FriendView getFriendView(int idx) {
		return (FriendView)getScrollPaneContentPane().getComponent(idx);
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.2f, 0.4f);
	}
	
	private class FriendView extends PaddingView {
		private FriendView(String id, String name) {
			setOpaque(false);
			setPadding(0, 0.05f, 0.05f, 0.05f);
			getContentPane().add(createContentPane(id, name));
		}
		
		private String getID() {
			return ((RoundRectButton)((Container)getContentPane().getComponent(0)).getComponent(0)).getText();
		}
		
		private Container createContentPane(String id, String name) {
			TransparentPanel cp = new TransparentPanel();
			cp.setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			cp.add(createIDView(id, name), new LinearConstraints(3, LinearSpace.MATCH_PARENT));
			cp.add(new AddButton(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
			((AddButton)cp.getComponent(1)).addActionListener(FriendSearchPanel.this);
			return cp;
		}
		
		private RoundRectButton createIDView(String id, String name) {
			RoundRectButton iv = new RoundRectButton(id+"("+name+")");
			iv.setEnableEffect(false);
			iv.setFont(FontUtil.createDefaultFont(15f));
			iv.setForeground(ColorUtil.getLightGray());
			iv.setBackground(ColorUtil.getClickedViewColor());
			return iv;
		}
	}
}
