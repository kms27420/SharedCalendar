package processor;

import controler.ServerCommunicator;
import data.Friend;
import data.Schedule;
import enumeration.Header;

public class ExternalDBProcessor implements DBProcessor {
	private final ServerCommunicator COMMUNICATOR;
	
	public ExternalDBProcessor(ServerCommunicator communicator) {
		COMMUNICATOR = communicator;
	}

	@Override
	public void loadDBData() {}
	
	@Override
	public void insertSchedule(Schedule insert) {
		try {
			COMMUNICATOR.sendData(Header.INSERT_SCHEDULE, new String[]{insert.getTitle(), insert.getContent(), 
					insert.getYMD().toString(), insert.getStartTime().toString(), insert.getEndTime().toString()});
		} catch(Exception e) {}
	}
	
	@Override
	public void updateSchedules(Schedule update) {
		try {
			COMMUNICATOR.sendData(Header.UPDATE_SCHEDULE, new String[]{update.getScheduleID()+"", update.getTitle(), update.getContent(), 
					update.getYMD().toString(), update.getStartTime().toString(), update.getEndTime().toString()});
		} catch(Exception e) {}
	}
	
	@Override
	public void deleteSchedules(Schedule delete) {
		try {
			COMMUNICATOR.sendData(Header.DELETE_SCHEDULE, new String[]{delete.getScheduleID()+""});
		} catch(Exception e) {}
	}

	@Override
	public void insertFriend(Friend friend) {
		try {
			COMMUNICATOR.sendData(Header.INSERT_FRIEND, new String[]{friend.getID()});
		} catch(Exception e) {}
	}
	
	@Override
	public void deleteFriend(Friend friend) {
		try {
			COMMUNICATOR.sendData(Header.DELETE_FRIEND, new String[]{friend.getID()});
		} catch(Exception e) {}
	}
	
	@Override
	public void searchFriend(String keyword) {
		try {
			COMMUNICATOR.sendData(Header.SEARCH_FRIEND, new String[]{keyword});
		} catch(Exception e) {}
	}
	
	@Override
	public void share(int scheduleID, String[] guests) {
		String[] data = new String[guests.length+1];
		data[0] = scheduleID+"";
		for(int i=0; i<guests.length; i++)
			data[i+1] = guests[i];
		try {
			COMMUNICATOR.sendData(Header.SHARE, data);
		} catch(Exception e) {}
	}
	
	@Override
	public void unshare(int scheduleID, String guestID) {
		try {
			COMMUNICATOR.sendData(Header.UNSHARE, new String[]{scheduleID+"", guestID});
		} catch(Exception e) {}
	}
}
