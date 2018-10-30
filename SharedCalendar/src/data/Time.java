package data;

public class Time {
	private final int DATA[] = new int[Type.values().length];
	
	public Time() {}
	
	public Time(int hour, int minute) {
		setTime(hour, minute);
	}
	
	public Time(Time time) {
		setTime(time);
	}
	/**
	 * Set time by using string type of time. String type format is "hh : mm" , string length is 7.
	 * @param strType String format of time.
	 * @throws Exception String type is illegal format.
	 */
	public Time(String strType) throws IllegalArgumentException {
		setTime(strType);
	}
	
	public void setTime(int hour, int minute) {
		DATA[Type.HOUR.ordinal()] = hour;
		DATA[Type.MINUTE.ordinal()] = minute;
	}
	
	public void setTime(Time time) {
		setTime(time.getHour(), time.getMinute());
	}
	
	public void setTime(String strType) throws IllegalArgumentException {
		if(strType.length()!=7)	throw new IllegalArgumentException("The argument is illegal format.");
		try {
			setTime(Integer.parseInt(strType.substring(0,2)), Integer.parseInt(strType.substring(5,7)));
		} catch(Exception e) {	throw new IllegalArgumentException("The argument is illegal format.");	}
	}
	
	public int getHour() {
		return get(Type.HOUR);
	}
	
	public int getMinute() {
		return get(Type.MINUTE);
	}
	
	public int get(Type type) {
		return DATA[type.ordinal()];
	}
	
	public String getValidString() {
		return (getHour()<10?"0":"")+getHour()+" : "+(getMinute()<10?"0":"")+getMinute();
	}
	
	public boolean isValid() {
		return getHour()>=0 && getHour()<24 && getMinute()>=0 && getMinute()<60;
	}
	
	@Override
	public String toString() {
		return getValidString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Time))	return false;
		for(Type t : Type.values())
			if(get(t)!=((Time)obj).get(t))	return false;
		return true;
	}
	
	public static enum Type {
		HOUR, MINUTE;
	}
}
