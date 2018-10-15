package model;

public class Time {
	private int hour;
	private int minute;
	
	public Time() {}
	
	public Time(int hour, int minute) { 
		setTime(hour, minute);
	}
	
	public Time(Time time) {
		setTime(time);
	}
	
	public void setTime(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	
	public void setTime(Time time) {
		hour = time.getHour();
		minute = time.getMinute();
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	@Override
	public String toString() {
		return hour + ":" + minute;
	}
}
