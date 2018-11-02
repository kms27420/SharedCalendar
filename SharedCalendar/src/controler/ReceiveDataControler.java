package controler;

import enumeration.Header;
import listener.DBSuccessListener;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.popup.common.AlertView;

public class ReceiveDataControler {
	private final DBSuccessListener SUCCESS_LISTENER;
	
	public ReceiveDataControler(DBSuccessListener successListener) {
		SUCCESS_LISTENER = successListener;
	}
	
	public void onReceiveData(Object[] receive) {
		Header header = Header.getHeader((String)receive[0]);
		
		switch(header) {
		case JOIN :
			SUCCESS_LISTENER.onJoinSuccess();
			break;
		case JOIN_ID_ALREADY_EXIST :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("아이디가 이미 존재합니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case FAIL_TO_JOIN :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("회원가입에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case LOGIN :
			String[][] schedules = null;
			String[][] friends = null;
			String[][] sharing = null;
			if(((Object[])receive[1])[0].equals(Header.SUCCESS_TO_SELECT_SCHEDULE.name()))
				schedules = (String[][])((Object[])receive[1])[1];
			else schedules = new String[][]{};
			
			if(((Object[])receive[2])[0].equals(Header.SUCCESS_TO_SELECT_FRIEND.name()))
				friends = (String[][])((Object[])receive[2])[1];
			else friends = new String[][]{};
			
			if(((Object[])receive[3])[0].equals(Header.SUCCESS_TO_SELECT_SHARING.name()))
				sharing = (String[][])((Object[])receive[3])[1];
			else sharing = new String[][]{};
			SUCCESS_LISTENER.onLoginSuccess(schedules, friends, sharing);
			break;
		case LOGIN_NO_ID :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("아이디가 존재하지 않습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case LOGIN_WRONG_PW :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("패스워드가 올바르지 않습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case FAIL_TO_LOGIN :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("로그인에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case SEARCH_FRIEND :
			SUCCESS_LISTENER.onFriendSearchSuccess((String[][])receive[1]);
			break;
		case FAIL_TO_SEARCH_FRIEND :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("친구 검색에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case INSERT_SCHEDULE :
			SUCCESS_LISTENER.onScheduleInsertSuccess((String[])receive[1]);
			break;
		case FAIL_TO_INSERT_SCHEDULE :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("일정 추가에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case UPDATE_SCHEDULE :
			SUCCESS_LISTENER.onScheduleUpdateSuccess((String[])receive[1]);
			break;
		case FAIL_TO_UPDATE_SCHEDULE :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("일정 수정에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case DELETE_SCHEDULE :
			SUCCESS_LISTENER.onScheduleDeleteSuccess((String)receive[1]);
			break;
		case FAIL_TO_DELETE_SCHEDULE :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("일정 삭제에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case INSERT_FRIEND :
			SUCCESS_LISTENER.onFriendInsertSuccess((String)receive[1], (String)receive[3], (String)receive[2], (String)receive[4]);
			break;
		case FAIL_TO_INSERT_FRIEND :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("친구 추가에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case DELETE_FRIEND :
			SUCCESS_LISTENER.onFriendDeleteSuccess((String)receive[1], (String)receive[2]);
			break;
		case FAIL_TO_DELETE_FRIEND :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("친구 삭제에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case SHARE :
			SUCCESS_LISTENER.onShareSuccess((String[])receive[1], (String[])receive[2]);
			break;
		case FAIL_TO_SHARE :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("일정 공유에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
		case UNSHARE :
			SUCCESS_LISTENER.onUnshareSuccess(Integer.parseInt((String)receive[1]), (String)receive[2], (String)receive[3]);
			break;
		case FAIL_TO_UNSHARE :
			((AlertView)SubViewType.ALERT.VIEW).setAlert("일정 공유 삭제에 실패하였습니다. 다시 시도해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
			break;
			default : break;
		}
	}
}
