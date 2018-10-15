package calendar;

import java.util.Calendar;

import model.YMD;

public final class CalendarUtil {
	public static final int ROW = 6;
	public static final int COL = 7;
	private static final int[][] MATRIX = new int[ROW][COL];
	private static final Calendar CALENDAR = Calendar.getInstance();
	 
	private CalendarUtil() {}
	
	private static void setMatrix(int year, int month) {
		CALENDAR.set(Calendar.YEAR, year);
		CALENDAR.set(Calendar.MONTH, month-1);
		CALENDAR.set(Calendar.DATE, 1);
		
		int startDay = CALENDAR.get(Calendar.DAY_OF_WEEK);
		int lastDate = CALENDAR.getActualMaximum(Calendar.DATE);
		int inputDate = 1;
		int rowCursor = 0;
		for(int colCursor=1; colCursor<=ROW*COL; colCursor++) {
			if(colCursor>=startDay&&inputDate<=lastDate)	MATRIX[rowCursor][(colCursor-1)%COL] = inputDate++;
			else	MATRIX[rowCursor][(colCursor-1)%COL] = 0;
			
			rowCursor = colCursor%COL==0 ? rowCursor+1 : rowCursor;
		}
	}
	
	public static int[][] getCalendarMatrix(int year, int month) {
		setMatrix(year, month);
		return MATRIX;
	}
	
	public static YMD getToday() {
		Calendar cal = Calendar.getInstance();
		return new YMD(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
	}
}
