package mainFrame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Central extends JComponent implements ActionListener {
	
	public Central() {
	}
	
	private void paint(Graphics2D g) {
	}
	
	private void update() {
		
	}
	
	//DO NOT TOUCH!!================================================================||
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		paint(g2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		update();
		repaint();
	}
	
}
