package view.customized;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import util.FontUtil;

public class TransparentTextField extends TransparentPanel implements FocusListener {
	public TransparentTextField(boolean isPasswordMode) {
		setLayout(new BorderLayout());
		add(isPasswordMode?new JPasswordField():new JTextField());
		initTextField();
	}
	
	public String getText() {
		return ((JTextField)getComponent(0)).getText();
	}
	
	public void setText(String text) {
		((JTextField)getComponent(0)).setText(text);
	}
	
	public void setHorizontalAlignment(int alignment) {
		((JTextField)getComponent(0)).setHorizontalAlignment(alignment);
	}
	
	public void setCaretColor(Color c) {
		((JTextField)getComponent(0)).setCaretColor(c);
	}
	
	@Override
	public void setForeground(Color fg) {
		if(getComponentCount()==0)	super.setForeground(fg);
		else	((JTextField)getComponent(0)).setForeground(fg);
	}
	
	@Override
	public void setFont(Font f) {
		if(getComponentCount()==0)	super.setFont(f);
		else	((JTextField)getComponent(0)).setFont(f);
	}
	
	@Override
	public void setBorder(Border border) {
		if(getComponentCount()==0)	super.setBorder(border);
		else	((JTextField)getComponent(0)).setBorder(border);
	}
	
	private void initTextField() {
		JTextField tf = (JTextField)getComponent(0);
		tf.setOpaque(false);
		tf.setHorizontalAlignment(JTextField.CENTER);
		tf.setCaretColor(Color.WHITE);
		tf.setForeground(Color.WHITE);
		tf.setFont(FontUtil.createDefaultFont(15f));
		tf.setBorder(new EmptyBorder(0,0,2,0));
		tf.addFocusListener(this);
	}
	
	public String getSelectedText() {
		return ((JTextField)getComponent(0)).getSelectedText();
	}

	@Override
	public void focusLost(FocusEvent e) {
		e.getComponent().revalidate();
		e.getComponent().repaint();
	}
	@Override
	public void focusGained(FocusEvent e) {
		((JTextField)e.getComponent()).selectAll();
		e.getComponent().revalidate();
		e.getComponent().repaint();
	}
	
	private Color focusGainedUnderline = Color.WHITE;
	private Color focusLostUnderline = Color.GRAY;
	private Stroke underlineStroke = new BasicStroke(2f);
	@Override
	public void paint(Graphics g) {
		g.setColor(getComponent(0).isFocusOwner()?focusGainedUnderline:focusLostUnderline);
		((Graphics2D)g).setStroke(underlineStroke);
		g.drawLine(0, getHeight()-2, getWidth(), getHeight()-2);
		super.paint(g);
	}
}
