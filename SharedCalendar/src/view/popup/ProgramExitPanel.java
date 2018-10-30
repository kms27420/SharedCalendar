package view.popup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.TransparentLabel;

public class ProgramExitPanel extends WindowView implements CommonButtonsListener {
	public ProgramExitPanel() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL,0));
		add(createNaviView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		((CommonButtonsView)getComponent(1)).setCommonButtonsListener(this);
	}
	
	private JLabel createNaviView() {
		JLabel naviView = new TransparentLabel("정말 캘린더를 종료하시겠습니까?");
		naviView.setFont(FontUtil.createDefaultFont(15f));
		naviView.setForeground(Color.WHITE);
		naviView.setHorizontalAlignment(JLabel.CENTER);
		naviView.setVerticalAlignment(JLabel.CENTER);
		return naviView;
	}

	private CommonButtonsView createButtonsView() {
		return new CommonButtonsView() {
			{
				setMargin(0.05f);
				setPadding(0.1f, 0.24f, 0.5f, 0.24f);
			}
		};
	}
	
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		System.exit(0);
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.2f, 0.2f);
	}
}
