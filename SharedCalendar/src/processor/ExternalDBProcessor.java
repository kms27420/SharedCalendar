package processor;

import controler.ServerCommunicator;
import data.Friend;
import data.Schedule;
import data.SharedSchedule;
import data.YMD;
import enumeration.Header;

public class ExternalDBProcessor implements DBProcessor {
	private final ServerCommunicator COMMUNICATOR;
	
	public ExternalDBProcessor(ServerCommunicator communicator) {
		COMMUNICATOR = communicator;
	}

	@Override
	public void onScheduleInsert(Schedule insert) {
		try {
			COMMUNICATOR.sendData(Header.INSERT_SCHEDULE, new String[]{
					insert.getTitle(), insert.getContent(), insert.getYMD().toString(), 
					insert.getStartTime().toString(), insert.getEndTime().toString()
			});
		} catch(Exception e) {}
	}

	@Override
	public void onScheduleUpdate(Schedule update) {
		try {
			COMMUNICATOR.sendData(Header.UPDATE_SCHEDULE, new String[]{
					update.getScheduleID()+"", update.getTitle(), update.getContent(),
					update.getYMD().toString(), update.getStartTime().toString(), update.getEndTime().toString()
			});
		} catch(Exception e) {}
	}

	@Override
	public void onScheduleDelete(Schedule delete) {
		try {
			COMMUNICATOR.sendData(Header.DELETE_SCHEDULE, new String[]{delete.getScheduleID()+""});
		} catch(Exception e) {}
	}

	@Override
	public void onShare(SharedSchedule share) {
		try {
			for(Friend f : share.getSharingFriends())
				COMMUNICATOR.sendData(Header.INSERT_SHARING, new String[]{share.getScheduleID()+"", f.getID()});
		} catch(Exception e) {}
	}

	@Override
	public void onUnshare(SharedSchedule unshare) {
		try {
			for(Friend f : unshare.getSharingFriends())
				COMMUNICATOR.sendData(Header.DELETE_FRIEND, new String[]{unshare.getScheduleID()+"", f.getID()});
		} catch(Exception e) {}
	}

	@Override
	public void onFriendInsert(String id) {
		try {
			COMMUNICATOR.sendData(Header.INSERT_FRIEND, new String[]{id});
		} catch(Exception e) {}
	}

	@Override
	public void onFriendDelete(String id) {
		try {
			COMMUNICATOR.sendData(Header.DELETE_FRIEND, new String[]{id});
		} catch(Exception e) {}
	}

	@Override
	public void onSchedulesSelectByYMD(YMD search) {
		try {
			COMMUNICATOR.sendData(Header.SELECT_SCHEDULE_BY_YMD, new String[]{search.toString()});
		} catch(Exception e) {}
	}

	@Override
	public void onSchedulesSelectBySearch(String search) {
		try {
			COMMUNICATOR.sendData(Header.SELECT_SCHEDULE_BY_KEYWORD, new String[]{search});
		} catch(Exception e){}
	}

	@Override
	public void onSelectFriends() {
		try {
			COMMUNICATOR.sendData(Header.SELECT_FRIENDS, new String[]{});
		} catch(Exception e) {}
	}

	@Override
	public void onSelectFriend(String id) {
		try {
			COMMUNICATOR.sendData(Header.SELECT_FRIEND, new String[]{id});
		} catch(Exception e) {}
	}
}
