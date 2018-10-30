package view.customized;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ActionCallablePanel extends RoundRectPanel {
	private final List<ActionListener> ACTION_LISTENERS = new ArrayList<>();
	 
	public ActionCallablePanel() {
		addMouseListener(new ActionCaller());
	}
	
	public ActionListener[] getActionListeners() {
		return ACTION_LISTENERS.toArray(new ActionListener[ACTION_LISTENERS.size()]);
	}
	
	public void addActionListener(ActionListener actionListener) {
		ACTION_LISTENERS.add(actionListener);
	}
	
	public void removeActionListener(ActionListener actionListener) {
		ACTION_LISTENERS.remove(actionListener);
	}
	
	@Override
	public void removeMouseListener(MouseListener l) {
		if(l instanceof ActionCaller)	return;
		super.removeMouseListener(l);
	}
	
	private class ActionCaller extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), "Action performed.");
			ActionListener[] als = getActionListeners();
			for(ActionListener al : als)	al.actionPerformed(ae);
		}
	}
}
