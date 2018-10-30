package view.popup;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import listener.join.JoinListener;
import util.ColorUtil;
import util.FontUtil;
import util.ScreenSizeUtil;
import util.WindowShower;
import util.WindowShower.SubViewType;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.PaddingView;
import view.customized.RoundRectButton;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.TransparentTextField;

public class JoinPanel extends WindowView implements ActionListener {
	private JoinListener joinEventListener = (i,p,n)->{};
	
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
		joinEventListener = l;
	}

	private final AlertView ALERT = new AlertView("패스워드를 다시 확인하여주십시오.", ButtonsType.CHECK_ONLY);
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof JLabel))	return;
		switch(((JLabel)e.getSource()).getText()) {
		case "CONFIRM" :
			if(getInputedPassword().equals(getInputedRepassword()))	joinEventListener.onJoin(getInputedID(), getInputedPassword(), getInputedName());
			else WindowShower.INSTANCE.showSubWindow(ALERT, SubViewType.ALERT);
			break;
		case "CANCEL" :
			hideWindowView();
			break;
			default : break;
		}
	}
	
	private String getInputedID() {
		return ((TransparentTextField)((PaddingView)((Container)getComponent(0)).getComponent(1)).getContentPane().getComponent(0)).getText();
	}
	
	private String getInputedName() {
		return ((TransparentTextField)((PaddingView)((Container)getComponent(1)).getComponent(1)).getContentPane().getComponent(0)).getText();
	}
	
	private String getInputedPassword() {
		return ((TransparentTextField)((PaddingView)((Container)getComponent(2)).getComponent(1)).getContentPane().getComponent(0)).getText();
	}
	
	private String getInputedRepassword() {
		return ((TransparentTextField)((PaddingView)((Container)getComponent(3)).getComponent(1)).getContentPane().getComponent(0)).getText();
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
		PaddingView bv = new PaddingView();
		bv.setOpaque(false);
		bv.setPadding(0.2f, 0.2f, 0.2f, 0.2f);
		bv.setMargin(0.2f);
		bv.getContentPane().add(createButton("CONFIRM"));
		bv.getContentPane().add(createButton("CANCEL"));
		return bv;
	}
	
	private JLabel createButton(String text) {
		RoundRectButton b = new RoundRectButton(text);
		b.addActionListener(this);
		b.setEnableEffect(true);
		b.setEffectedFg(ColorUtil.getDarkerColor(ColorUtil.getOrangeColor()));
		b.setNormalFg(ColorUtil.getOrangeColor());
		return b;
	}
	
	@Override
	protected Dimension getWindowSize() {
		return ScreenSizeUtil.getScaledSize(0.25f, 0.2f);
	}
}
