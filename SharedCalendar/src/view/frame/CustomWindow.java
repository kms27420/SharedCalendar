package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.Window.Type;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.ScreenSizeUtil;

public abstract class CustomWindow {
	public static final int TITLE_BAR_HEIGHT = 30;
	
	protected final Window WINDOW;
	
	public CustomWindow(Window window) {
		WINDOW = window;
		
		((JPanel)getSuperContentPane()).setOpaque(false);
		((JPanel)getSuperContentPane()).setBorder(new EmptyBorder(1,1,1,1));
		getSuperContentPane().setLayout(new BorderLayout());
		getSuperContentPane().add(new TitleBar(), BorderLayout.NORTH);
		getSuperContentPane().add(createNewContentPane());

		setUndecorated(true);
		setTitleBarExist(true);
		addFrameResizeAdapter();
		addTitleBarAdapter();
	}
	
	/**
	 * Specify the location by CustomFrame.LocationType
	 * @param type Direction you want to specify
	 */
	public void setLocation(LocationType type) {
		int x = (ScreenSizeUtil.getScreenWidth()-WINDOW.getWidth())/2;
		int y = (ScreenSizeUtil.getScreenHeight()-WINDOW.getHeight())/2;
		x += x*type.X;
		y += y*type.Y;
		WINDOW.setLocation(x, y);
	}
	/**
	 * Set the size of content pane.
	 * @param width Width to set.
	 * @param height Height to set.
	 */
	public void setContentPaneSize(int width, int height) {
		if(width<0 || height<0) throw new IllegalArgumentException("Ratios must be positive.");
		getSuperContentPane().setPreferredSize(new Dimension(width, height + (isTitleBarExist()?TITLE_BAR_HEIGHT:0)));
		WINDOW.pack();
	}
	/**
	 * Set the size of content pane.
	 * @param size Size to set.
	 */
	public void setContentPaneSize(Dimension size) {
		setContentPaneSize(size.width, size.height);
	}
	/**
	 * Set exist of the TitleBar of this frame.
	 * @param exist If true show TitleBar else hide TitleBar
	 */
	public void setTitleBarExist(boolean exist) {
		getTitleBar().setPreferredSize(new Dimension(0, exist?TITLE_BAR_HEIGHT:0));
		getSuperContentPane().revalidate();
		getSuperContentPane().repaint();
	}
	/**
     * Returns the <code>contentPane</code> object for this frame.
     * @return the <code>contentPane</code> property
     */
	public Container getContentPane() {
		return (Container)getSuperContentPane().getComponent(1);
	}
	/**
	 * Get the TitleBar instance of this frame.
	 * @return TitleBar instance of this frame
	 */
	public TitleBar getTitleBar() {
		return (TitleBar)getSuperContentPane().getComponent(0);
	}
	
	public boolean isVisible() {
		return WINDOW.isVisible();
	}
	
	public void setVisible(boolean visible) {
		WINDOW.setVisible(visible);
	}
	
	public void setType(Type type) {
		WINDOW.setType(type);
	}
	
	public void setOpacity(float opacity) {
		WINDOW.setOpacity(opacity);
	}
	
	public void setResizable(boolean resizable) {
		getFrameResizaAdapter().setResizable(resizable);
	}
	
	public void addFocusListener(FocusListener l) {
		WINDOW.addFocusListener(l);
	}
	
	public void removeFocusListener(FocusListener l) {
		WINDOW.removeFocusListener(l);
	}
	
	public abstract void setDefaultCloseOperation(int operation);
	protected abstract Container getSuperContentPane();
	protected abstract void setUndecorated(boolean undecorated);
	
	private boolean isTitleBarExist() {
		return getTitleBar().getPreferredSize().height == TITLE_BAR_HEIGHT;
	}
	
	private FrameResizeAdapter getFrameResizaAdapter() {
		for(MouseListener ml : getSuperContentPane().getMouseListeners())
			if(ml instanceof FrameResizeAdapter)	return (FrameResizeAdapter)ml;
		return null;
	}
	
	private Container createNewContentPane() {
		JPanel newContentPane = new JPanel();
		newContentPane.setLayout(new BorderLayout());
		newContentPane.setBackground(Color.WHITE);
		return newContentPane;
	}
	
	private void addFrameResizeAdapter() {
		FrameResizeAdapter frameResizeAdapter = new FrameResizeAdapter();
		getSuperContentPane().addMouseListener(frameResizeAdapter);
		getSuperContentPane().addMouseMotionListener(frameResizeAdapter);
		frameResizeAdapter = null;
	}
	
	private void addTitleBarAdapter() {
		getTitleBar().addFrameMoveAdapter(new FrameMoveAdapter());
		getTitleBar().addNavigationBtAdapter(new NavigationBtAdapter());
	}
	
	public static enum LocationType {
		NORTH(0,-1), SOUTH(0,1), WEST(-1,0), EAST(1,0),
		NORTH_WEST(-1,-1), NORTH_EAST(1,-1),
		SOUTH_WEST(-1,1), SOUTH_EAST(1,1),
		CENTER(0,0);
		
		public final int X, Y;
		private LocationType(int x, int y) {
			X = x;
			Y = y;
		}
	}
}
