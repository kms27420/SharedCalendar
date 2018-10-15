package model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
	private YMD ymd = new YMD();
	private Time startTime = new Time(); 
	private Time endTime = new Time();
	private String title;
	private String content;
	private List<String> sharedFriends = new ArrayList<>();
	
	public Schedule() {}
	
	public Schedule(YMD ymd, Time startTime, Time endTime, String title, String content) {
		setYMD(ymd);
		setStartTime(startTime);
		setEndTime(endTime);
		setTitle(title);
		setContent(content);
	}
	
	public Schedule(YMD ymd, Time startTime, Time endTime, String title, String content, List<String> sharedFriends) {
		this(ymd, startTime, endTime, title, content);
		setSharedFriends(sharedFriends);
	}
	
	public Schedule(YMD ymd, Time startTime, Time endTime, String title, String content, String[] sharedFriends) {
		this(ymd, startTime, endTime, title, content);
		setSharedFriends(sharedFriends);
	}
	
	public void setYMD(YMD ymd) {
		this.ymd.setYMD(ymd);
	}
	
	public void setStartTime(Time startTime) {
		this.startTime.setTime(startTime);
	}
	
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setSharedFriends(List<String> sharedFriends) {
		this.sharedFriends.clear();
		for(String sharedFriend : sharedFriends)
			this.sharedFriends.add(sharedFriend);
	}
	
	public void setSharedFriends(String[] sharedFriends) {
		this.sharedFriends.clear();
		for(String sharedFriend : sharedFriends)
			this.sharedFriends.add(sharedFriend);
	}
	
	public void addSharedFriend(String sharedFriend) {
		sharedFriends.add(sharedFriend);
	}
	
	public YMD getYMD() {
		return new YMD(ymd);
	}
	
	public Time getStartTime() {
		return new Time(startTime);
	}
	
	public Time getEndTime() {
		return new Time(endTime);
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String[] getSharedFriends() {
		return sharedFriends.toArray(new String[sharedFriends.size()]);
	}
}
