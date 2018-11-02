package listener.view.ymd;

import data.YMD;

public class YMDSelectAdapter implements YMDSelectListener {
	@Override
	public void onYMDSelect(YMD selectedYMD) {}
	@Override
	public void onYMDSelect(int yearIncreased, int monthIncreased) {}
	@Override
	public void onYMDSelect(int selectedDate) {}
}
