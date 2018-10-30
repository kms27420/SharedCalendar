package view.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.YMD;
import listener.ymd.YMDSelectAdapter;
import listener.ymd.YMDSelectListener;
import view.YMDView;
import view.calendar.MonthMoveButton.Direction;
import view.customized.TransparentPanel;

public class CalendarPanel extends JPanel implements YMDView, ActionListener {
	private final Color BG = Color.WHITE;
	private int sideViewWidth = 40;
	private YMDSelectListener ymdSelectListener = new YMDSelectAdapter();
	
	public CalendarPanel() {
		setBackground(BG);
		setLayout(new BorderLayout());
		add(new SideView(Direction.LEFT), BorderLayout.WEST);
		add(new CentralView(), BorderLayout.CENTER);
		add(new SideView(Direction.RIGHT), BorderLayout.EAST);
		
		getComponent(0).setPreferredSize(new Dimension(sideViewWidth,0));
		getComponent(2).setPreferredSize(getComponent(0).getPreferredSize());
		for(MonthMoveButton bt : getMonthMoveButtons())	bt.addActionListener(this);
	} 

	@Override
	public void showYMD(YMD ymd) {
		getYearMonthPanel().showYMD(ymd);
		getDatePanel().showYMD(ymd);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof MonthMoveButton))	return;
		ymdSelectListener.onYMDSelect(0, ((MonthMoveButton)e.getSource()).DIRECTION==Direction.LEFT ? -1 : 1);
	}
	
	public void setYMDSelectListener(YMDSelectListener ymdSelectListener) {
		this.ymdSelectListener = ymdSelectListener;
		getYearMonthPanel().setYMDSelectListener(ymdSelectListener);
		getDatePanel().setYMDSelectListener(ymdSelectListener);
	}
	
	private YearMonthPanel getYearMonthPanel() {
		return (YearMonthPanel)((Container)getComponent(1)).getComponent(0);
	}
	
	private DatePanel getDatePanel() {
		return (DatePanel)((Container)getComponent(1)).getComponent(2);
	}
	
	private MonthMoveButton[] getMonthMoveButtons() {
		return new MonthMoveButton[] {
				(MonthMoveButton)((SideView)getComponent(0)).getComponent(0), 
				(MonthMoveButton)((SideView)getComponent(2)).getComponent(0)
			   };
	}
	
	private class CentralView extends TransparentPanel {
		private CentralView() {
			setLayout(new LinearLayout(Orientation.VERTICAL, 0));
			add(new YearMonthPanel(), new LinearConstraints(15, LinearSpace.MATCH_PARENT));
			add(new DaysView(), new LinearConstraints(10, LinearSpace.MATCH_PARENT));
			add(new DatePanel(), new LinearConstraints(45, LinearSpace.MATCH_PARENT));
		}
	}
	
	private class SideView extends TransparentPanel {
		private int buttonWidth = 30;
		private int buttonHeight = 30;
		
		private SideView(Direction direction) {
			setLayout(null);
			add(new MonthMoveButton(direction));
			getComponent(0).setSize(buttonWidth, buttonHeight);
		}
		
		private int pw, ph;
		@Override
		public void paintComponent(Graphics g) {
			if(pw!=getWidth()||ph!=getHeight()) {
				pw = getWidth();
				ph = getHeight();
				getComponent(0).setLocation((pw-buttonWidth)/2, (ph-buttonHeight)/2);	
			}
			super.paintComponent(g);
		}
	}
}
