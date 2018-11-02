package view.customized;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;

public class CommonScrollPane extends JPanel {
	private int shownCompCount = 5;
	private int scrollBarWidth;
	public CommonScrollPane() {
		setOpaque(false);
		setLayout(new BorderLayout());
		add(new ContentPane());
		add(new ScrollBar(), BorderLayout.EAST);
		addScrollAdapter(new ScrollAdapter());
		setScrollBarWidth(10);
	}
	
	public void setScrollBarWidth(int scrollBarWidth) {
		this.scrollBarWidth = scrollBarWidth;
		getComponent(1).setPreferredSize(new Dimension(scrollBarWidth, 0));
		revalidate();
		repaint();
	}
	
	public Container getContentPane() {
		return (Container)getComponent(0);
	}
	
	public void setScrollBarColor(Color color) {
		((ScrollBar)getComponent(1)).setBackground(color);
	}
	
	private int pw, ph, pCompCnt;
	@Override
	public void paintComponent(Graphics g) {
		if(pw!=getWidth()-scrollBarWidth || ph!=getHeight() || pCompCnt!=getContentPane().getComponentCount()) {
			pw = getWidth()-scrollBarWidth;
			ph = getHeight();
			pCompCnt = getContentPane().getComponentCount();
			updateHeightVariables();
			updateScrollPaneY();
			updateScrollBarY();
			revalidate();
			repaint();
		}
		super.paintComponent(g);
	}
	
	private int scrollBarHeight, paneHeight, compHeight;
	private int scrollPaneY, scrollBarY;
	private void updateHeightVariables() {
		compHeight = getHeight()/shownCompCount;
		paneHeight = pCompCnt*compHeight;
		try {
			scrollBarHeight = getHeight()*getHeight()/paneHeight;
			scrollBarHeight = scrollBarHeight>getHeight() ? getHeight() : scrollBarHeight;
		} catch(Exception e) {	scrollBarHeight = getHeight();	}
	}
	
	private void updateScrollPaneY() {
		scrollPaneY = scrollPaneY < getHeight()-paneHeight ? 
				getHeight()-paneHeight : scrollPaneY;
		scrollPaneY = scrollPaneY > 0 ? 0 : scrollPaneY;
	}
	
	private void updateScrollBarY() {
		scrollBarY = scrollBarY<0 ? 0 : scrollBarY;
		scrollBarY = scrollBarY > getHeight()-scrollBarHeight ? getHeight()-scrollBarHeight : scrollBarY;
	}
	
	private void addScrollAdapter(ScrollAdapter adapter) {
		getContentPane().addMouseWheelListener(adapter);
		((ScrollBar)getComponent(1)).addScrollBarAdapter(adapter);
	}
	
	private void moveScroll(int movedScroll) {
		((ContentPane)getComponent(0)).moveScroll(movedScroll);
		((ScrollBar)getComponent(1)).moveScroll(movedScroll);
		revalidate();
		repaint();
	}
	
	private class ContentPane extends TransparentPanel {
		private ContentPane() {
			setLayout(null);
		}
		
		private void moveScroll(int movedScroll) {
			scrollPaneY += movedScroll*paneHeight/getHeight();
			updateScrollPaneY();
		}
		
		@Override
		protected void addImpl(Component comp, Object constraints, int index) {
			super.addImpl(comp, constraints, index);
			revalidate();
			repaint();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Component[] comps = getComponents();
			for(int i=0;i<comps.length;i++)
				comps[i].setBounds(0, scrollPaneY+i*compHeight, pw, compHeight);
			revalidate();	
			super.paintComponent(g);
		}
	}
	
	private class ScrollBar extends TransparentPanel {
		private final Color BG = new Color(217, 217, 217);
		
		private ScrollBar() {
			setLayout(null);
			add(new JPanel());
			getComponent(0).setBackground(BG);
		}
		
		@Override
		public void setBackground(Color bg) {
			if(getComponentCount()>0)	getComponent(0).setBackground(bg);
			else	super.setBackground(bg);
		}
		
		private void addScrollBarAdapter(ScrollAdapter adapter) {
			getComponent(0).addMouseListener(adapter);
			getComponent(0).addMouseMotionListener(adapter);
			addMouseWheelListener(adapter);
		}
		
		private void moveScroll(int movedScroll) {
			scrollBarY -= movedScroll;
			updateScrollBarY();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			if(scrollBarHeight==getHeight())	((JPanel)getComponent(0)).setOpaque(false);
			else {
				((JPanel)getComponent(0)).setOpaque(true);
				getComponent(0).setBounds(0, scrollBarY, getWidth(), scrollBarHeight);
			}
			super.paintComponent(g);
		}
	}
	
	private class ScrollAdapter extends MouseAdapter {
		private int py, movedScroll;
		@Override
		public void mousePressed(MouseEvent e) {
			py = e.getYOnScreen();
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(getHeight()>=paneHeight)	return;
			movedScroll = py-e.getYOnScreen();
			py = e.getYOnScreen();
			moveScroll(movedScroll);
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(getHeight()>=paneHeight)	return;
			moveScroll(-e.getWheelRotation()*4);
		}
	}
}
