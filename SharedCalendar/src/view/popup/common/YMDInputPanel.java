package view.popup.common;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import data.YMD;
import data.YMD.Type;
import model.DataStore;
import util.FontUtil;
import view.customized.PaddingView;
import view.customized.TransparentLabel;
import view.customized.TransparentPanel;
import view.customized.TransparentTextField;

public class YMDInputPanel extends PaddingView {
	public YMDInputPanel() {
		setOpaque(false);
		setPadding(0.1f, 0.1f, 0.1f, 0.1f);
		getContentPane().add(createContentPane());
	}
	
	public void showInitedView() {
		for(Type type : Type.values())	
			getInputField(type).setText((DataStore.getBeingShownYMD().get(type)<10 ? "0" : "") + DataStore.getBeingShownYMD().get(type));
	}
	
	public YMD getInputedYMD() {
		return new YMD(getInputedInt(Type.YEAR), getInputedInt(Type.MONTH), getInputedInt(Type.DATE));
	}
	
	private Container createContentPane() {
		TransparentPanel cp = new TransparentPanel();
		cp.setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		int[] weight = {4,3,3};
		NumericFilter keyFilter = new NumericFilter();
		EmptyFieldFiller emptyFieldFiller = new EmptyFieldFiller();
		for(Type type : Type.values()) {
			cp.add(new YMDInputFieldView(type), new LinearConstraints(weight[type.ordinal()], LinearSpace.MATCH_PARENT));
			((Container)((Container)cp.getComponent(type.ordinal())).getComponent(0)).getComponent(0).addKeyListener(keyFilter);
			((Container)((Container)cp.getComponent(type.ordinal())).getComponent(0)).getComponent(0).addFocusListener(emptyFieldFiller);
		}
		return cp;
	}
	
	private TransparentTextField getInputField(Type ymdType) {
		return (TransparentTextField)((Container)((Container)getContentPane().getComponent(0)).getComponent(ymdType.ordinal())).getComponent(0);
	}
	
	private int getInputedInt(Type ymdType) {
		return Integer.parseInt(getInputField(ymdType).getText());
	}
	
	private class NumericFilter extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			if(!isNumeric(e.getKeyChar())) {
				e.consume();
				return;
			}
			if(((JTextField)e.getComponent()).getSelectedText()!=null)	return;
			if(((JTextField)e.getComponent()).getText().length()==(e.getComponent()==getInputField(Type.YEAR).getComponent(0) ? 4 : 2))
				e.consume();
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
				if(getInputField(type).getText().equals("")) 
					getInputField(type).setText((DataStore.getBeingShownYMD().get(type)<10 ? "0" : "") + DataStore.getBeingShownYMD().get(type));
		}
	}
	
	private class YMDInputFieldView extends TransparentPanel {
		private YMDInputFieldView(Type type) {
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
			JLabel naviView = new TransparentLabel(type==Type.YEAR?"년":(type==Type.MONTH)?"월":"일");
			naviView.setHorizontalAlignment(JLabel.CENTER);
			naviView.setVerticalAlignment(JLabel.CENTER);
			naviView.setForeground(Color.WHITE);
			naviView.setFont(FontUtil.createDefaultFont(15f));
			return naviView;
		}
	}
}
