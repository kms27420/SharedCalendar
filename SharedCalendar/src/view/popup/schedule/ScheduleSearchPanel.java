package view.popup.schedule;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Schedule;
import listener.view.schedule.ScheduleSearchListener;
import listener.view.schedule.SearchedScheduleSelectListener;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import view.customized.ActionCallablePanel;
import view.customized.CommonScrollPane;
import view.customized.TransparentLabel;
import view.popup.common.AlertLabel;
import view.popup.common.SearchPanel;
import view.popup.common.SearchView;

public class ScheduleSearchPanel extends SearchPanel implements ActionListener {
	private ScheduleSearchListener scheduleSearchListener = k->{};
	private SearchedScheduleSelectListener searchedScheduleSelectListener = i->{};
	private int clickedIdx = -1;
	
	public ScheduleSearchPanel() {}
	
	public void showSearchedSchedules(List<Schedule> searched) {
		clickedIdx = -1;
		getScrollPaneContentPane().removeAll();
		if(searched.isEmpty()) {
			getScrollPaneContentPane().add(new AlertLabel("검색 결과가 없습니다."));
			return;
		}
		for(Schedule s : searched)
			getScrollPaneContentPane().add(new ScheduleView(s.getYMD().toString(), s.getTitle()));
		for(Component comp : getScrollPaneContentPane().getComponents())
			((ScheduleView)comp).addActionListener(this);
		revalidate();
		repaint();
	}
	
	public void setScheduleSearchListener(ScheduleSearchListener l) {
		scheduleSearchListener = l;
	}
	
	public void setSearchedScheduleSelectListener(SearchedScheduleSelectListener l) {
		searchedScheduleSelectListener = l;
	}
	
	@Override
	protected void showInitedView() {
		clickedIdx = -1;
		getScrollPaneContentPane().removeAll();
		getSearchView().setText("");
	}

	@Override
	public void onCheckButtonClick(ActionEvent e) {
		scheduleSearchListener.onScheduleSearch(((SearchView)getComponent(2)).getSearchText());
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int idx=0; idx<((CommonScrollPane)getComponent(1)).getContentPane().getComponentCount(); idx++) 
			if(e.getSource()==((CommonScrollPane)getComponent(1)).getContentPane().getComponent(idx)) {
				if(clickedIdx==idx)	searchedScheduleSelectListener.onSearchedScheduleSelect(clickedIdx);
				else {
					if(clickedIdx>=0)
						((ScheduleView)((CommonScrollPane)getComponent(1)).getContentPane().getComponent(clickedIdx)).showClickedStatement(false);
					clickedIdx=idx;
					((ScheduleView)((CommonScrollPane)getComponent(1)).getContentPane().getComponent(clickedIdx)).showClickedStatement(true);
					revalidate();
					repaint();
				}
				break;
			}
	}

	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.2f, 0.45f);
	}
	
	private class ScheduleView extends ActionCallablePanel {
		private ScheduleView(String ymd, String title) {
			setOpaque(false);
			setBackground(ColorUtil.getClickedViewColor());
			setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			add(createLabel(ymd, JLabel.RIGHT), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
			add(createLabel(title, JLabel.CENTER), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		}
		
		private void showClickedStatement(boolean isClicked) {
			setOpaque(isClicked);
		}
		
		private TransparentLabel createLabel(String text, int alignment) {
			TransparentLabel l = new TransparentLabel(text);
			l.setHorizontalAlignment(alignment);
			l.setVerticalAlignment(JLabel.CENTER);
			l.setFont(FontUtil.createDefaultFont(15f));
			l.setForeground(ColorUtil.getLightColor(ColorUtil.getLightGray()));
			return l;
		}
	}
}
