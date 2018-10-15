package view.calendar_panel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import calendar.MonthInEnglish;
import listener.YMDSelectListener;
import model.YMD;
import util.FontUtil;
import view.customized.TransparentPanel;
import view.customized.text_button.TextButton;

public class YearMonthPanel extends TransparentPanel implements ActionListener, YMDPanel {
	private YMDSelectListener ymdSelectListener = (y,m,d)->{};
	 
	YearMonthPanel() {
		setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		add(createSideBtView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createCentralBtView(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		add(createSideBtView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		
		getCentralBt().addActionListener(this);
		getPreviousBt().addActionListener(this);
		getNextBt().addActionListener(this);
	}
	
	void setYMDSelectListener(YMDSelectListener ymdSelectListener) {
		this.ymdSelectListener = ymdSelectListener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof CentralBt) {
			// Show sub frame that provides user interface setting year and month function.
		} else if(e.getSource() instanceof SideBt)
			ymdSelectListener.onYMDSelect(Integer.parseInt(((SideBt)e.getSource()).getText()), YMDSelectListener.NO_SELECTED, YMDSelectListener.NO_SELECTED);
		else return;
	}
	
	@Override
	public void showYMDView(YMD ymd) {
		getCentralBt().setText(MonthInEnglish.values()[ymd.getMonth()-1].name().toUpperCase()+", "+ymd.getYear());
		getPreviousBt().setText((ymd.getYear()-1)+"");
		getNextBt().setText((ymd.getYear()+1)+"");
	}
	
	// 이 위로는 외부 노출 API
	
	private CentralBt getCentralBt() {
		return (CentralBt)((Container)((Container)getComponent(1)).getComponent(1)).getComponent(1);
	}
	
	private SideBt getPreviousBt() {
		return (SideBt)((Container)getComponent(0)).getComponent(4);
	}
	
	private SideBt getNextBt() {
		return (SideBt)((Container)getComponent(2)).getComponent(4);
	}
	
	// 이 아래로부터는 뷰 구성
	
	private JPanel createCentralBtView() {
		JPanel contentIncludingView = new TransparentPanel();
		contentIncludingView.setLayout(new GridLayout(3, 1));
		contentIncludingView.add(new TransparentPanel());
		contentIncludingView.add(new CentralBt());
		contentIncludingView.add(new TransparentPanel());
		
		JPanel centralBtView = new TransparentPanel();
		centralBtView.setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		centralBtView.add(new TransparentPanel(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		centralBtView.add(contentIncludingView, new LinearConstraints(3, LinearSpace.MATCH_PARENT));
		centralBtView.add(new TransparentPanel(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		return centralBtView;
	}
	
	private JPanel createSideBtView() {
		JPanel sideBtView = new TransparentPanel();
		sideBtView.setLayout(new GridLayout(3,3));
		for(int i=0; i<9; i++) {
			if(i==4)	sideBtView.add(new SideBt());
			else	sideBtView.add(new TransparentPanel());
		}	
		return sideBtView;
	}
	
	private class CentralBt extends TextButton {
		private final Color FG = Color.GRAY;
		private final Font FONT = FontUtil.createDefaultFont(25);
		
		private CentralBt() {
			setForeground(FG);
			setOriginalFg(FG);
			setEnableOppositeColorEffect(false);
			setEffectedFg(new Color(200,200,200));
			setFont(FONT);
		}
	}
	
	private class SideBt extends TextButton {
		private final Color FG = new Color(255, 121,98);
		private final Font FONT = FontUtil.createDefaultFont(15);
		
		private SideBt() {
			setForeground(FG);
			setOriginalFg(FG);
			setFont(FONT);
		}
	}
}
