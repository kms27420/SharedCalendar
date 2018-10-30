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
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.TransparentLabel;

public class AlertView extends WindowView implements CommonButtonsListener {
	private AlertCheckListener alertCheckListener = ()->{};
	
	public AlertView(ButtonsType buttonsType) {
		this("", buttonsType);
	}
	
	public AlertView(String alert, ButtonsType buttonsType) {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(createLabel(alert), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createButtonsView(buttonsType), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
	}
	
	public void setAlert(String alert) {
		((JLabel)getComponent(0)).setText(alert);
	}
	
	public void setAlertCheckListener(AlertCheckListener l) {
		alertCheckListener = l;
	}
	
	private JLabel createLabel(String alert) {
		JLabel label = new TransparentLabel(alert);
		label.setFont(FontUtil.createDefaultFont(15f));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);
		return label;
	}
	
	private CommonButtonsView createButtonsView(ButtonsType buttonsType) {
		CommonButtonsView cbv = new CommonButtonsView();
		cbv.setOpaque(false);
		cbv.setPadding(0.3f, 0.3f, 0.4f, 0.3f);
		cbv.setMargin(0.1f);
		cbv.setButtonsType(buttonsType);
		cbv.setCommonButtonsListener(this);
		
		return cbv;
	}
	
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		alertCheckListener.onAlertCheck();
		hideWindowView();
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}

	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.2f, 0.2f);
	}
	
	public static interface AlertCheckListener {
		public void onAlertCheck();
	}
}
