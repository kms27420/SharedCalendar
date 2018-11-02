package controler;

import java.io.IOException;
import java.util.List;

import calendar.CalendarUtil;
import data.Friend;
import data.Login;
import data.Schedule;
import data.Sharing;
import data.Time;
import data.YMD;
import enumeration.Header;
import exception.ServerConnectionException;
import listener.DBSuccessListener;
import listener.view.ViewEventListener;
import model.DataStore;
import processor.AutoLoginInfoGetter;
import processor.DBProcessor;
import processor.ExternalDBProcessor;
import processor.InternalDBProcessor;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.MainPanel;
import view.popup.FriendDeletePanel;
import view.popup.FriendSearchPanel;
import view.popup.JoinPanel;
import view.popup.LoginPanel;
import view.popup.YMDSelectPanel;
import view.popup.common.AlertView;
import view.popup.schedule.ScheduleDeletePanel;
import view.popup.schedule.ScheduleDetailView;
import view.popup.schedule.ScheduleInsertPanel;
import view.popup.schedule.ScheduleSearchPanel;
import view.popup.schedule.ScheduleSharePanel;
import view.popup.schedule.ScheduleUnsharePanel;
import view.popup.schedule.ScheduleUpdatePanel;

public class MainControler implements ViewEventListener, DBSuccessListener {
	private final MainPanel MAIN_PANEL = new MainPanel();
	private final DataStore DATA_STORE = new DataStore();
	
	private final ReceiveDataControler RECEIVE_DATA_PROCESSOR = new ReceiveDataControler(this);
	private final InternalDBProcessor INTERNAL_DB_PROCESSOR = new InternalDBProcessor(this);
	private final ServerCommunicator COMMUNICATOR = new ServerCommunicator(this, RECEIVE_DATA_PROCESSOR);
	private final ExternalDBProcessor EXTERNAL_DB_PROCESSOR = new ExternalDBProcessor(COMMUNICATOR);
	
	private DBProcessor dbProcessor;
	
	public MainControler() {
		AutoLoginInfoGetter autoLoginInfoGetter = new AutoLoginInfoGetter();
		if(autoLoginInfoGetter.isAutoLogin()) {
			try {
				Login l = autoLoginInfoGetter.getLoginInfo();
				onLogin(l.getID(), l.getPW());
			} catch(IOException e) {
				init();
			}
		}	else	init();
		
		init();
		WindowShower.INSTANCE.showMainWindow(MAIN_PANEL);
	}
	
	@Override
	public void onJoin(String id, String pw, String name) {
		try {
			COMMUNICATOR.readyToCommunication();
			COMMUNICATOR.sendData(Header.JOIN, new String[]{id, pw, name});
		} catch (ServerConnectionException e) {
			alertWhenCommunicationFailed("서버와 연결에 실패하였습니다.");
		}
	}

	@Override
	public void onLogin(String id, String pw) {
		try {
			DATA_STORE.setLoginedID(id);
			COMMUNICATOR.readyToCommunication();
			COMMUNICATOR.sendData(Header.LOGIN, new String[]{id, pw});
		} catch(ServerConnectionException e) {
			alertWhenCommunicationFailed("서버와 연결에 실패하였습니다.");
		}
	}
	
	@Override
	public void onLogout() {
		if(!DATA_STORE.isLogined())	return;
		DATA_STORE.setLogin(false);
		COMMUNICATOR.disconnect();
		
		dbProcessor = INTERNAL_DB_PROCESSOR;
		dbProcessor.loadDBData();
		
		MAIN_PANEL.showLoginStatus(false);
		
		DATA_STORE.setBeingShownYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		MAIN_PANEL.showFriends(DATA_STORE.getAllFriends());
		
		WindowShower.INSTANCE.hideSubWindow(SubViewType.LOGOUT);
	}

	@Override
	public void onYMDSelect(YMD selectedYMD) {
		selectedYMD.setYear(selectedYMD.getMonth()==0 ? 
								selectedYMD.getYear()-1 : 
									selectedYMD.getMonth()==13 ? 
											selectedYMD.getYear()+1 : selectedYMD.getYear());
		selectedYMD.setMonth(selectedYMD.getMonth()==0 ? 
								12 :
									selectedYMD.getMonth()==13 ?
											1 : selectedYMD.getMonth());
		int lastDate = CalendarUtil.getMaxDate(selectedYMD.getYear(), selectedYMD.getMonth());
		selectedYMD.setDate(selectedYMD.getDate()>lastDate ? lastDate : selectedYMD.getDate());
		if(DataStore.getBeingShownYMD().equals(selectedYMD)) {
			WindowShower.INSTANCE.hideSubWindow(SubViewType.YMD_SELECT);
			return;
		}
		DATA_STORE.setBeingShownYMD(selectedYMD);
		MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		WindowShower.INSTANCE.hideSubWindow(SubViewType.YMD_SELECT);
	}
	
	@Override
	public void onYMDSelect(int yearIncreased, int monthIncreased) {
		YMD ymd = new YMD(DataStore.getBeingShownYMD());
		ymd.setYear(ymd.getYear()+yearIncreased);
		ymd.setMonth(ymd.getMonth()+monthIncreased);
		onYMDSelect(ymd);
	}
	
	@Override
	public void onYMDSelect(int selectedDate) {
		onYMDSelect(new YMD(DataStore.getBeingShownYMD().getYear(), DataStore.getBeingShownYMD().getMonth(), selectedDate));
	}
	
	@Override
	public void onScheduleInsert(String title, String content, YMD ymd, Time startTime, Time endTime) {
		dbProcessor.insertSchedule(new Schedule(title, content, ymd, startTime, endTime));
	}
	
	@Override
	public void onScheduleUpdate(int idx, String title, String content, YMD ymd, Time startTime, Time endTime) {
		int scheduleID = DATA_STORE.getBeingShownSchedules().get(idx).getScheduleID();
		dbProcessor.updateSchedules(new Schedule(scheduleID, title, content, ymd, startTime, endTime));
	}
	
	@Override
	public void onScheduleDelete(int idx) {
		dbProcessor.deleteSchedules(DATA_STORE.getBeingShownSchedules().get(idx));
	}
	
	@Override
	public void onScheduleSelect(int idx) {
		((ScheduleUnsharePanel)SubViewType.UNSHARE.VIEW).setScheduleIdx(idx);
		DATA_STORE.setSharedFriends(DATA_STORE.getBeingShownSchedules().get(idx).getScheduleID());
		((ScheduleDetailView)SubViewType.SCHEDULE_DETAIL.VIEW).showScheduleDetail(DATA_STORE.getBeingShownSchedules().get(idx));
		((ScheduleDetailView)SubViewType.SCHEDULE_DETAIL.VIEW).showSharedFriends(DATA_STORE.getSharedFriends());
		WindowShower.INSTANCE.showSubWindow(SubViewType.SCHEDULE_DETAIL);
	}

	@Override
	public void onScheduleSearch(String keyword) {
		DATA_STORE.setSearchedSchedules(keyword);
		((ScheduleSearchPanel)SubViewType.SEARCH.VIEW).showSearchedSchedules(DATA_STORE.getSearchedSchedules());
	}

	@Override
	public void onSearchedScheduleSelect(int idx) {
		((ScheduleUnsharePanel)SubViewType.UNSHARE.VIEW).setScheduleIdx(idx);
		DATA_STORE.setSharedFriends(DATA_STORE.getSearchedSchedules().get(idx).getScheduleID());
		((ScheduleDetailView)SubViewType.SCHEDULE_DETAIL.VIEW).showScheduleDetail(DATA_STORE.getBeingShownSchedules().get(idx));
		((ScheduleDetailView)SubViewType.SCHEDULE_DETAIL.VIEW).showSharedFriends(DATA_STORE.getSharedFriends());
		WindowShower.INSTANCE.showSubWindow(SubViewType.SCHEDULE_DETAIL);
	}

	@Override
	public void onFriendInsert(int idx) {
		dbProcessor.insertFriend(DATA_STORE.getSearchedFriends().get(idx));
	}
	
	@Override
	public void onFriendDelete(int idx) {
		dbProcessor.deleteFriend(DATA_STORE.getAllFriends().get(idx));
	}
	
	@Override
	public void onFriendSearch(String search) {
		dbProcessor.searchFriend(search);
	}
	
	@Override
	public void onShare(int scheduleIdx, int[] friendIdx) {
		String[] guests = new String[friendIdx.length];
		int scheduleID = DATA_STORE.getBeingShownSchedules().get(scheduleIdx).getScheduleID();
		List<Friend> unshardFriends = DATA_STORE.getUnsharedFriends();
		for(int idx=0; idx<friendIdx.length; idx++)
			guests[idx] = unshardFriends.get(friendIdx[idx]).getID();
		dbProcessor.share(scheduleID, guests);
	}
	
	@Override
	public void onUnshare(int scheduleIdx, int friendIdx) {
		int scheduleID = DATA_STORE.getBeingShownSchedules().get(scheduleIdx).getScheduleID();
		DATA_STORE.setSharedFriends(scheduleID);
		dbProcessor.unshare(scheduleID, DATA_STORE.getSharedFriends().get(friendIdx).getID());
	}
	
	private void alertWhenCommunicationFailed(String alert) {
		((AlertView)SubViewType.ALERT.VIEW).setAlert(alert);
		WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
	}

	@Override
	public void onJoinSuccess() {
		WindowShower.INSTANCE.hideSubWindow(SubViewType.JOIN);
	}

	@Override
	public void onLoginSuccess(String[][] schedules, String[][] friends, String[][] sharing) {
		onDataLoadSuccess(schedules, friends, sharing);
		DATA_STORE.setLogin(true);
		dbProcessor = EXTERNAL_DB_PROCESSOR;
		MAIN_PANEL.showLoginStatus(true);
		WindowShower.INSTANCE.hideSubWindow(SubViewType.LOGIN);
	}

	@Override
	public void onDataLoadSuccess(String[][] schedules, String[][] friends, String[][] sharing) {
		DATA_STORE.setSchedules(schedules);
		DATA_STORE.setFriends(friends);
		DATA_STORE.setSharing(sharing);
		DATA_STORE.setBeingShownYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		MAIN_PANEL.showFriends(DATA_STORE.getAllFriends());
	}

	@Override
	public void onScheduleInsertSuccess(String[] insert) {
		Schedule add = new Schedule(Integer.parseInt(insert[0]), insert[1], insert[2], new YMD(insert[3]), new Time(insert[4]), new Time(insert[5]));
		DATA_STORE.addSchedule(add);
		DATA_STORE.setBeingShownYMD(add.getYMD());
		MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_INSERT);
	}

	@Override
	public void onScheduleUpdateSuccess(String[] update) {
		DATA_STORE.updateSchedule(new Schedule(Integer.parseInt(update[0]), update[1], update[2], new YMD(update[3]), new Time(update[4]), new Time(update[5])));
		DATA_STORE.setBeingShownYMD(new YMD(update[3]));
		MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_UPDATE);
	}

	@Override
	public void onScheduleDeleteSuccess(String scheduleID) {
		DATA_STORE.deleteSchedule(new Schedule(Integer.parseInt(scheduleID)));
		DATA_STORE.setBeingShownYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_DELETE);
	}

	@Override
	public void onFriendInsertSuccess(String reqID, String reqName, String resID, String resName) {
		if(reqID.equals(DataStore.getLoginedID()))	{
			((AlertView)SubViewType.ALERT.VIEW).setAlert(resID+"("+resName+") 님을 친구 추가하였습니다.");
			DATA_STORE.addFriend(new Friend(resID, resName));
			DATA_STORE.deleteSearchedFriend(new Friend(resID, resName));
			((FriendSearchPanel)SubViewType.FRIEND_INSERT.VIEW).showSearchedFriends(DATA_STORE.getSearchedFriends());
		} else {
			((AlertView)SubViewType.ALERT.VIEW).setAlert(reqID+"("+reqName+") 님이 친구 추가하였습니다.");
			DATA_STORE.addFriend(new Friend(reqID, reqName));
		}
		MAIN_PANEL.showFriends(DATA_STORE.getAllFriends());
		WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
	}

	@Override
	public void onFriendDeleteSuccess(String reqID, String resID) {
		if(reqID.equals(DataStore.getLoginedID())) {
			((AlertView)SubViewType.ALERT.VIEW).setAlert(resID+" 님을 친구 삭제하였습니다.");
			DATA_STORE.deleteFriend(new Friend(resID));
		} else {
			((AlertView)SubViewType.ALERT.VIEW).setAlert(reqID+" 님이 친구 삭제하였습니다.");
			DATA_STORE.deleteFriend(new Friend(reqID));
		}
		MAIN_PANEL.showFriends(DATA_STORE.getAllFriends());
		WindowShower.INSTANCE.hideSubWindow(SubViewType.FRIEND_DELETE);
		WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
	}

	@Override
	public void onFriendSearchSuccess(String[][] searched) {
		DATA_STORE.setSearchedFriends(searched);
		((FriendSearchPanel)SubViewType.FRIEND_INSERT.VIEW).showSearchedFriends(DATA_STORE.getSearchedFriends());
	}

	@Override
	public void onShareSuccess(String[] sharedSchedule, String[] guests) {
		if(DataStore.getLoginedID().equals(sharedSchedule[1])) {
			for(String guestID : guests)
				DATA_STORE.addSharing(new Sharing(Integer.parseInt(sharedSchedule[0]), sharedSchedule[1], guestID));
			((AlertView)SubViewType.ALERT.VIEW).setAlert("일정을 공유하였습니다.");
		} else {
			DATA_STORE.addSchedule(new Schedule(Integer.parseInt(sharedSchedule[0]), sharedSchedule[1], sharedSchedule[2], sharedSchedule[3], 
					new YMD(sharedSchedule[4]), new Time(sharedSchedule[5]), new Time(sharedSchedule[6])));
			DATA_STORE.addSharing(new Sharing(Integer.parseInt(sharedSchedule[0]), sharedSchedule[1], DataStore.getLoginedID()));
			((AlertView)SubViewType.ALERT.VIEW).setAlert(sharedSchedule[1]+"님이 "+sharedSchedule[4]+"일자 '"+sharedSchedule[2]+"' 일정을 공유하였습니다.");
			DATA_STORE.setBeingShownYMD(new YMD(sharedSchedule[4]));
			MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
			MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		}
		WindowShower.INSTANCE.hideSubWindow(SubViewType.SHARE);
		WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
	}

	@Override
	public void onUnshareSuccess(int unsharedScheduleID, String reqID, String guestID) {
		if(DataStore.getLoginedID().equals(reqID)) {
			if(DATA_STORE.deleteSharing(new Sharing(unsharedScheduleID, reqID)))	
				DATA_STORE.deleteSchedule(new Schedule(unsharedScheduleID));
			DATA_STORE.deleteSharing(new Sharing(unsharedScheduleID, guestID));
			((AlertView)SubViewType.ALERT.VIEW).setAlert("일정 공유를 해제하였습니다.");
		} else {
			if(DATA_STORE.deleteSharing(new Sharing(unsharedScheduleID, guestID)))
				DATA_STORE.deleteSchedule(new Schedule(unsharedScheduleID));
			DATA_STORE.deleteSharing(new Sharing(unsharedScheduleID, reqID));
			((AlertView)SubViewType.ALERT.VIEW).setAlert(reqID+"님이 일정 공유를 해제하였습니다.");
		}
		
		DATA_STORE.setSharedFriends(unsharedScheduleID);
		((ScheduleDetailView)SubViewType.SCHEDULE_DETAIL.VIEW).showSharedFriends(DATA_STORE.getSharedFriends());
		DATA_STORE.setBeingShownYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		WindowShower.INSTANCE.hideSubWindow(SubViewType.UNSHARE);
		WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
	}
	
	@Override
	public void onUnsharedFriendSearch(int scheduleIdx) {
		((ScheduleSharePanel)SubViewType.SHARE.VIEW).setScheduleIdx(scheduleIdx);
		DATA_STORE.setUnsharedFriends(DATA_STORE.getBeingShownSchedules().get(scheduleIdx).getScheduleID());
		((ScheduleSharePanel)SubViewType.SHARE.VIEW).showFriends(DATA_STORE.getUnsharedFriends());
		WindowShower.INSTANCE.showSubWindow(SubViewType.SHARE);
	}

	private void init() {
		MAIN_PANEL.setViewEventListener(this);
		bindPopUpView();
		
		dbProcessor = DATA_STORE.isLogined() ? EXTERNAL_DB_PROCESSOR : INTERNAL_DB_PROCESSOR;
		dbProcessor.loadDBData();
		DATA_STORE.setBeingShownYMD(DataStore.getBeingShownYMD());
		
		MAIN_PANEL.showLoginStatus(DATA_STORE.isLogined());
		MAIN_PANEL.showYMD(DataStore.getBeingShownYMD());
		MAIN_PANEL.showSchedules(DATA_STORE.getBeingShownSchedules());
		MAIN_PANEL.showFriends(DATA_STORE.getAllFriends());
	}
	
	private void bindPopUpView() {
		((JoinPanel)SubViewType.JOIN.VIEW).setJoinListener(this);
		((LoginPanel)SubViewType.LOGIN.VIEW).setLoginListener(this);
		((AlertView)SubViewType.LOGOUT.VIEW).setAlertCheckListener(()->this.onLogout());
		((YMDSelectPanel)SubViewType.YMD_SELECT.VIEW).setYMDSelectListener(this);
		((ScheduleSearchPanel)SubViewType.SEARCH.VIEW).setScheduleSearchListener(this);
		((ScheduleSearchPanel)SubViewType.SEARCH.VIEW).setSearchedScheduleSelectListener(this);
		((ScheduleInsertPanel)SubViewType.SCHEDULE_INSERT.VIEW).setScheduleInsertListener(this);
		((ScheduleUpdatePanel)SubViewType.SCHEDULE_UPDATE.VIEW).setScheduleUpdateListener(this);
		((ScheduleDeletePanel)SubViewType.SCHEDULE_DELETE.VIEW).setScheduleDeleteListener(this);
		((FriendSearchPanel)SubViewType.FRIEND_INSERT.VIEW).setFriendInsertListener(this);
		((FriendSearchPanel)SubViewType.FRIEND_INSERT.VIEW).setFriendSearchListener(this);
		((FriendDeletePanel)SubViewType.FRIEND_DELETE.VIEW).setFriendDeleteListener(this);
		((ScheduleSharePanel)SubViewType.SHARE.VIEW).setShareListener(this);
		((ScheduleUnsharePanel)SubViewType.UNSHARE.VIEW).setUnshareListener(this);
	}
}
