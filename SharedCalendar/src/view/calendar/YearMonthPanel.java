package view.calendar;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import calendar.MonthInEnglish;
import data.YMD;
import listener.view.ymd.YMDSelectAdapter;
import listener.view.ymd.YMDSelectListener;
import util.ColorUtil;
import util.FontUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import view.abv.YMDView;
import view.calendar.MonthMoveButton.Direction;
import view.customized.TransparentPanel;
import view.customized.text_button.TextButton;

public class YearMonthPanel extends TransparentPanel implements ActionListener, YMDView {
	private YMDSelectListener ymdSelectListener = new YMDSelectAdapter();
	
	YearMonthPanel() {
		setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		add(createSideBtView(Direction.LEFT), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createCentralBtView(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		add(createSideBtView(Direction.RIGHT), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		
		getCentralBt().addActionListener(this);
		getPreviousBt().addActionListener(this);
		getNextBt().addActionListener(this);
	}
	
	public void setYMDSelectListener(YMDSelectListener l) {
		this.ymdSelectListener = l;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof CentralButton)
			WindowShower.INSTANCE.showSubWindow(SubViewType.YMD_SELECT);
		else if(e.getSource() instanceof YearMoveButton)
			ymdSelectListener.onYMDSelect(((YearMoveButton)e.getSource()).DIRECTION==Direction.LEFT?-1:1, 0);
		else return;
	}
	
	@Override
	public void showYMD(YMD ymd) {
		getCentralBt().setText(MonthInEnglish.values()[ymd.getMonth()-1].name().toUpperCase()+", "+ymd.getYear());
		getPreviousBt().setText((ymd.getYear()-1)+"");
		getNextBt().setText((ymd.getYear()+1)+"");
	}
	
	// 이 위로는 외부 노출 API
	
	private CentralButton getCentralBt() {
		return (CentralButton)((Container)getComponent(1)).getComponent(0);
	}
	
	private YearMoveButton getPreviousBt() {
		return (YearMoveButton)((Container)getComponent(0)).getComponent(0);
	}
	
	private YearMoveButton getNextBt() {
		return (YearMoveButton)((Container)getComponent(2)).getComponent(0);
	}
	
	// 이 아래로부터는 뷰 구성
	
	private JPanel createCentralBtView() {
		return new TransparentPanel() {
			{
				setLayout(null);
				add(new CentralButton());
			}
			private int pw, ph;
			@Override
			public void paintComponent(Graphics g) {
				if(pw!=getWidth()||ph!=getHeight()) {
					pw = getWidth();
					ph = getHeight();
					getComponent(0).setBounds(pw/10, ph/3, pw*4/5, ph/3);
				}
				super.paintComponent(g);
			}
		};
	}
	
	private Container createSideBtView(Direction direction) {
		return new TransparentPanel() {
			{
				setLayout(null);
				add(new YearMoveButton(direction));
			}
			private int pw, ph;
			@Override
			public void paintComponent(Graphics g) {
				if(pw!=getWidth()||ph!=getHeight()) {
					pw = getWidth();
					ph = getHeight();
					getComponent(0).setBounds(pw/3, ph/3, pw/3, ph/3);
				}
				super.paintComponent(g);
			}
		};
	}
	
	private class CentralButton extends TextButton {
		private final Color FG = Color.GRAY;
		private final Font FONT = FontUtil.createDefaultFont(25);
		
		private CentralButton() {
			setNormalFg(FG);
			setHorizontalAlignment(CENTER);
			setEnableOpposingFgEffect(false);
			setEffectedFg(new Color(200,200,200));
			setFont(FONT);
		}
	}
	
	private class YearMoveButton extends TextButton {
		private final Color FG = ColorUtil.getOrangeColor();
		private final Font FONT = FontUtil.createDefaultFont(15);
		private final Direction DIRECTION;
		
		private YearMoveButton(Direction direction) {
			DIRECTION = direction;
			setHorizontalAlignment(CENTER);
			setNormalFg(FG);
			setFont(FONT);
		}
	}
}
