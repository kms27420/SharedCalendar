package view.calendar_panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import listener.YMDSelectListener;
import model.YMD;
import view.customized.ActionCallableLabel;
import view.customized.TransparentPanel;

public class CalendarPanel extends JPanel implements YMDPanel {
	private final Color BG = Color.WHITE;
	
	public CalendarPanel() {
		setBackground(BG);
		setLayout(new BorderLayout());
		add(new SideView(SideView.LEFT), BorderLayout.WEST);
		add(new CentralView(), BorderLayout.CENTER);
		add(new SideView(SideView.RIGHT), BorderLayout.EAST);
		
		getComponent(0).setPreferredSize(new Dimension(40,0));
		getComponent(2).setPreferredSize(new Dimension(40,0));
	} 

	@Override
	public void showYMDView(YMD ymd) {
		getYearMonthPanel().showYMDView(ymd);
		getDatePanel().showYMDView(ymd);
	}
	
	public void setYMDSelectListener(YMDSelectListener ymdSelectListener) {
		getYearMonthPanel().setYMDSelectListener(ymdSelectListener);
		getDatePanel().setYMDSelectListener(ymdSelectListener);
	}
	
	private YearMonthPanel getYearMonthPanel() {
		return ((CentralView)getComponent(1)).getYearMonthPanel();
	}
	
	private DatePanel getDatePanel() {
		return ((CentralView)getComponent(1)).getDatePanel();
	}
	
	private class CentralView extends TransparentPanel {
		private CentralView() {
			setLayout(new LinearLayout(Orientation.VERTICAL, 0));
			add(new YearMonthPanel(), new LinearConstraints(15, LinearSpace.MATCH_PARENT));
			add(new DaysView(), new LinearConstraints(10, LinearSpace.MATCH_PARENT));
			add(new DatePanel(), new LinearConstraints(45, LinearSpace.MATCH_PARENT));
		}
		
		private YearMonthPanel getYearMonthPanel() {
			return (YearMonthPanel)getComponent(0);
		}
		
		private DatePanel getDatePanel() {
			return (DatePanel)getComponent(2);
		}
	}
	
	private static class SideView extends ActionCallableLabel {
		private static final String LEFT = "left";
		private static final String RIGHT = "right";
		
		private Color circleColor = new Color(255, 121, 98);
		private Color arrowColor = Color.WHITE;
		private String arrowDirection;
		
		private SideView(String arrowDirection) {
			setOpaque(false);
			this.arrowDirection = arrowDirection;
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(circleColor);
			g.fillOval((getWidth()-30)/2, (getHeight()-30)/2, 30, 30);
			g.setColor(arrowColor);
			g.drawLine((getWidth()-15)/2, getHeight()/2, (getWidth()+15)/2, getHeight()/2);
			switch(arrowDirection) {
			case LEFT :
				g.drawLine((getWidth()-15)/2, getHeight()/2, getWidth()/2, (getHeight()-15)/2);
				g.drawLine((getWidth()-15)/2, getHeight()/2, getWidth()/2, (getHeight()+15)/2);
				break;
			case RIGHT :
				g.drawLine((getWidth()+15)/2, getHeight()/2, getWidth()/2, (getHeight()-15)/2);
				g.drawLine((getWidth()+15)/2, getHeight()/2, getWidth()/2, (getHeight()+15)/2);
				break;
				default : break;
			}
		}
	}
}
