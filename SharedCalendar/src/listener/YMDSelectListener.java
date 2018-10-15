package listener;

public interface YMDSelectListener {
	public static final int NO_SELECTED = -1;
	
	public void onYMDSelect(int selectedYear, int selectedMonth, int selectedDate); 
}
