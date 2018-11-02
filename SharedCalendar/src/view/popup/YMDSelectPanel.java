package view.popup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.YMD;
import listener.view.ymd.YMDSelectAdapter;
import listener.view.ymd.YMDSelectListener;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.popup.common.AlertView;
import view.popup.common.YMDInputPanel;
import view.customized.PaddingView;
import view.customized.TransparentLabel;

public class YMDSelectPanel extends WindowView implements CommonButtonsListener {
	private YMDSelectListener ymdSelectListener = new YMDSelectAdapter();
	
	public YMDSelectPanel() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(new NaviView("SELECT DATE"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(new YMDInputPanel(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		((CommonButtonsView)getComponent(2)).setCommonButtonsListener(this);
	}
	
	@Override
	protected void showInitedView() {
		getYMDInputPanel().showInitedView();
	}
	
	private AlertView alertPanel = new AlertView("The date isn't exist.", ButtonsType.CHECK_ONLY);
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		YMD ymd = getYMDInputPanel().getInputedYMD();
		if(ymd.isValid())	ymdSelectListener.onYMDSelect(getYMDInputPanel().getInputedYMD());
		else {
			((AlertView)SubViewType.ALERT.VIEW).setAlert("해당 날짜가 존재하지 않습니다. 다시 입력해주세요.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
		}
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.2f, 0.3f);
	}

	public void setYMDSelectListener(YMDSelectListener l) {
		this.ymdSelectListener = l;
	}
	
	private YMDInputPanel getYMDInputPanel() {
		return (YMDInputPanel)getComponent(1);
	}
	
	private CommonButtonsView createButtonsView() {
		return new CommonButtonsView() {
			{
				setMargin(0.05f);
				setPadding(0.25f, 0.24f, 0.5f, 0.24f);
			}
		};
	}
	
	private class NaviView extends PaddingView {
		private NaviView(String text) {
			setPadding(0.2f,0.2f,0.3f,0.2f);
			setOpaque(false);
			getContentPane().add(createComp(text));
		}
		
		private TransparentLabel createComp(String text) {
			TransparentLabel c = new TransparentLabel(text);
			c.setText(text);
			c.setHorizontalAlignment(JLabel.CENTER);
			c.setVerticalAlignment(JLabel.BOTTOM);
			c.setForeground(new Color(197, 90, 17));
			c.setFont(FontUtil.createDefaultFont(20f));
			return c;
		}
	}	
}
