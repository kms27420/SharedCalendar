package view;

import javax.swing.JPanel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import listener.YMDSelectListener;
import view.calendar_panel.CalendarPanel;

public class MainPanel extends JPanel implements YMDSelectListener {
	private int year, month, date;
	 
	public MainPanel() {
		setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		add(new CalendarPanel(), new LinearConstraints(3, LinearSpace.MATCH_PARENT));
	}

	@Override
	public void onYMDSelect(int changedYear, int changedMonth, int changedDate) {
		 this.date = changedDate;
	}
}
