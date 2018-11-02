package view.popup.common;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.Time;
import data.Time.Type;
import util.FontUtil;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.TransparentTextField;

public class TimeInputPanel extends TransparentPanel {
	private Time shownTime;
	
	public TimeInputPanel() {
		setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		int[] weight = {4,3,3};
		NumericFilter keyFilter = new NumericFilter();
		EmptyFieldFiller emptyFieldFiller = new EmptyFieldFiller();
		for(Type type : Type.values()) {
			add(new TimeInputFieldView(type), new LinearConstraints(weight[type.ordinal()], LinearSpace.MATCH_PARENT));
			getInputField(type).addKeyListener(keyFilter);
			getInputField(type).addFocusListener(emptyFieldFiller);
		}
	}
	
	public void showInitedView(Time time) {
		shownTime = time;
		for(Type type : Type.values())	getInputField(type).setText(shownTime.get(type)<10 ? "0"+shownTime.get(type) : shownTime.get(type)+"");
	}
	
	public Time getInputedTime() {
		return new Time(getInputedInt(Type.HOUR), getInputedInt(Type.MINUTE));
	}
	
	private TransparentTextField getInputField(Type timeType) {
		return (TransparentTextField)((Container)getComponent(timeType.ordinal())).getComponent(0);
	}
	
	private int getInputedInt(Type timeType) {
		return Integer.parseInt(getInputField(timeType).getText());
	}
	
	private class NumericFilter extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			if(!isNumeric(e.getKeyChar())) {
				e.consume();
				return;
			}
			if(((TransparentTextField)e.getComponent()).getSelectedText()!=null)	return;
			if(((JTextComponent)e.getComponent()).getText().length()==2)	e.consume();
		}
		
		private boolean isNumeric(char c) {
			try {
				Integer.parseInt(c+"");
			} catch(Exception e) {	return false;	}
			return true;
		}
	}
	
	private class EmptyFieldFiller extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			for(Type type : Type.values())
				if(getInputField(type).getText().equals("")) {
					if(shownTime==null)	getInputField(type).setText("00");
					else	getInputField(type).setText(shownTime.get(type)+"");
				}
		}
	}
	
	private class TimeInputFieldView extends TransparentPanel {
		private TimeInputFieldView(Type type) {
			setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
			add(createInputField(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
			add(createNaviView(type), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		}
		
		private TransparentTextField createInputField() {
			TransparentTextField inputField = new TransparentTextField(false);
			inputField.setHorizontalAlignment(JTextField.CENTER);
			inputField.setFont(FontUtil.createDefaultFont(25f));
			inputField.setOpaque(false);
			inputField.setBorder(null);
			return inputField;
		}
		
		private JLabel createNaviView(Type type) {
			JLabel naviView = new TransparentLabel(type==Type.HOUR?"시":"분");
			naviView.setHorizontalAlignment(JLabel.CENTER);
			naviView.setVerticalAlignment(JLabel.CENTER);
			naviView.setForeground(Color.WHITE);
			naviView.setFont(FontUtil.createDefaultFont(15f));
			return naviView;
		}
	}
}
