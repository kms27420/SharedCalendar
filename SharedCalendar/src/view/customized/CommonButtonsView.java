package view.customized;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommonButtonsView extends PaddingView {
	private final RoundRectButton CHECK_BUTTON = new RoundRectButton("CHECK");
	private final RoundRectButton CANCEL_BUTTON = new RoundRectButton("CANCEL");
	
	private CommonButtonsListener commonButtonsListener = new CommonButtonsAdapter();
	
	public CommonButtonsView() {
		setOpaque(false);
		setButtonsType(ButtonsType.BOTH);
		initCommonButtons();
	}
	
	public CommonButtonsView(ButtonsType buttonsType) {
		this();
		setButtonsType(buttonsType);
	}
	
	public void setCheckButtonText(String text) {
		CHECK_BUTTON.setText(text);
	}
	
	public void setCancelButtonText(String text) {
		CANCEL_BUTTON.setText(text);
	}
	
	public void setButtonsType(ButtonsType type) {
		getContentPane().removeAll();
		switch(type) {
		case BOTH :
			getContentPane().add(CHECK_BUTTON);
			getContentPane().add(CANCEL_BUTTON);
			break;
		case CHECK_ONLY :
			getContentPane().add(CHECK_BUTTON);
			break;
		case CANCEL_ONLY :
			getContentPane().add(CANCEL_BUTTON);
			break;
			default : break;
		}
	}
	
	public void setCommonButtonsListener(CommonButtonsListener l) {
		this.commonButtonsListener = l;
	}
	
	private void initCommonButtons() {
		ActionListener commonButtonsBinder = e-> {
			if(e.getSource()==((ActionCallableLabel)((Container)getComponent(2)).getComponent(0)))
				commonButtonsListener.onCheckButtonClick(e);
			else if(e.getSource()==((ActionCallableLabel)((Container)getComponent(2)).getComponent(1)))
				commonButtonsListener.onCancelButtonClick(e);
			else return;
		};
		CHECK_BUTTON.addActionListener(commonButtonsBinder);
		CANCEL_BUTTON.addActionListener(commonButtonsBinder);
	}
	
	public static interface CommonButtonsListener {
		public void onCheckButtonClick(ActionEvent e);
		public void onCancelButtonClick(ActionEvent e);
	}
	
	public static class CommonButtonsAdapter implements CommonButtonsListener {
		@Override
		public void onCheckButtonClick(ActionEvent e) {}
		@Override
		public void onCancelButtonClick(ActionEvent e) {}
	}
	
	public static enum ButtonsType {
		CHECK_ONLY, CANCEL_ONLY, BOTH;
	}
}
