package view.popup.schedule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;

import data.Friend;
import data.Schedule;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.ActionCallablePanel;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.CommonScrollPane;
import view.customized.PaddingView;
import view.customized.RoundRectPanel;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.UnderlineLabel;
import view.list.DeleteButton;
import view.popup.common.AlertLabel;

public class ScheduleDetailView extends WindowView implements CommonButtonsListener, ActionListener {
	private int clickedIdx=-1;
	
	public ScheduleDetailView() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		add(createScheduleView(), new LinearConstraints(19, LinearSpace.MATCH_PARENT));
		add(createSharedFriendsView(), new LinearConstraints(11, LinearSpace.MATCH_PARENT));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof DeleteButton) {
			((ScheduleUnsharePanel)SubViewType.UNSHARE.VIEW).setFriendIdx(getIdx(((DeleteButton)e.getSource())));
			WindowShower.INSTANCE.showSubWindow(SubViewType.UNSHARE);
		} else if(e.getSource() instanceof SharedFriendView)
			for(int idx=0; idx<getSharedFriendsContentPane().getComponentCount(); idx++) {
				if(getSharedFriendsContentPane().getComponent(idx)==e.getSource()) {	
					if(clickedIdx>=0)	
						((SharedFriendView)getSharedFriendsContentPane().getComponent(clickedIdx)).showClickedStatement(false);
					clickedIdx = clickedIdx==idx ? -1 : idx;
					if(clickedIdx==-1)	return;
					((SharedFriendView)getSharedFriendsContentPane().getComponent(clickedIdx)).showClickedStatement(true);
					return;
				}
			}
		else	return;
	}

	@Override
	public void onCheckButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {}

	public void showScheduleDetail(Schedule schedule) {
		getInfoView(0).showContent(schedule.getTitle());
		getInfoView(1).showContent(schedule.getYMD().toString());
		getInfoView(2).showContent(schedule.getStartTime().toString());
		getInfoView(3).showContent(schedule.getEndTime().toString());
		getInfoView(4).showContent(schedule.getContent());
	}
	
	public void showSharedFriends(List<Friend> sharedFriends) {
		clickedIdx = -1;
		getSharedFriendsContentPane().removeAll();
		if(sharedFriends.isEmpty()) {
			getSharedFriendsContentPane().add(new AlertLabel("NO DATA"));
			revalidate();
			repaint();
			return;
		}
		for(Friend f : sharedFriends)
			getSharedFriendsContentPane().add(new SharedFriendView(f.getID(), f.getName()));
		for(Component comp : getSharedFriendsContentPane().getComponents())
			((SharedFriendView)comp).addActionListener(this);
		revalidate();
		repaint();
	}
	
	private Container getSharedFriendsContentPane() {
		return ((CommonScrollPane)((Container)getComponent(1)).getComponent(1)).getContentPane();
	}
	
	private int getIdx(DeleteButton b) {
		int idx = 0;
		for(Component comp : getSharedFriendsContentPane().getComponents()) {
			if(((SharedFriendView)comp).getDeleteButton()==b)	return idx;
			idx++;
		}
		return idx;
	}
	
	private InfoView getInfoView(int idx) {
		return (InfoView)((Container)((PaddingView)getComponent(0)).getContentPane().getComponent(0)).getComponent(idx);
	}
	
	private Container createScheduleView() {
		RoundRectPanel sv = new RoundRectPanel();
		sv.setOpaque(true);
		sv.setBackground(ColorUtil.getClickedViewColor());
		sv.setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		sv.add(new InfoView("TITLE"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		sv.add(new InfoView("DATE"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		sv.add(new InfoView("BEGIN TIME"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		sv.add(new InfoView("END TIME"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		sv.add(new InfoView("CONTENT"), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		
		PaddingView pv = new PaddingView();
		pv.setOpaque(false);
		pv.setPadding(0f, 0.1f, 0.05f, 0.04f);
		pv.getContentPane().add(sv);
		return pv;
	}
	
	private Container createSharerListNaviView() {
		RoundRectPanel nv = new RoundRectPanel();
		nv.setOpaque(true);
		nv.setBackground(ColorUtil.getClickedViewColor());
		nv.setLayout(new BorderLayout());
		nv.add(new TransparentLabel("SHARER LIST"));
		((TransparentLabel)nv.getComponent(0)).setFont(FontUtil.createDefaultFont(15f));
		((TransparentLabel)nv.getComponent(0)).setHorizontalAlignment(JLabel.CENTER);
		((TransparentLabel)nv.getComponent(0)).setVerticalAlignment(JLabel.CENTER);
		((TransparentLabel)nv.getComponent(0)).setForeground(ColorUtil.getOrangeColor());
		PaddingView pv = new PaddingView();
		pv.setOpaque(false);
		pv.setPadding(0, 0, 0.05f, 0.05f);
		pv.getContentPane().add(nv);
		return pv;
	}
	
	private Container createSharedFriendsView() {
		TransparentPanel fv = new TransparentPanel();
		fv.setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		fv.add(createSharerListNaviView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		fv.add(new CommonScrollPane(), new LinearConstraints(4, LinearSpace.MATCH_PARENT));
		fv.add(new CommonButtonsView(ButtonsType.CHECK_ONLY), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		((CommonButtonsView)fv.getComponent(2)).setCommonButtonsListener(this);
		((CommonButtonsView)fv.getComponent(2)).setPadding(0.3f, 0.24f, 0.3f, 0.24f);
		((CommonButtonsView)fv.getComponent(2)).setFont(FontUtil.createDefaultFont(12f));
		return fv;
	}
	
	private class SharedFriendView extends ActionCallablePanel {
		private SharedFriendView(String id, String name) {
			setBackground(ColorUtil.getClickedViewColor());
			setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			add(createUnderlineLabel(id, name), new LinearConstraints(9, LinearSpace.MATCH_PARENT));
			add(createDeleteButtonView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		}
		
		private void showClickedStatement(boolean isClicked) {
			setOpaque(isClicked);
			((DeleteButton)((PaddingView)getComponent(1)).getContentPane().getComponent(0)).setVisible(isClicked);
			revalidate();
			repaint();
		}
		
		private DeleteButton getDeleteButton() {
			return (DeleteButton)((PaddingView)getComponent(1)).getContentPane().getComponent(0);
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
			((DeleteButton)dbv.getContentPane().getComponent(0)).addActionListener(ScheduleDetailView.this);
			return dbv;
		}
	}
	
	private class InfoView extends TransparentPanel {
		private InfoView(String title) {
			setLayout(new LinearLayout(Orientation.HORIZONTAL,0));
			add(createLabel(title, FlatHorizontalAlignment.RIGHT, ColorUtil.getLightColor(ColorUtil.getOrangeColor()))
					, new LinearConstraints(1, LinearSpace.MATCH_PARENT));
			add(createLabel("", FlatHorizontalAlignment.CENTER, Color.WHITE), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		}
		
		private FlatLabel createLabel(String text, FlatHorizontalAlignment horizontalAlignment, Color fg) {
			FlatLabel l = new FlatLabel(text);
			l.setOpaque(false);
			l.setHorizontalAlignment(horizontalAlignment);
			l.setVerticalAlignment(FlatVerticalAlignment.CENTER);
			l.setForeground(fg);
			l.setFont(FontUtil.createDefaultFont(15f));
			return l;
		}
		
		private void showContent(String content) {
			((FlatLabel)getComponent(1)).setPreferredSize(new Dimension(0, 0));
			((FlatLabel)getComponent(1)).setText(content);
		}
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.3f, 0.4f);
	}
}
