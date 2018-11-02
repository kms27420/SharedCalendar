package processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.Friend;
import data.Schedule;
import data.Time;
import data.YMD;
import listener.DBSuccessListener;

public class InternalDBProcessor implements DBProcessor {
	private static final String SCHEDULES_PATH = "bin/internal/Schedules.txt";
	private final List<Schedule> SCHEDULES = new ArrayList<>();
	private final File DB_FILE = new File(SCHEDULES_PATH);
	private final DBSuccessListener DB_SUCCESS_LISTENER;
	
	public InternalDBProcessor(DBSuccessListener dbSuccessListener) {
		DB_SUCCESS_LISTENER = dbSuccessListener;
	}
	@Override
	public void loadDBData() {
		Scanner sc = null;
		List<String[]> tmp = new ArrayList<>();
		try {
			sc = new Scanner(DB_FILE);
			while(sc.hasNextLine()) {
				tmp.add(new String[]{sc.nextLine(), "", sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine()});
			}
			DB_SUCCESS_LISTENER.onDataLoadSuccess(tmp.toArray(new String[tmp.size()][]), new String[][]{}, new String[][]{});
		} catch(IOException e) {e.printStackTrace();}
		finally {
			sc.close();
			sc = null;
			tmp = null;
		}
	}
	@Override
	public void insertSchedule(Schedule insert) {
		int scheduleID = SCHEDULES.size()==0 ? 1 : SCHEDULES.get(SCHEDULES.size()-1).getScheduleID()+1;
		Schedule tmp = new Schedule(insert);
		tmp.setScheduleID(scheduleID);
		FileWriter appender = null;
		try {
			appender = new FileWriter(DB_FILE, true);
			appender.write(tmp.toString());
			appender.flush();
			appender.close();
			DB_SUCCESS_LISTENER.onScheduleInsertSuccess(new String[]{
					tmp.getScheduleID()+"", tmp.getTitle(), tmp.getContent(), 
					tmp.getYMD().toString(), tmp.getStartTime().toString(), tmp.getEndTime().toString()
					});
		} catch(Exception e) {} 
		finally {
			appender = null;
			tmp = null;
		}
	}
	@Override
	public void updateSchedules(Schedule update) {
		Scanner sc = null;
		FileWriter updater = null;
		String input = "";
		List<Schedule> tmp = new ArrayList<>();
		try {
			sc = new Scanner(DB_FILE);
			while(sc.hasNextLine()) {
				tmp.add(new Schedule(Integer.parseInt(sc.nextLine()), sc.nextLine(), sc.nextLine(), 
						new YMD(sc.nextLine()), new Time(sc.nextLine()), new Time(sc.nextLine())));
			}
			for(Schedule s : tmp)
				if(s.equals(update))	input += update.toString();
				else	input += s.toString();
			
			updater = new FileWriter(DB_FILE, false);
			updater.write(input);
			updater.flush();
			updater.close();
			DB_SUCCESS_LISTENER.onScheduleUpdateSuccess(new String[]{
					update.getScheduleID()+"", update.getTitle(), update.getContent(), 
					update.getYMD().toString(), update.getStartTime().toString(), update.getEndTime().toString()
					});
		} catch(IOException e) {e.printStackTrace();}
		finally {
			sc.close();
			sc = null;
			updater = null;
			tmp = null;
			input = null;
		}
	}
	@Override
	public void deleteSchedules(Schedule delete) {
		Scanner sc = null;
		FileWriter updater = null;
		String input = "";
		List<Schedule> tmp = new ArrayList<>();
		try {
			sc = new Scanner(DB_FILE);
			while(sc.hasNextLine()) {
				tmp.add(new Schedule(Integer.parseInt(sc.nextLine()), sc.nextLine(), sc.nextLine(), 
						new YMD(sc.nextLine()), new Time(sc.nextLine()), new Time(sc.nextLine())));
			}
			for(Schedule s : tmp)
				if(!s.equals(delete))	input+=s.toString();
			
			updater = new FileWriter(DB_FILE, false);
			updater.write(input);
			updater.flush();
			updater.close();
			DB_SUCCESS_LISTENER.onScheduleDeleteSuccess(delete.getScheduleID()+"");
		} catch(IOException e) {}
		finally {
			sc.close();
			sc = null;
			updater = null;
			input = null;
			tmp = null;
		}
	}
	@Override
	public void insertFriend(Friend friend) {}
	@Override
	public void deleteFriend(Friend friend) {}
	@Override
	public void searchFriend(String keyword) {}
	@Override
	public void share(int scheduleID, String[] guests) {}
	@Override
	public void unshare(int scheduleID, String guestID) {}
}
