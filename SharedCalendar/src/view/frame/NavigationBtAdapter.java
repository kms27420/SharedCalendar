package view.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBtAdapter implements ActionListener {
	NavigationBtAdapter() {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof NavigationButton))	return;
		 
		if(((NavigationButton)e.getSource()).getText().equals("X"))	System.exit(0);
		else	FrameShower.INSTANCE.hideMainFrame();
	}
}
