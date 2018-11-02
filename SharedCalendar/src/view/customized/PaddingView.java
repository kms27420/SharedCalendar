package view.customized;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PaddingView extends JPanel {
	private float top, left, bottom, right, margin;
	public PaddingView() {
		setLayout(new BorderLayout());
		add(new TransparentPanel(), BorderLayout.NORTH);
		add(new TransparentPanel(), BorderLayout.WEST);
		add(new MarginView());
		add(new TransparentPanel(), BorderLayout.SOUTH);
		add(new TransparentPanel(), BorderLayout.EAST);
	}
	
	public Container getContentPane() {
		return (Container)getComponent(2);
	}
	
	public void setPadding(float top, float left, float bottom, float right) {
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
	}
	
	public void setMargin(float margin) {
		this.margin = margin;
	}
	
	private int pw, ph, pCompCount;
	private float ptop, pleft, pbottom, pright, pmargin;
	private Dimension dtop = new Dimension();
	private Dimension dleft = new Dimension();
	private Dimension dbottom = new Dimension();
	private Dimension dright = new Dimension();
	private int compWidth, compHeight, marginWidth;
	private boolean isAdded;
	@Override
	public void paintComponent(Graphics g) {
		if(pw!=getWidth()||ph!=getHeight()
				||ptop!=top||pleft!=left||pbottom!=bottom||pright!=right
				||pmargin!=margin||pCompCount!=getContentPane().getComponentCount() || isAdded) {
			pw=getWidth();
			ph=getHeight();
			ptop=top;
			pleft=left;
			pbottom=bottom;
			pright=right;
			dtop.setSize(0, ptop*ph);
			dleft.setSize(pleft*pw, 0);
			dbottom.setSize(0, pbottom*ph);
			dright.setSize(pright*pw, 0);
			pmargin=margin;
			marginWidth=(int)(pw*pmargin);
			pCompCount=getContentPane().getComponentCount();
			isAdded = false;
			try{
				compWidth = (pw-dleft.width-dright.width-marginWidth*(pCompCount-1))/pCompCount;
			} catch(Exception e) {	compWidth=pw-dleft.width-dright.width;	}
			compHeight = ph-dtop.height-dbottom.height;
			getComponent(0).setPreferredSize(dtop);
			getComponent(1).setPreferredSize(dleft);
			getComponent(3).setPreferredSize(dbottom);
			getComponent(4).setPreferredSize(dright);
			
			revalidate();
			repaint();
		}
		super.paintComponent(g);
	}
	
	private class MarginView extends TransparentPanel {
		private MarginView() {
			setLayout(null);
		}
		
		@Override
		protected void addImpl(Component comp, Object constraints, int index) {
			super.addImpl(comp, constraints, index);
			isAdded = true;
		}
		
		private int startX;
		@Override
		public void paintComponent(Graphics g) {
			startX = pw-dleft.width-dright.width-compWidth*pCompCount-marginWidth*(pCompCount-1);
			for(int i=0;i<pCompCount;i++) 
				getComponent(i).setBounds(startX + (compWidth+marginWidth)*i, 0, compWidth, compHeight);
			
			super.paintComponent(g);
		}
	}
}
