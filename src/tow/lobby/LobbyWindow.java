package tow.lobby;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import tow.Global;
import tow.login.ConnectListener;
import tow.server.GameServer;

public class LobbyWindow extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public final int wFrame = 400+7+7;//Размер окна
	public final int hFrame = 235;
	
	private JTextField tfPortHost;
	private LobbyHostThread lobbyHostThread;
	private DefaultListModel<String> listModel;
	private boolean connect = false;//Запустился сервер? 
	
	public LobbyWindow(boolean server, ConnectListener cl, JTextField tfPortHost){//Сервер ли?
		super("Lobby");
		this.tfPortHost = tfPortHost;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		int wDisplay = sSize.width;
		int hDisplay = sSize.height;
		setBounds(wDisplay/2-wFrame/2,hDisplay/2-hFrame/2,wFrame,hFrame);
		
		//Создаём список
		listModel = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listModel);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(0);
		list.setBounds(0, 0, wFrame-50, hFrame);
		
		//Создаём граф. объект списка
		JScrollPane scroll = new JScrollPane(list);
        
        //Создаём главный layout
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		
		//Создаём кнопку запуска
        if (server){
	        JButton buttonStart = new JButton("Start");
	        buttonStart.addActionListener(new ButtonStartListener());
	        panel.add(buttonStart, BorderLayout.SOUTH);
        }
        
        setContentPane(panel);
		setVisible(true);
		
		if (server) lobbyHostThread = new LobbyHostThread(tfPortHost);
		new LobbyClientThread(cl, listModel);
	}
	
	//Листенер для кнопки старта
	class ButtonStartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			startServer();
			while (!connect){
				try {
					Thread.sleep(0,1);
				} catch (InterruptedException ie) {
					Global.error("Wait start server");
				}
			}
			
			lobbyHostThread.startServer();//Сообщить о старте сервера
			lobbyHostThread.close();
		}
	}
	
	public void startServer(){
		new Thread(this).start();
	}
	
	//Поток для старта сервера
	@Override
	public void run() {
		String maxPowerServer = "false";
		if (Global.setting.MAX_POWER_SERVER) maxPowerServer = "true"; 
		String[] args = {tfPortHost.getText(), String.valueOf(lobbyHostThread.in.size()), maxPowerServer};//количество игроков
		GameServer.fromClient(args, this);
	} 
	
	//Сигнал сервера о готовности коннекта клиентов
	public void connect(){
		connect = true;
	}
	
}
