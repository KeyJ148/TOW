package tow.game.client.login.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorButtonListener implements ActionListener {
	
	Color c;
	LoginWindow frame;
	
	public ColorButtonListener(Color c, LoginWindow frame){
		this.c = c;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent ae){
		frame.recolorTank(c);
	} 

}
