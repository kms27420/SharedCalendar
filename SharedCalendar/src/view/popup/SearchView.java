package view.popup;

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
	
	private JTextField createTextField() {
		JTextField tf = new JTextField();
		tf.setOpaque(false);
		tf.setFont(FontUtil.createDefaultFont(15f));
		return tf;
	}
}
