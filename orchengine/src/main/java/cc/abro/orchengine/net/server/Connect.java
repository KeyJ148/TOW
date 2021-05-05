package cc.abro.orchengine.net.server;

import cc.abro.orchengine.net.server.readers.ServerReadTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connect {

    public int id; //Номер соединения в массиве в gameServer
    public boolean disconnect = false; //Отключён ли этот игрок
    public int numberSend = 0; //Кол-во отправленных сообщений клиенту

    public String ipRemote; //Ip клиента
    public int portUDP; //Порт клиента для отправки UDP

    public DataInputStream in; //Сокет для приёма TCP
    public DataOutputStream out; //Сокет для отправки TCP

    public volatile MessagePack messagePack; //Хранение принятых сообщений, очередь на обработку
    public ServerReadTCP serverReadTCP; //Поток для приёма сообщений

    public Connect(int id, Socket socket, int portUDP) throws IOException {
        this.id = id;
        this.ipRemote = socket.getInetAddress().getHostAddress();
        this.portUDP = portUDP;

        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        this.messagePack = new MessagePack(id);
        this.serverReadTCP = new ServerReadTCP(id);
    }

}
