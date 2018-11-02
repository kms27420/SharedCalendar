package model;

import java.util.ArrayList;
import java.util.List;

import calendar.CalendarUtil;
import data.Friend;
import data.Schedule;
import data.Sharing;
import data.Time;
import data.YMD;

public class DataStore {
	private List<Schedule> schedules = new ArrayList<>();
	private List<Friend> friends = new ArrayList<>();
	private List<Sharing> sharing = new ArrayList<>();
	
	private static YMD beingShownYMD = CalendarUtil.getToday();
	private List<Schedule> beingShownSchedules = new ArrayList<>();
	
	private List<Schedule> searchedSchedules = new ArrayList<>();
	private List<Friend> searchedFriends = new ArrayList<>();
	private List<Friend> sharedFriends = new ArrayList<>();
	private List<Friend> unsharedFriends = new ArrayList<>();
	
	private static String loginedID;
	private boolean isLogin;
	
	public DataStore() {}
	
	public void setLoginedID(String loginedID) {
		DataStore.loginedID = loginedID;
	}
	
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public void setBeingShownYMD(YMD ymd) {
		beingShownYMD = new YMD(ymd);
		beingShownSchedules = getSchedules(ymd);
	}
	
	public void setSchedules(String[][] data) {
		schedules = new ArrayList<>();
		for(String[] ss : data) 
			schedules.add(new Schedule(Integer.parseInt(ss[0]), ss[1], ss[2], ss[3], new YMD(ss[4]), new Time(ss[5]), new Time(ss[6])));
	}
	
	public void setFriends(String[][] data) {
		friends = new ArrayList<>();
		for(String[] ff : data)
			friends.add(new Friend(ff[0], ff[1]));
	}
	
	public void setSharing(String[][] data) {
		sharing = new ArrayList<>();
		for(String[] ss : data)
			sharing.add(new Sharing(Integer.parseInt(ss[0]), ss[1], ss[2]));
	}
	
	public void setSearchedSchedules(String keyword) {
		searchedSchedules = new ArrayList<>();
		for(Schedule s : schedules)
			if(isContains(s.getTitle(), keyword) || isContains(s.getContent(), keyword))	searchedSchedules.add(new Schedule(s));
	}
	
	public void setSharedFriends(int scheduleID) {
		sharedFriends = new ArrayList<>();
		for(Sharing s : sharing)
			if(s.getScheduleID()==scheduleID)
				sharedFriends.add(getFriend(s.getOwnerID().equals(loginedID)?s.getGuestID():s.getOwnerID()));
	}
	
	public void setUnsharedFriends(int scheduleID) {
		unsharedFriends = getAllFriends();
		for(Sharing s : sharing)
			if(s.getScheduleID()==scheduleID)
				unsharedFriends.remove(new Friend(s.getGuestID()));
	}
	
	public void setSearchedFriends(String[][] friends) {
		searchedFriends = new ArrayList<>();
		for(String[] f : friends)
			searchedFriends.add(new Friend(f[0], f[1]));
	}
	
	public void updateSchedule(Schedule update) {
		for(Schedule s : schedules)
			if(s.equals(update)) {
				s.setSchedule(update);
				return;
			}
	}
	
	public void addSchedule(Schedule add) {
		schedules.add(new Schedule(add));
	}
	
	public void addFriend(Friend add) {
		friends.add(add);
	}
	
	public void addSharing(Sharing add) {
		this.sharing.add(add);
	}
	
	public boolean deleteSchedule(Schedule delete) {
		return schedules.remove(delete);
	}
	
	public boolean deleteFriend(Friend delete) {
		return friends.remove(delete);
	}
	
	public boolean deleteSharing(Sharing delete) {
		return sharing.remove(delete);
	}
	
	public boolean deleteSearchedFriend(Friend f) {
		return searchedFriends.remove(f);
	}
	
	public boolean isLogined() {
		return isLogin;
	}
	
	public static String getLoginedID() {
		return loginedID;
	}
	
	public static YMD getBeingShownYMD() {
		return new YMD(beingShownYMD);
	}
	
	public List<Schedule> getBeingShownSchedules() {
		List<Schedule> rv = new ArrayList<>();
		for(Schedule s : beingShownSchedules)	rv.add(new Schedule(s));
		return rv;
	}
	
	public List<Schedule> getSearchedSchedules() {
		List<Schedule> rv = new ArrayList<>();
		for(Schedule s : searchedSchedules)
			rv.add(new Schedule(s));
		return rv;
	}
	
	public List<Friend> getAllFriends() {
		List<Friend> rv = new ArrayList<>();
		for(Friend f : friends)	rv.add(new Friend(f));
		return rv;
	}
	
	public List<Friend> getSharedFriends() {
		List<Friend> rv = new ArrayList<>();
		for(Friend f : sharedFriends)
			rv.add(new Friend(f));
		return rv;
	}
	
	public List<Friend> getUnsharedFriends() {
		List<Friend> rv = new ArrayList<>();
		for(Friend f : unsharedFriends)
			rv.add(new Friend(f));
		return rv;
	}
	
	public List<Friend> getSearchedFriends() {
		List<Friend> rv = new ArrayList<>();
		for(Friend f : searchedFriends)
			rv.add(new Friend(f));
		return rv;
	}
	
	private List<Schedule> getSchedules(YMD ymd) {
		List<Schedule> rv = new ArrayList<>();
		for(Schedule s : schedules)
			if(s.getYMD().equals(ymd))	rv.add(new Schedule(s));
		return rv;
	}
	
	private Friend getFriend(String id) {
		for(Friend f : friends)
			if(f.getID().equals(id))	return new Friend(f);
		return null;
	}
	
	private boolean isContains(String compare, String keyword) {
		if(keyword.length()>compare.length())	return false;
		int kidx = -1;
		for(int i=0; i<compare.length(); i++) {
			if(kidx++==keyword.length()-1)	return true;
			kidx = compare.charAt(i)!=keyword.charAt(kidx) ? -1 : kidx;
			if(kidx==keyword.length()-1)	return true;
		}
		return false;
	}
}
