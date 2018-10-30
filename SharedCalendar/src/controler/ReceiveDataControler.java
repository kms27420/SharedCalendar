package controler;

import java.util.ArrayList;
import java.util.List;

import data.Friend;
import data.Schedule;
import data.SharedSchedule;
import data.Time;
import data.YMD;
import enumeration.Header;
import listener.login.LoginListener;
import listener.ymd.YMDSelectAdapter;
import listener.ymd.YMDSelectListener;
import processor.DBProcessor;
import processor.ExternalDBProcessor;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.MainPanel;
import view.customized.CommonButtonsView.ButtonsType;
import view.popup.AlertView;

public class ReceiveDataControler {
	private final AlertView ALERT = new AlertView(ButtonsType.CHECK_ONLY);
	
	private YMDSelectListener ymdSelectListener = new YMDSelectAdapter();
	private final ExternalDBProcessor DB_PROCESSOR;
	private final MainPanel MAIN_PANEL;
	private final LoginListener LOGIN_LISTENER;
	
	private YMD updatedYMD;
	
	public ReceiveDataControler(ServerCommunicator communicator, MainPanel mainPanel, LoginListener loginListener) {
		DB_PROCESSOR = new ExternalDBProcessor(communicator);
		MAIN_PANEL = mainPanel;
		LOGIN_LISTENER = loginListener;
	}
	
	public DBProcessor getDBProcessor() {
		return DB_PROCESSOR;
	}
	
	public void onReceiveData(Object[] receive) {
		Header header = Header.getHeader((String)receive[0]);
		String[][] ss = null;
		String[][] ff = null;
		List<Schedule> s = null;
		List<Friend> f = null;
		switch(header) {
		case ALARM_DELETE_FRIEND :
			DB_PROCESSOR.onSelectFriends();
			ALERT.setAlert("친구 삭제되었습니다. 발신인 : " + receive[1] + ", 수신인 : " + receive[2]);
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case ALARM_INSERT_FRIEND :
			DB_PROCESSOR.onSelectFriends();
			ALERT.setAlert("친구 추가되었습니다. 발신인 : " + receive[1] + ", 수신인 : " + receive[2]);
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case ALARM_SHARE :
			ALERT.setAlert("일정이 공유되었습니다. 발신인 : " + receive[1] + ", 수신인 : " + receive[2]);
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case ALARM_UNSHARE :
			ALERT.setAlert("일정 공유가 해제되었습니다. 발신인 : " + receive[1] + ", 수신인 : " + receive[2]);
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case DELETE_SCHEDULE :
			DB_PROCESSOR.onSchedulesSelectByYMD(MainControler.beingShownYMD);
			break;
		case FAIL_TO_DELETE_FRIEND :
			ALERT.setAlert("친구 삭제에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_DELETE_SCHEDULE :
			ALERT.setAlert("일정 삭제에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_INSERT_FRIEND :
			ALERT.setAlert("친구 추가에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_INSERT_SCHEDULE :
			ALERT.setAlert("일정 추가에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_JOIN :
			ALERT.setAlert("회원가입에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_LOGIN :
			ALERT.setAlert("로그인에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_SELECT_FRIEND :
			ALERT.setAlert("친구 조회에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_SELECT_FRIENDS :
			ALERT.setAlert("친구 불러오기에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_SELECT_SCHEDULE :
			ALERT.setAlert("일정 불러오기에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_SHARE :
			ALERT.setAlert("일정 공유에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_UNSHARE :
			ALERT.setAlert("일정 공유 해제에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case FAIL_TO_UPDATE_SCHEDULE :
			ALERT.setAlert("일정 갱신에 실패하였습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case INSERT_FRIEND :
			WindowShower.INSTANCE.hideSubWindow(SubViewType.FRIEND_INSERT);
			DB_PROCESSOR.onSelectFriends();
			break;
		case INSERT_SCHEDULE :
			WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_INSERT);
			updatedYMD = new YMD((String)receive[1]);
			if(updatedYMD.equals(MainControler.beingShownYMD)) {
				updatedYMD=null;
				DB_PROCESSOR.onSchedulesSelectByYMD(MainControler.beingShownYMD);
			} else	DB_PROCESSOR.onSchedulesSelectByYMD(updatedYMD);
			break;
		case JOIN_ID_ALREADY_EXIST :
			ALERT.setAlert("아이디가 이미 존재합니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case JOIN_SUCCESS :
			ALERT.setAlert("회원가입이 완료되었습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			WindowShower.INSTANCE.hideSubWindow(SubViewType.JOIN);
			break;
		case LOGIN_NO_ID :
			ALERT.setAlert("아이디가 없습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case LOGIN_SUCCESS :
			WindowShower.INSTANCE.hideSubWindow(SubViewType.LOGIN);
			LOGIN_LISTENER.onLoginSuccess();
			MAIN_PANEL.showLoginStatus(true);
			break;
		case LOGIN_WRONG_PW :
			ALERT.setAlert("패스워드가 틀렸습니다.");
			WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case SELECT_FRIEND ://TODO
			break;
		case SELECT_FRIENDS :
			ff = (String[][])receive[1];
			f = new ArrayList<>();
			for(int i=0; i<ff.length; i++)	f.add(new Friend(ff[i][0], ff[i][1]));
			MAIN_PANEL.showFriends(f);
			break;
		case SELECT_SCHEDULE_BY_KEYWORD :
			ss = (String[][])receive[1];
			ff = (String[][])receive[2];
			s = new ArrayList<>();
			f = null;
			for(int i=0; i<ss.length; i++) {
				f = new ArrayList<>();
				for(int j=0; j<ff[i].length; j+=2)	f.add(new Friend(ff[i][j], ff[i][j+1]));
				s.add(new SharedSchedule(Integer.parseInt(ss[i][0]), ss[i][1], ss[i][2], new YMD(ss[i][3]), new Time(ss[i][4]), new Time(ss[i][5]),f));
			}
			//TODO 검색 스케쥴 리스트 갱신
			break;
		case SELECT_SCHEDULE_BY_YMD :
			ss = (String[][])receive[1];
			ff = (String[][])receive[2];
			s = new ArrayList<>();
			f = null;
			for(int i=0; i<ss.length; i++) {
				f = new ArrayList<>();
				for(int j=0; j<ff[i].length; j+=2)	f.add(new Friend(ff[i][j], ff[i][j+1]));
				s.add(new SharedSchedule(Integer.parseInt(ss[i][0]), ss[i][1], ss[i][2], new YMD(ss[i][3]), new Time(ss[i][4]), new Time(ss[i][5]),f));
			}
			if(updatedYMD!=null) {
				MainControler.beingShownYMD = updatedYMD;
				MAIN_PANEL.showYMD(MainControler.beingShownYMD);
				updatedYMD=null;
			}
			MAIN_PANEL.showSchedules(s);
			break;
		case UPDATE_SCHEDULE :
			WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_UPDATE);
			updatedYMD = new YMD((String)receive[1]);
			if(updatedYMD.equals(MainControler.beingShownYMD)) {
				updatedYMD=null;
				DB_PROCESSOR.onSchedulesSelectByYMD(MainControler.beingShownYMD);
			} else	DB_PROCESSOR.onSchedulesSelectByYMD(updatedYMD);
			break;
			default : break;
		}
	}
}
