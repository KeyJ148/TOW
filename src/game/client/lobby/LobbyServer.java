package game.client.lobby;

import engine.Loader;
import engine.io.Logger;
import engine.net.client.Connector;
import engine.setting.SettingStorage;
import game.client.ClientData;
import game.server.ServerLoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LobbyServer implements ActionListener, StartServerListener, Runnable{

    private LobbyWindow lobbyWindow;

    private int port;
    private String name;

    private ServerSocket serverSocket;
    private ArrayList<UserConnect> connects = new ArrayList<>();

    public LobbyServer(int port, String name){
        this.port = port;
        this.name = name;

        this.lobbyWindow = new LobbyWindow(this);
        lobbyWindow.addPlayerToLobby(name);

        new Thread(this).start();
    }

    @Override
    public void run() {
        mainServerProccess();
    }

    private void mainServerProccess(){
        try {
            serverSocket = new ServerSocket(port + 1);

            while (!serverSocket.isClosed()) {
                Socket sock = serverSocket.accept();

                //Создание подключения
                UserConnect newConnect = new UserConnect(sock);

                //Отправка нового подключению своего ника
                newConnect.out.writeUTF(name);

                for(UserConnect connect : connects){
                    newConnect.out.writeUTF(connect.name); //Отправка новому подключению данные о других игроках
                    connect.out.writeUTF(newConnect.name); //Отправка другим игрокам новых данные о клиенте
                }

                //Добавление в общий список подключений
                connects.add(newConnect);

                //Отображение нового ника в окне лобби
                lobbyWindow.addPlayerToLobby(newConnect.name);
            }
        } catch (IOException e){}
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        //Закрываем окно с лобби
        lobbyWindow.dispose();

        try{
            serverSocket.close();
        } catch (IOException e){
            Logger.println(e.getMessage(), Logger.Type.ERROR);
        }

        //Запуск потока сервера (+1 потому что хост тоже подключится к серверу)
        ServerLoader.startServerListener = this;
        ServerLoader.mapPath = ClientData.map;
        new ServerLoader(port, connects.size()+1, SettingStorage.Performance.MAX_POWER_SERVER);
    }

    //Обратная связь сервера о том, что он готов принять клиентов
    @Override
    public void serverStart(){
        try {
            for (UserConnect connect : connects){
                connect.out.writeUTF(" "); //Отправка всем игрокам сообщения о старте сервера
            }

            new Connector().connect("127.0.0.1", port);
        } catch (IOException e){
            Logger.println(e.getMessage(), Logger.Type.ERROR);
            Loader.exit();
        }
    }

    public class UserConnect{
        public DataInputStream in;
        public DataOutputStream out;
        public String name;

        public UserConnect(Socket sock) throws IOException{
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());
            name = in.readUTF();
        }
    }
}
