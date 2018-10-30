package controler;

import java.io.IOException;

import calendar.CalendarUtil;
import data.Login;
import data.YMD;
import enumeration.Header;
import exception.ServerConnectionException;
import listener.join.JoinListener;
import listener.login.LoginListener;
import listener.ymd.YMDSelectListener;
import processor.AutoLoginInfoGetter;
import processor.DBProcessor;
import processor.InternalDBProcessor;
import processor.LoginProcessor;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.MainPanel;
import view.customized.CommonButtonsView.ButtonsType;
import view.popup.AlertView;

public class MainControler implements YMDSelectListener, LoginListener, JoinListener {
	public static YMD beingShownYMD = CalendarUtil.getToday();
	
	private final AlertView ALERT = new AlertView(ButtonsType.CHECK_ONLY);
	private final MainPanel MAIN_PANEL = new MainPanel();
	
	private final ServerCommunicator COMMUNICATOR = new ServerCommunicator(this);
	private final LoginProcessor LOGIN_PROCESSOR = new LoginProcessor(COMMUNICATOR);
	private final ReceiveDataControler RECEIVE_DATA_PROCESSOR = new ReceiveDataControler(COMMUNICATOR, MAIN_PANEL, this);
	private final InternalDBProcessor INTERNAL_DB_PROCESSOR = new InternalDBProcessor(MAIN_PANEL);
	
	private DBProcessor dbProcessor;
	
	public MainControler() {
		COMMUNICATOR.setReceiveDataControler(RECEIVE_DATA_PROCESSOR);
		
		AutoLoginInfoGetter autoLoginInfoGetter = new AutoLoginInfoGetter();
		if(autoLoginInfoGetter.isAutoLogin()) {
			try {
				Login l = autoLoginInfoGetter.getLoginInfo();
				onLogin(l.getID(), l.getPW());
			} catch(IOException e) {
				alertWhenCommunicationFailed("자동 로그인 정보를 불러오지 못하였습니다.");
			}
		}	else	onLogout();
		
		MAIN_PANEL.setYMDSelectListener(this);
		MAIN_PANEL.setLoginListener(this);
		MAIN_PANEL.setJoinListener(this);
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
			COMMUNICATOR.readyToCommunication();
			LOGIN_PROCESSOR.login(new Login(id, pw));
		} catch(ServerConnectionException e) {
			alertWhenCommunicationFailed("서버와 연결에 실패하였습니다.");
		}
	}
	
	@Override
	public void onLogout() {
		LOGIN_PROCESSOR.logout();
		dbProcessor = INTERNAL_DB_PROCESSOR;
		MAIN_PANEL.setSelectEventListener(dbProcessor);
		MAIN_PANEL.setUpdateEventListener(dbProcessor);
		MAIN_PANEL.showLoginStatus(false);
		MAIN_PANEL.showYMD(beingShownYMD);
		dbProcessor.onSchedulesSelectByYMD(beingShownYMD);
	}

	@Override
	public void onLoginSuccess() {
		LOGIN_PROCESSOR.loginSuccess();
		dbProcessor = RECEIVE_DATA_PROCESSOR.getDBProcessor();
		MAIN_PANEL.setSelectEventListener(dbProcessor);
		MAIN_PANEL.setUpdateEventListener(dbProcessor);
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
		if(beingShownYMD.equals(selectedYMD)) {
			WindowShower.INSTANCE.hideSubWindow(SubViewType.YMD_INPUT);
			return;
		}
		beingShownYMD.setYMD(selectedYMD);
		MAIN_PANEL.showYMD(beingShownYMD);
		WindowShower.INSTANCE.hideSubWindow(SubViewType.YMD_INPUT);
		dbProcessor.onSchedulesSelectByYMD(beingShownYMD);
	}
	
	@Override
	public void onYMDSelect(int yearIncreased, int monthIncreased) {
		YMD ymd = new YMD(beingShownYMD);
		ymd.setYear(ymd.getYear()+yearIncreased);
		ymd.setMonth(ymd.getMonth()+monthIncreased);
		onYMDSelect(ymd);
	}
	
	@Override
	public void onYMDSelect(int selectedDate) {
		onYMDSelect(new YMD(beingShownYMD.getYear(), beingShownYMD.getMonth(), selectedDate));
	}
	
	private void alertWhenCommunicationFailed(String alert) {
		ALERT.setAlert(alert);
		WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
		if(dbProcessor==null)	dbProcessor = INTERNAL_DB_PROCESSOR;
	}
}
