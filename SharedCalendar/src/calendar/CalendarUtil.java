package calendar;

import java.util.Calendar;

import data.YMD;

public final class CalendarUtil {
	public static final int ROW = 6;
	public static final int COL = 7;
	
	public static final int MIN_YEAR = 1850;
	public static final int MAX_YEAR = 2150;
	public static final int MIN_MONTH = 1;
	public static final int MAX_MONTH = 12;
	public static final int MIN_DATE = 1;
	private static final int[][] MAX_DATE = new int[][] {
		{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
		{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
	};
	
	private static final int[][] MATRIX = new int[ROW][COL];
	private static final Calendar CALENDAR = Calendar.getInstance();
	 
	private CalendarUtil() {}
	
	private static void setMatrix(int year, int month) {
		CALENDAR.set(Calendar.YEAR, year);
		CALENDAR.set(Calendar.MONTH, month-1);
		CALENDAR.set(Calendar.DATE, 1);
		
		int startDay = CALENDAR.get(Calendar.DAY_OF_WEEK);
		int lastDate = getMaxDate(year, month);
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
	
	public static int getMaxDate(int year, int month) {
		return MAX_DATE[year%4==0?0:1][month-1];
	}
}
