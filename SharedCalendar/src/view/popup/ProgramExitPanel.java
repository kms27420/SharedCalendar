package view.popup;

import view.customized.CommonButtonsView.ButtonsType;
import view.popup.common.AlertView;
import view.popup.common.AlertView.AlertCheckListener;

public class ProgramExitPanel extends AlertView implements AlertCheckListener {
	public ProgramExitPanel() {
		super("정말 캘린더를 종료하시겠습니까?", ButtonsType.BOTH);
		setAlertCheckListener(this);
	}

	@Override
	public void onAlertCheck() {
		System.exit(0);
	}
}
