package tow.login;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tow.Global;

public class ColorListener implements ActionListener {
	
	Color c;
	LoginWindow frame;
	
	public ColorListener(Color c, LoginWindow frame){
		this.c = c;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent ae){
		frame.colorTank = c;
		Global.color = new org.newdawn.slick.Color(c.getRed(), c.getGreen(), c.getBlue());
		frame.panel.repaint();
	} 

}
