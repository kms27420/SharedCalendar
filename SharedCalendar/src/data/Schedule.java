package data;

public class Schedule {
	private final Object[] DATA = new Object[Type.values().length];
	
	public Schedule() {}
	
	public Schedule(int scheduleID) {
		setScheduleID(scheduleID);
	}
	
	public Schedule(String title, String content, YMD ymd, Time startTime, Time endTime) {
		setScheduleID(0);
		setTitle(title);
		setContent(content);
		setYMD(ymd);
		setStartTime(startTime);
		setEndTime(endTime);
	}
	
	public Schedule(int scheduleID, String title, String content, YMD ymd, Time startTime, Time endTime) {
		this(title, content, ymd, startTime, endTime);
		setScheduleID(scheduleID);
	}
	
	public Schedule(int scheduleID, String ownerID, String title, String content, YMD ymd, Time startTime, Time endTime) {
		this(scheduleID, title, content, ymd, startTime, endTime);
	}
	
	public Schedule(Schedule schedule) {
		setSchedule(schedule);
	}
	
	public void setScheduleID(int scheduleID) {
		set(Type.SCHEDULE_ID, scheduleID);
	}
	
	public void setOwnerID(String ownerID) {
		set(Type.OWNER_ID, ownerID);
	}
	
	public void setTitle(String title) {
		set(Type.TITLE, title);
	}
	
	public void setContent(String content) {
		set(Type.CONTENT, content);
	}
	
	public void setYMD(YMD ymd) {
		set(Type.YMD, ymd);
	}
	
	public void setStartTime(Time startTime) {
		set(Type.START_TIME, startTime);
	}
	
	public void setEndTime(Time endTime) {
		set(Type.END_TIME, endTime);
	}
	
	public void setSchedule(Schedule schedule) {
		for(Type t : Type.values())
			set(t, schedule.get(t));
	}
	
	private void set(Type type, Object value) {
		DATA[type.ordinal()] = value;
	}
	
	public int getScheduleID() {
		return (int)get(Type.SCHEDULE_ID);
	}
	
	public String getOwnerID() {
		return (String)get(Type.OWNER_ID);
	}
	
	public String getTitle() {
		return (String)get(Type.TITLE);
	}
	
	public String getContent() {
		return (String)get(Type.CONTENT);
	}
	
	public YMD getYMD() {
		return new YMD((YMD)get(Type.YMD));
	}
	
	public Time getStartTime() {
		return new Time((Time)get(Type.START_TIME));
	}
	
	public Time getEndTime() {
		return new Time((Time)get(Type.END_TIME));
	}
	
	private Object get(Type type) {
		return DATA[type.ordinal()];
	}
	
	public boolean isValid() {
		return ((YMD)get(Type.YMD)).isValid() && 
				((Time)get(Type.START_TIME)).isValid() && ((Time)get(Type.END_TIME)).isValid();
	}
	
	public boolean isContentsEquals(Schedule compare) {
		for(Type t : Type.values())
			if(!t.equals(Type.SCHEDULE_ID) && !get(t).equals(compare.get(t)))	return false;
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Schedule))	return false;
		return getScheduleID()==((Schedule)obj).getScheduleID();
	}
	
	@Override
	public String toString() {
		return getScheduleID() + "\n" + getTitle() + "\n" + getContent() + "\n"
				+ getYMD().toString() + "\n" + getStartTime().toString() + "\n" + getEndTime().toString() + "\n";
	}
	
	public static enum Type {
		SCHEDULE_ID, OWNER_ID, TITLE, CONTENT, YMD, START_TIME, END_TIME;
	}
}
