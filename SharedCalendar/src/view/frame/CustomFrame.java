package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CustomFrame extends JFrame {
	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int TITLE_BAR_HEIGHT = 30;
	 
	CustomFrame() {
		// set frame's size & location
		setMinimumSize(new Dimension(SCREEN_SIZE.width/8, SCREEN_SIZE.height/8));
		setMaximumSize(SCREEN_SIZE);
		
		((JPanel)super.getContentPane()).setBorder(new EmptyBorder(1, 1, 1, 1));
		super.getContentPane().setLayout(new BorderLayout());
		super.getContentPane().add(new TitleBar(), BorderLayout.NORTH);
		super.getContentPane().add(createNewContentPane());					// add default(empty) view to replace with contentPane
		
		// set frame's properties
		super.setUndecorated(true);				// remove JFrame's title bar
		setTitleBarExist(true);				// add customized title bar
		addFrameResizeAdapter();
		addTitleBarAdapter();
	}
	/**
	 * Specify the location by CustomFrame.LocationType
	 * @param type Direction you want to specify
	 */
	public void setLocation(LocationType type) {
		int x = (SCREEN_SIZE.width-getWidth())/2;
		int y = (SCREEN_SIZE.height-getHeight())/2;
		x += x*type.X;
		y += y*type.Y;
		setLocation(x, y);
	}
	/**
	 * Set the preferredSize of the frame's contentPane at a ratio of the screen size.
	 * @param widthRatio Ratio of width
	 * @param heightRatio Ratio of height
	 */
	public void setSizeFromScreen(float widthRatio, float heightRatio) {
		if(widthRatio<0 || heightRatio<0) throw new IllegalArgumentException("Ratios must be positive.");
		super.getContentPane().setPreferredSize(new Dimension((int)(SCREEN_SIZE.width*widthRatio), (int)(SCREEN_SIZE.height*heightRatio)));
		pack();
	}
	/**
	 * Set exist of the TitleBar of this frame.
	 * @param exist If true show TitleBar else hide TitleBar
	 */
	public void setTitleBarExist(boolean exist) {
		getTitleBar().setPreferredSize(new Dimension(0, exist?TITLE_BAR_HEIGHT:0));
		super.getContentPane().revalidate();
		super.getContentPane().repaint();
	}
	/**
     * Returns the <code>contentPane</code> object for this frame.
     * @return the <code>contentPane</code> property
     */
	@Override
	public Container getContentPane() {
		if(super.getContentPane().getComponentCount()>1)	return (Container)super.getContentPane().getComponent(1);
		return super.getContentPane();
	}
	/**
	 * Get the TitleBar instance of this frame.
	 * @return TitleBar instance of this frame
	 */
	public TitleBar getTitleBar() {
		return (TitleBar)super.getContentPane().getComponent(0);
	}
	
	@Override
	public void setTitle(String title) {
		getTitleBar().setTitle(title);
	}
	
	@Override
	public String getTitle() {
		return getTitleBar().getTitle();
	}
	/**
	 * Use setTitleBarExist(boolean exist) instead of this one.
	 */
	@Override
	@Deprecated
	public void setUndecorated(boolean undecorated) {
		setTitleBarExist(!undecorated);
	}
	
	private Container createNewContentPane() {
		JPanel newContentPane = new JPanel();
		newContentPane.setLayout(new BorderLayout());
		newContentPane.setBackground(Color.WHITE);
		return newContentPane;
	}
	
	private void addFrameResizeAdapter() {
		FrameResizeAdapter frameResizeAdapter = new FrameResizeAdapter();
		((JPanel)super.getContentPane()).addMouseListener(frameResizeAdapter);
		((JPanel)super.getContentPane()).addMouseMotionListener(frameResizeAdapter);
		frameResizeAdapter = null;
	}
	
	private void addTitleBarAdapter() {
		getTitleBar().addFrameMoveAdapter(new FrameMoveAdapter());
		getTitleBar().addNavigationBtAdapter(new NavigationBtAdapter());
	}
	/**
	 * An enum class representing the position of a frame in a direction.
	 * @author Kwon
	 *
	 */
	public static enum LocationType {
		NORTH(0,-1), SOUTH(0,1), CENTER(0,0), WEST(-1,0), EAST(1,0),
		NORTH_WEST(-1,-1), NORTH_EAST(1,-1),
		SOUTH_WEST(-1,1), SOUTH_EAST(1,1);
		
		private final int X, Y;
		
		private LocationType(int x, int y) {
			X = x;
			Y = y;
		}
	}
}
