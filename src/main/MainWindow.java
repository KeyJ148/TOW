package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	public MainWindow(){
		super(Global.setting.WINDOW_NAME);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(Global.game.render, BorderLayout.CENTER);
		
		if (Global.setting.FULL_SCREEN){
			setResizable(false);
			setUndecorated(true);
	        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	        gd.setFullScreenWindow(this);
		} else {
			setPreferredSize(new Dimension(Global.setting.WIDTH_SCREEN, Global.setting.HEIGHT_SCREEN));
			pack();
		}
		
		Global.game.start();
        setVisible(true);
        
        if (Global.setting.DEBUG_CONSOLE) System.out.println("Window create.");
	}
}
