package main.login;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorListener implements ActionListener {
	
	Color c;
	WindowMain frame;
	
	public ColorListener(Color c, WindowMain frame){
		this.c = c;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent ae){
		frame.setColor(c);
	} 

}
