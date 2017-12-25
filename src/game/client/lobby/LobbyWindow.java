package game.client.lobby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LobbyWindow extends JFrame {

    public static final String FRAME_NAME = "Lobby server";
    public static final int FRAME_WIDTH = 400+7+7;//Размер окна
    public static final int FRAME_HEIGHT = 235;

    private DefaultListModel<String> listModel;
    private JPanel panel; //Главный layout

    public LobbyWindow(){
        super(FRAME_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int wDisplay = sSize.width;
        int hDisplay = sSize.height;
        setBounds(wDisplay/2-FRAME_WIDTH/2, hDisplay/2-FRAME_HEIGHT/2, FRAME_WIDTH, FRAME_HEIGHT);

        createGUIElements();
        setVisible(true);
    }

    //Если это сервер, то передаем листинер для кнопки старта
    public LobbyWindow(ActionListener listener){
        this();

        //Создаём кнопку запуска
        JButton buttonStart = new JButton("Start");
        buttonStart.addActionListener(listener);
        panel.add(buttonStart, BorderLayout.SOUTH);
    }

    public void addPlayerToLobby(String name){
        listModel.addElement(name);
        repaint();
    }

    private void createGUIElements(){
        //Создаём список
        listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(0);
        list.setBounds(0, 0, FRAME_WIDTH-50, FRAME_HEIGHT);

        //Создаём граф. объект списка
        JScrollPane scroll = new JScrollPane(list);

        //Создаём главный layout
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        setContentPane(panel);
    }
}
