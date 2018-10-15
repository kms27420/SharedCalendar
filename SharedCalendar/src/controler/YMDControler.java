package controler;

import java.util.List;

import calendar.CalendarUtil;
import listener.YMDSelectListener;
import model.Schedule;
import model.YMD;
import view.calendar_panel.YMDPanel;

public class YMDControler implements YMDSelectListener {
	private DBControler dbControler = new DBControler();
	private YMD beingShownYMD = CalendarUtil.getToday();
	private List<Schedule> beingShownSchedules = dbControler.getSchedules(beingShownYMD);
	
	private final YMDPanel YMD_PANEL;
	
	public YMDControler(YMDPanel ymdPanel) {
		YMD_PANEL = ymdPanel;
		YMD_PANEL.showYMDView(beingShownYMD);
	}
	
	@Override
	public void onYMDSelect(int selectedYear, int selectedMonth, int selectedDate) {
		if(selectedYear!=NO_SELECTED)	beingShownYMD.setYear(selectedYear);
		if(selectedMonth!=NO_SELECTED)	beingShownYMD.setMonth(selectedMonth);
		if(selectedDate!=NO_SELECTED)	beingShownYMD.setDate(selectedDate);
		beingShownSchedules = dbControler.getSchedules(beingShownYMD);
		YMD_PANEL.showYMDView(beingShownYMD); 
	}
}
