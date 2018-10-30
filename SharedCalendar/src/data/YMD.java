package data;

import calendar.CalendarUtil;

/**
 * Data class that contains year, month and date information.
 * @author Kwon
 *
 */
public class YMD {
	private final int[] DATA = new int[Type.values().length];
	
	public YMD() {}
	
	public YMD(int year, int month, int date) {
		setYMD(year, month, date);
	}
	
	public YMD(YMD ymd) {
		setYMD(ymd);
	}
	
	public YMD(String strType) throws IllegalArgumentException {
		setYMD(strType);
	}
	
	public void setYMD(int year, int month, int date) {
		setYear(year);
		setMonth(month);
		setDate(date);
	}
	
	public void setYMD(YMD ymd) {
		setYear(ymd.getYear());
		setMonth(ymd.getMonth());
		setDate(ymd.getDate());
	}
	
	public void setYMD(String strType) throws IllegalArgumentException {
		if(strType.length()!=8)	throw new IllegalArgumentException("The argument is not YMD string type.");
		try {
			setYear(Integer.parseInt(strType.substring(0,4)));
			setMonth(Integer.parseInt(strType.substring(4,6)));
			setDate(Integer.parseInt(strType.substring(6,8)));
		} catch(Exception e) {	throw new IllegalArgumentException("The argument is not time string type.");	}
	}
	
	public void setYear(int year) {
		set(Type.YEAR, year);
	}
	
	public void setMonth(int month) {
		set(Type.MONTH, month);
	}
	
	public void setDate(int date) {
		set(Type.DATE, date);
	}
	
	public void set(Type type, int value) {
		DATA[type.ordinal()] = value;
	}
	
	public int getYear() {
		return get(Type.YEAR);
	}
	
	public int getMonth() {
		return get(Type.MONTH);
	}
	
	public int getDate() {
		return get(Type.DATE);
	}
	
	public int get(Type type) {
		return DATA[type.ordinal()];
	}
	
	public String getString() {
		return getYear()+(getMonth()<10?"0":"")+getMonth()+(getDate()<10?"0":"")+getDate();
	}
	
	public boolean isValid() {
		return get(Type.YEAR) >= CalendarUtil.MIN_YEAR && get(Type.YEAR) <= CalendarUtil.MAX_YEAR
				&& get(Type.MONTH) >= CalendarUtil.MIN_MONTH && get(Type.MONTH) <= CalendarUtil.MAX_MONTH
				&& get(Type.DATE) >= CalendarUtil.MIN_DATE
				&& get(Type.DATE) <= CalendarUtil.getMaxDate(get(Type.YEAR), get(Type.MONTH));
	}
	
	@Override
	public String toString() {
		return getString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof YMD))	return false;
		for(Type t : Type.values())	if(get(t)!=((YMD)obj).get(t))	return false;
		return true;
	}
	
	public static enum Type {
		YEAR, MONTH, DATE;
	}
}
