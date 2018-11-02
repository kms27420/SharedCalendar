package listener;

public interface DBSuccessListener {
	public void onJoinSuccess();
	public void onLoginSuccess(String[][] schedules, String[][] friends, String[][] sharing);
	public void onDataLoadSuccess(String[][] schedules, String[][] friends, String[][] sharing);
	public void onScheduleInsertSuccess(String[] insert);
	public void onScheduleUpdateSuccess(String[] update);
	public void onScheduleDeleteSuccess(String scheduleID);
	public void onFriendInsertSuccess(String reqID, String reqName, String resID, String resName);
	public void onFriendDeleteSuccess(String reqID, String resID);
	public void onFriendSearchSuccess(String[][] searched);
	public void onShareSuccess(String[] sharedSchedule, String[] guests);
	public void onUnshareSuccess(int unsharedScheduleID, String reqID, String guestID);
}
