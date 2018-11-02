package listener.view;

import listener.view.friend.FriendChangeListener;
import listener.view.join.JoinListener;
import listener.view.login.LoginChangeListener;
import listener.view.schedule.ScheduleChangeListener;
import listener.view.sharing.SharingChangeListener;
import listener.view.ymd.YMDSelectListener;

public interface ViewEventListener extends JoinListener, LoginChangeListener, 
ScheduleChangeListener, FriendChangeListener, SharingChangeListener, YMDSelectListener {

}
