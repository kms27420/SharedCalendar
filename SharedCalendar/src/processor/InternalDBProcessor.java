package processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controler.MainControler;
import data.Schedule;
import data.SharedSchedule;
import data.Time;
import data.YMD;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.MainPanel;

public class InternalDBProcessor implements DBProcessor {
	private static final String SCHEDULES_PATH = "bin/internal/Schedules.txt";
	private final List<Schedule> SCHEDULES = new ArrayList<>();
	private final File DB_FILE = new File(SCHEDULES_PATH);
	private FileWriter appender, updater;
	private final MainPanel MAIN_PANEL;
	
	private boolean isYMDChanged;
	
	public InternalDBProcessor(MainPanel mainPanel) {
		MAIN_PANEL = mainPanel;
		Scanner sc = null;
		try {
			sc = new Scanner(DB_FILE);
			while(sc.hasNextLine()) {
				SCHEDULES.add(new Schedule(Integer.parseInt(sc.nextLine()), 
						sc.nextLine(), sc.nextLine(), 
						new YMD(sc.nextLine()), new Time(sc.nextLine()), new Time(sc.nextLine())));
			}
		} catch(IOException e) {
			appender = null;
			updater = null;
		}
		finally {
			sc = null;
		}
	}
	
	@Override
	public void onScheduleInsert(Schedule insert) {
		int scheduleID = SCHEDULES.size()==0 ? 1 : SCHEDULES.get(SCHEDULES.size()-1).getScheduleID()+1;
		Schedule tmp = new Schedule(insert);
		tmp.setScheduleID(scheduleID);
		try {
			if(appender==null)	appender = new FileWriter(DB_FILE, true);
			appender.write(tmp.toString());
			appender.flush();
			SCHEDULES.add(tmp);
			if(!insert.getYMD().equals(MainControler.beingShownYMD))	isYMDChanged = true;
			onSchedulesSelectByYMD(insert.getYMD());
			WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_INSERT);
		} catch(Exception e) {
		} finally {
			tmp = null;
		}
	}

	@Override
	public void onScheduleUpdate(Schedule update) {
		String str = "";
		for(Schedule s : SCHEDULES)
			if(s.equals(update))	str += update.toString();
			else	str += s.toString();
		try {
			if(updater!=null)	updater.close();
			updater = new FileWriter(DB_FILE, false);
			updater.write(str);
			updater.flush();
			int idx = SCHEDULES.indexOf(update);
			SCHEDULES.remove(update);
			SCHEDULES.add(idx, update);
			if(!update.getYMD().equals(MainControler.beingShownYMD))	isYMDChanged = true;
			onSchedulesSelectByYMD(update.getYMD());
			WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_UPDATE);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			str = null;
		}
	}

	@Override
	public void onScheduleDelete(Schedule delete) {
		String str = "";
		for(Schedule s : SCHEDULES)
			if(!s.equals(delete))	str += s.toString();
		try {
			if(updater!=null)	updater.close();
			updater = new FileWriter(DB_FILE, false);
			updater.write(str);
			updater.flush();
			SCHEDULES.remove(delete);
			onSchedulesSelectByYMD(delete.getYMD());
			WindowShower.INSTANCE.hideSubWindow(SubViewType.SCHEDULE_DELETE);
		} catch(Exception e) {
		} finally {
			str = null;
		}
	}

	@Override
	public void onSchedulesSelectByYMD(YMD search) {
		List<Schedule> rv = new ArrayList<>();
		for(Schedule s : SCHEDULES)
			if(s.getYMD().equals(search))	rv.add(s);
		if(isYMDChanged) {
			MainControler.beingShownYMD = search;
			MAIN_PANEL.showYMD(search);
			isYMDChanged = false;
		}
		MAIN_PANEL.showSchedules(rv);
	}

	@Override
	public void onSchedulesSelectBySearch(String search) {
		List<Schedule> rv = new ArrayList<>();
		char[] compare = search.toCharArray();
		for(Schedule s : SCHEDULES)
			if(isInclude(s.getTitle().toCharArray(), compare) || isInclude(s.getContent().toCharArray(), compare))	
				rv.add(s);
	}
	
	private boolean isInclude(char[] comp, char[] search) {
		int searchIdx = 0;
		for(int i=0; i<comp.length; i++) {
			if(comp[i]==search[searchIdx]) {
				if(searchIdx==search.length-1)	return true;
				searchIdx++;
			} else	searchIdx = 0;
		}
		return false;
	}
	
	@Override
	public void onShare(SharedSchedule s) {}
	@Override
	public void onUnshare(SharedSchedule s) {}
	@Override
	public void onFriendInsert(String id) {}
	@Override
	public void onFriendDelete(String id) {}
	@Override
	public void onSelectFriends() {}
	@Override
	public void onSelectFriend(String id) {}
}
