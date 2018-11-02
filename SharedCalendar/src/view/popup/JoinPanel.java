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

import listener.view.join.JoinListener;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.PaddingView;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.TransparentTextField;
import view.popup.common.AlertView;

public class JoinPanel extends WindowView implements CommonButtonsListener {
	private JoinListener joinListener = (i,p,n)->{};
	
	public JoinPanel() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(createInputView("ID", false), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createInputView("NAME", false), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createInputView("PASSWORD", true), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createInputView("RE-PASSWORD", true), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
	}
	
	public void setJoinListener(JoinListener l) {
		joinListener = l;
	}
	
	@Override
	protected void showInitedView() {
		getIDTextField().setText("");
		getNameTextField().setText("");
		getPWTextField().setText("");
		getRePWTextField().setText("");
	}

	
	
	@Override
	public void onCheckButtonClick(ActionEvent e) {
		if(getInputedPassword().equals(getInputedRepassword()))	
			joinListener.onJoin(getInputedID(), getInputedPassword(), getInputedName());
		else {
			((AlertView)SubViewType.ALERT.VIEW).setAlert("패스워드를 다시 확인하여주십시오.");
			WindowShower.INSTANCE.showSubWindow(SubViewType.ALERT);
		}
	}

	@Override
	public void onCancelButtonClick(ActionEvent e) {
		hideWindowView();
	}
	
	private String getInputedID() {
		return getIDTextField().getText();
	}
	
	private String getInputedName() {
		return getNameTextField().getText();
	}
	
	private String getInputedPassword() {
		return getPWTextField().getText();
	}
	
	private String getInputedRepassword() {
		return getRePWTextField().getText();
	}
	
	private TransparentTextField getIDTextField() {
		return (TransparentTextField)((PaddingView)((Container)getComponent(0)).getComponent(1)).getContentPane().getComponent(0);
	}
	
	private TransparentTextField getNameTextField() {
		return (TransparentTextField)((PaddingView)((Container)getComponent(1)).getComponent(1)).getContentPane().getComponent(0);
	}
	
	private TransparentTextField getPWTextField() {
		return (TransparentTextField)((PaddingView)((Container)getComponent(2)).getComponent(1)).getContentPane().getComponent(0);
	}
	
	private TransparentTextField getRePWTextField() {
		return (TransparentTextField)((PaddingView)((Container)getComponent(3)).getComponent(1)).getContentPane().getComponent(0);
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
		CommonButtonsView bv = new CommonButtonsView(ButtonsType.BOTH);
		bv.setOpaque(false);
		bv.setPadding(0.2f, 0.2f, 0.2f, 0.2f);
		bv.setMargin(0.1f);
		bv.setCheckButtonText("CONFIRM");
		bv.setCancelButtonText("CANCEL");
		bv.setFont(FontUtil.createDefaultFont(12f));
		bv.setForeground(ColorUtil.getDarkerColor(ColorUtil.getOrangeColor()));
		bv.setCommonButtonsListener(this);
		return bv;
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.25f, 0.35f);
	}
}
