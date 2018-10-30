package view.popup;

import java.awt.BorderLayout;
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
import listener.event.SelectEventAdapter;
import listener.event.SelectEventListener;
import listener.event.UpdateEventAdapter;
import listener.event.UpdateEventListener;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.CommonScrollPane;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.customized.TransparentPanel;

public class FriendSearchPanel extends WindowView implements ActionListener, CommonButtonsListener {
	private UpdateEventListener updateEventListener = new UpdateEventAdapter();
	private SelectEventListener selectEventListener = new SelectEventAdapter();
	
	public FriendSearchPanel() {
		setOpaque(false);
		setLayout(new BorderLayout());
		add(createContentPane());
	}
	
	public void setUpdateEventListener(UpdateEventListener l) {
		updateEventListener = l;
	}
	
	public void setSelectEventListener(SelectEventListener l) {
		selectEventListener = l;
	}
	
	public void showSearchedFriends(List<Friend> searchedFriends) {
		getContentPane().removeAll();
		for(Friend f : searchedFriends)	getContentPane().add(new FriendView(f.getID()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int idx=0; idx<getContentPane().getComponentCount(); idx++)
			if(e.getSource()==getAddButton(idx))	
				updateEventListener.onFriendInsert(getFriendView(idx).getID());
			else	return;
	}

	@Override
	public void onCheckButtonClick(ActionEvent e) {
		selectEventListener.onSelectFriend(getInputedText());
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}
	
	private Container getContentPane() {
		return ((CommonScrollPane)((Container)((PaddingView)getComponent(0)).getContentPane().getComponent(0)).getComponent(0)).getContentPane();
	}
	
	private String getInputedText() {
		return ((SearchView)((Container)((PaddingView)getComponent(0)).getContentPane().getComponent(0)).getComponent(1)).getSearchText();
	}
	
	private AddButton getAddButton(int idx) {
		return (AddButton)((Container)getFriendView(idx).getContentPane().getComponent(0)).getComponent(1);
	}
	
	private FriendView getFriendView(int idx) {
		return (FriendView)getContentPane().getComponent(idx);
	}
	
	private Container createContentPane() {
		TransparentPanel cp = new TransparentPanel();
		cp.setOpaque(false);
		cp.setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		cp.add(new CommonScrollPane(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		cp.add(new SearchView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		cp.add(createButtonsView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		
		PaddingView pv = new PaddingView();
		pv.setPadding(0.1f, 0, 0, 0);
		pv.getContentPane().add(cp);
		return pv;
	}
	
	private Container createButtonsView() {
		CommonButtonsView bv = new CommonButtonsView(ButtonsType.BOTH);
		bv.setPadding(0.1f, 0.1f, 0.1f, 0.1f);
		bv.setMargin(0.1f);
		bv.setCheckButtonText("SEARCH");
		bv.setCancelButtonText("BACK");
		bv.setCommonButtonsListener(FriendSearchPanel.this);
		return bv;
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.25f, 0.3f);
	}
	
	private class FriendView extends PaddingView {
		private FriendView(String id) {
			setPadding(0, 0.05f, 0.05f, 0.05f);
			getContentPane().add(createContentPane(id));
		}
		
		private String getID() {
			return ((RoundRectButton)((Container)getContentPane().getComponent(0)).getComponent(0)).getText();
		}
		
		private Container createContentPane(String id) {
			TransparentPanel cp = new TransparentPanel();
			cp.setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			cp.add(createIDView(id), new LinearConstraints(3, LinearSpace.MATCH_PARENT));
			cp.add(new AddButton());
			((AddButton)cp.getComponent(1)).addActionListener(FriendSearchPanel.this);
			return cp;
		}
		
		private RoundRectButton createIDView(String id) {
			RoundRectButton iv = new RoundRectButton(id);
			iv.setFont(FontUtil.createDefaultFont(15f));
			iv.setForeground(ColorUtil.getLightColor(ColorUtil.getLightGray()));
			iv.setBackground(ColorUtil.getLightGray());
			return iv;
		}
	}
}
