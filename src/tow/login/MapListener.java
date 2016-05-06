package tow.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class MapListener implements ActionListener{
	
	private LoginWindow frame;

	public MapListener(LoginWindow frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileOpen = new JFileChooser();
		fileOpen.setCurrentDirectory(new File("./res/map"));
		if (fileOpen.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
			File file = fileOpen.getSelectedFile();
			frame.map = file;
		}
	}

}
