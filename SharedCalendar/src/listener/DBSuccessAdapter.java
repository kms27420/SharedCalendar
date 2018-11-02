package listener;

public class DBSuccessAdapter implements DBSuccessListener {
	@Override
	public void onJoinSuccess() {}
	@Override
	public void onLoginSuccess(String[][] schedules, String[][] friends, String[][] sharing) {}
	@Override
	public void onScheduleInsertSuccess(String[] insert) {}
	@Override
	public void onFriendDeleteSuccess(String reqID, String resID) {}
	@Override
	public void onFriendSearchSuccess(String[][] searched) {}
	@Override
	public void onDataLoadSuccess(String[][] schedules, String[][] friends, String[][] sharing) {}
	@Override
	public void onScheduleUpdateSuccess(String[] update) {}
	@Override
	public void onScheduleDeleteSuccess(String scheduleID) {}
	@Override
	public void onFriendInsertSuccess(String reqID, String reqName, String resID, String resName) {}
	@Override
	public void onShareSuccess(String[] sharedSchedule, String[] guests) {}
	@Override
	public void onUnshareSuccess(int unsharedScheduleID, String reqID, String guestID) {}
}
