package view.popup.common;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import util.ColorUtil;
import util.FontUtil;
import view.customized.RoundRectPanel;

public class SearchView extends RoundRectPanel {
	public SearchView() {
		setOpaque(true);
		setBackground(ColorUtil.getLightGray());
		setLayout(new LinearLayout(Orientation.HORIZONTAL, 0));
		add(new SearchIconView(), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(createTextField(), new LinearConstraints(3, LinearSpace.MATCH_PARENT));
	}
	
	public String getSearchText() {
		return ((JTextField)getComponent(1)).getText();
	}
	
	public void setText(String t) {
		((JTextField)getComponent(1)).setText(t);
	}
	
	@Override
	public void setFont(Font f) {
		super.setFont(f);
		if(getComponentCount()>0)	((JTextField)getComponent(1)).setFont(f);
	}
	
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if(getComponentCount()>0)	((JTextField)getComponent(1)).setForeground(fg);
	}
	
	private JTextField createTextField() {
		JTextField tf = new JTextField();
		tf.setBorder(null);
		tf.setOpaque(false);
		tf.setFont(FontUtil.createDefaultFont(15f));
		return tf;
	}
}
