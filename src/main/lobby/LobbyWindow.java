package main.lobby;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class LobbyWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public final int wFrame = 400+7+7;//Размер окна
	public final int hFrame = 235;
	
	public LobbyWindow(boolean server){//Сервер ли?
		super("Lobby");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		int wDisplay = sSize.width;
		int hDisplay = sSize.height;
		setBounds(wDisplay/2-wFrame/2,hDisplay/2-hFrame/2,wFrame,hFrame);
		
		//Создаём список
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listModel);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(0);
		list.setBounds(0, 0, wFrame-50, hFrame);
		
		
		for (int i = 0; i < 250; i++) {
		    listModel.addElement("Элемент списка " + i);
		}
		
		JScrollPane scroll = new JScrollPane(list);
        scroll.setBounds(0, 0, wFrame-15, hFrame-37-30);
        
        add(scroll);
		
		setVisible(true);
	}
	
}
