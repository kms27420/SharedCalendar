package view.calendar;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import calendar.CalendarUtil;
import calendar.DayInEnglish;
import data.YMD;
import listener.view.ymd.YMDSelectAdapter;
import listener.view.ymd.YMDSelectListener;
import view.abv.YMDView;
import view.customized.TransparentPanel;

public class DatePanel extends TransparentPanel implements ActionListener, YMDView {
	private YMDSelectListener ymdSelectListener = new YMDSelectAdapter();
	
	public DatePanel() {
		setLayout(new GridLayout(CalendarUtil.ROW, CalendarUtil.COL));
		for(int i=0;i<((GridLayout)getLayout()).getRows();i++)
			for(int j=0;j<((GridLayout)getLayout()).getColumns();j++)
				add(new DateView());
		for(Component comp : getComponents())
			((DateView)comp).addActionListener(this);
	}
 
	public void setYMDSelectListener(YMDSelectListener ymdSelectListener) {
		this.ymdSelectListener = ymdSelectListener;
	}
	
	private int[][] dateArray;
	@Override
	public void showYMD(YMD ymd) {
		dateArray = CalendarUtil.getCalendarMatrix(ymd.getYear(), ymd.getMonth());
		for(int row=0;row<dateArray.length;row++) {
			for(int col=0;col<dateArray[row].length;col++) {
				if(dateArray[row][col]==0)
					((DateView)getComponent(row*dateArray[row].length + col)).showEmpty();
				else
					((DateView)getComponent(row*dateArray[row].length + col)).showDate(dateArray[row][col], col==DayInEnglish.SUN.ordinal());
				
				((DateView)getComponent(row*dateArray[row].length + col)).showFocusedView(dateArray[row][col]==ymd.getDate());
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof DateView))	return;
		if(((DateView)e.getSource()).getText()=="")	return;
		ymdSelectListener.onYMDSelect(Integer.parseInt(((DateView)e.getSource()).getText()));
	}
}
