package view.calendar_panel;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import calendar.CalendarUtil;
import listener.YMDSelectListener;
import model.YMD;
import view.customized.TransparentPanel;

public class DatePanel extends TransparentPanel implements ActionListener, YMDPanel {
	private YMDSelectListener ymdSelectListener = (y,m,d)->{};
	
	public DatePanel() {
		setLayout(new GridLayout(CalendarUtil.ROW, CalendarUtil.COL));
		for(int i=0;i<((GridLayout)getLayout()).getRows();i++)
			for(int j=0;j<((GridLayout)getLayout()).getColumns();j++)
				add(new DateView());
		for(Component comp : getComponents())
			((DateView)comp).addActionListener(this);
	}
 
	private int[][] dateArray;
	@Override
	public void showYMDView(YMD ymd) {
		dateArray = CalendarUtil.getCalendarMatrix(ymd.getYear(), ymd.getMonth());
		for(int row=0;row<dateArray.length;row++) {
			for(int col=0;col<dateArray[row].length;col++) {
				if(dateArray[row][col]==0)
					((DateView)getComponent(row*dateArray[row].length + col)).showEmpty();
				else
					((DateView)getComponent(row*dateArray[row].length + col)).showDate(dateArray[row][col], col==0);
				
				((DateView)getComponent(row*dateArray[row].length + col)).showFocusedView(dateArray[row][col]==ymd.getDate());
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof DateView))	return;
		if(((DateView)e.getSource()).getText()=="")	return;
		ymdSelectListener.onYMDSelect(YMDSelectListener.NO_SELECTED, YMDSelectListener.NO_SELECTED, Integer.parseInt(((DateView)e.getSource()).getText()));
	}
	
	void setYMDSelectListener(YMDSelectListener ymdSelectListener) {
		this.ymdSelectListener = ymdSelectListener;
	}
}
