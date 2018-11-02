package view.popup.schedule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Friend;
import listener.view.sharing.ShareListener;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower.WindowView;
import view.customized.ActionCallablePanel;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.CommonScrollPane;
import view.customized.PaddingView;
import view.customized.RoundRectPanel;
import view.customized.TransparentLabel;
import view.customized.UnderlineLabel;
import view.popup.common.AlertLabel;

public class ScheduleSharePanel extends WindowView implements ActionListener, CommonButtonsListener {
	private List<Integer> clickedIdx = new ArrayList<>();
	private ShareListener shareListener = (sidx, fidx)->{};
	private int scheduleIdx;
	
	public ScheduleSharePanel() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(createNaviView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(new CommonScrollPane(), new LinearConstraints(7, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		((CommonButtonsView)getComponent(2)).setCommonButtonsListener(this);
	}
	
	public void showFriends(List<Friend> friends) {
		while(clickedIdx.size()!=0) {clickedIdx.remove(0);}
		((CommonScrollPane)getComponent(1)).getContentPane().removeAll();
		if(friends.isEmpty()) {
			((CommonScrollPane)getComponent(1)).getContentPane().add(new AlertLabel("공유할 대상이 없습니다."));
			return;
		}
		for(Friend f : friends)
			((CommonScrollPane)getComponent(1)).getContentPane().add(new FriendView(f.getID()));
		for(Component comp : ((CommonScrollPane)getComponent(1)).getContentPane().getComponents())
			((FriendView)comp).addActionListener(this);
		revalidate();
		repaint();
	}
	
	public void setScheduleIdx(int scheduleIdx) {
		this.scheduleIdx = scheduleIdx;
	}
	
	public void setShareListener(ShareListener l) {
		shareListener = l;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int idx=0; idx<((CommonScrollPane)getComponent(1)).getContentPane().getComponentCount(); idx++)
			if(((CommonScrollPane)getComponent(1)).getContentPane().getComponent(idx)==e.getSource()) {
				if(clickedIdx.contains(idx)) {
					clickedIdx.remove((Object)idx);
					((FriendView)((CommonScrollPane)getComponent(1)).getContentPane().getComponent(idx)).showClickedStatement(false);
				} else {
					clickedIdx.add(idx);
					((FriendView)((CommonScrollPane)getComponent(1)).getContentPane().getComponent(idx)).showClickedStatement(true);
				}
				return;
			}
	}

	@Override
	public void onCheckButtonClick(ActionEvent e) {
		if(clickedIdx.isEmpty())	return;
		int[] tmp = new int[clickedIdx.size()];
		for(int i=0; i<clickedIdx.size(); i++)	tmp[i] = clickedIdx.get(i);
		shareListener.onShare(scheduleIdx, tmp);
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.2f, 0.5f);
	}
	
	private Container createNaviView() {
		RoundRectPanel nv = new RoundRectPanel();
		nv.setOpaque(true);
		nv.setBackground(ColorUtil.getClickedViewColor());
		nv.setLayout(new BorderLayout());
		nv.add(new TransparentLabel("FRIEND LIST"));
		((TransparentLabel)nv.getComponent(0)).setFont(FontUtil.createDefaultFont(15f));
		((TransparentLabel)nv.getComponent(0)).setHorizontalAlignment(JLabel.CENTER);
		((TransparentLabel)nv.getComponent(0)).setVerticalAlignment(JLabel.CENTER);
		((TransparentLabel)nv.getComponent(0)).setForeground(ColorUtil.getOrangeColor());
		
		PaddingView pv = new PaddingView();
		pv.setOpaque(false);
		pv.setPadding(0.1f, 0.1f, 0.1f, 0.1f);
		pv.getContentPane().add(nv);
		return pv;
	}
	
	private CommonButtonsView createButtonsView() {
		CommonButtonsView bv = new CommonButtonsView(ButtonsType.BOTH);
		bv.setOpaque(false);
		bv.setPadding(0.25f, 0.1f, 0.25f, 0.1f);
		bv.setMargin(0.1f);
		bv.setFont(FontUtil.createDefaultFont(15f));
		bv.setCheckButtonText("SHARE");
		bv.setCancelButtonText("BACK");
		return bv;
	}
	
	private class FriendView extends ActionCallablePanel {
		private FriendView(String id) {
			setOpaque(false);
			setBackground(ColorUtil.getClickedViewColor());
			setLayout(new BorderLayout());
			add(createUnderlineLabel(id));
		}
		
		private void showClickedStatement(boolean isClicked) {
			setOpaque(isClicked);
			revalidate();
			repaint();
		}
		
		private UnderlineLabel createUnderlineLabel(String id) {
			UnderlineLabel ul = new UnderlineLabel(id);
			ul.setOpaque(false);
			ul.setFont(FontUtil.createDefaultFont(15f));
			ul.setForeground(ColorUtil.getLightGray());
			return ul;
		}
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setBackground(ColorUtil.getDarkColor());
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(new ScheduleSharePanel(){
			{
				showFriends(new ArrayList<Friend>(){
					{
						for(int i=0; i<10; i++)
							add(new Friend("test"+(i+1), "test"+(i+1)));
					}
				});
			}
		});
		f.setBounds(600, 200, 400, 700);
		f.setVisible(true);
	}
}
