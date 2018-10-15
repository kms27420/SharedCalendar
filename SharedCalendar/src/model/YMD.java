package model;

/**
 * Data class that contains year, month and date information.
 * @author Kwon
 *
 */
public class YMD {	
	private int year;
	private int month; 
	private int date;
	
	public YMD() {}
	
	public YMD(int year, int month, int date) {
		setYMD(year, month, date);
	}
	
	public YMD(YMD ymd) {
		setYMD(ymd);
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
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setDate(int date) {
		this.date = date;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDate() {
		return date;
	}
}
