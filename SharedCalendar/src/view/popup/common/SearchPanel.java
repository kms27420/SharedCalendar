package view.popup.common;

import java.awt.Container;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import util.FontUtil;
import util.WindowShower.WindowView;
import view.customized.CommonButtonsView;
import view.customized.CommonButtonsView.ButtonsType;
import view.customized.CommonButtonsView.CommonButtonsListener;
import view.customized.CommonScrollPane;

public abstract class SearchPanel extends WindowView implements CommonButtonsListener {
	public SearchPanel() {
		setOpaque(false);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		add(new AlertLabel("RESULT LIST"), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		add(new CommonScrollPane(), new LinearConstraints(10, LinearSpace.MATCH_PARENT));
		add(createSearchView(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
		add(createButtonsView(), new LinearConstraints(2, LinearSpace.MATCH_PARENT));
	}
	
	protected SearchView getSearchView() {
		return (SearchView)getComponent(2);
	}
	
	protected Container getScrollPaneContentPane() {
		return ((CommonScrollPane)getComponent(1)).getContentPane();
	}
	
	private SearchView createSearchView() {
		SearchView sv = new SearchView();
		sv.setFont(FontUtil.createDefaultFont(25f));
		return sv;
	}
	
	private CommonButtonsView createButtonsView() {
		CommonButtonsView bv = new CommonButtonsView(ButtonsType.BOTH);
		bv.setCheckButtonText("SEARCH");
		bv.setCancelButtonText("BACK");
		bv.setPadding(0.1f, 0.2f, 0.1f, 0.2f);
		bv.setMargin(0.1f);
		bv.setCommonButtonsListener(this);
		return bv;
	}
}
