package listener.ymd;

import data.YMD;

public interface YMDSelectListener {
	public void onYMDSelect(YMD selectedYMD);
	public void onYMDSelect(int yearIncreased, int monthIncreased);
	public void onYMDSelect(int selectedDate);
}
