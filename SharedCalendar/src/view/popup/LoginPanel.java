package view.popup;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import listener.view.login.LoginChangeAdapter;
import listener.view.login.LoginListener;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.PaddingView;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.TransparentTextField;

public class LoginPanel extends WindowView implements CommonButtonsListener {
	private LoginListener loginListener = new LoginChangeAdapter();
	
	public LoginPanel() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(createInputView("ID", false), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createInputView("PASSWORD", true), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
	}
	
	public void setLoginListener(LoginListener l) {
		loginListener = l;
	}
	
	@Override
	protected void showInitedView() {
		getIDTextField().setText("");
		getPWTextField().setText("");
	}

	@Override
	public void onCheckButtonClick(ActionEvent e) {
		loginListener.onLogin(getInputedID(), getInputedPassword());
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}
	
	private String getInputedID() {
		return getIDTextField().getText();
	}
	
	private String getInputedPassword() {
		return getPWTextField().getText();
	}
	
	private TransparentTextField getIDTextField() {
		return (TransparentTextField)((PaddingView)((Container)getComponent(0)).getComponent(1)).getContentPane().getComponent(0);
	}
	
	private TransparentTextField getPWTextField() {
		return (TransparentTextField)((PaddingView)((Container)getComponent(1)).getComponent(1)).getContentPane().getComponent(0);
	}
	
	private Container createInputView(String text, boolean isPwMode) {
		TransparentLabel guide = new TransparentLabel(text);
		guide.setHorizontalAlignment(JLabel.RIGHT);
		guide.setVerticalAlignment(JLabel.CENTER);
		guide.setFont(FontUtil.createDefaultFont(15f));
		guide.setForeground(Color.WHITE);
		
		PaddingView iv = new PaddingView();
		iv.setOpaque(false);
		iv.setPadding(0.2f, 0.2f, 0.2f, 0.2f);
		iv.getContentPane().add(new TransparentTextField(isPwMode));

		TransparentPanel rv = new TransparentPanel();
		rv.setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		rv.add(guide, new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		rv.add(iv, new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		return rv;
	}
	
	private Container createButtonsView() {
		CommonButtonsView bv = new CommonButtonsView();
		bv.setOpaque(false);
		bv.setPadding(0.2f, 0.2f, 0.2f, 0.2f);
		bv.setMargin(0.1f);
		bv.setCheckButtonText("CONFIRM");
		bv.setCancelButtonText("CANCEL");
		bv.setForeground(ColorUtil.getDarkerColor(ColorUtil.getOrangeColor()));
		bv.setFont(FontUtil.createDefaultFont(12f));
		bv.setCommonButtonsListener(this);
		return bv;
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.25f, 0.2f);
	}
}
