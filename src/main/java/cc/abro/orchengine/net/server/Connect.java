package cc.abro.orchengine.net.server;

import cc.abro.orchengine.net.server.readers.ServerReadTCP;
import cc.abro.orchengine.net.server.senders.MessagePackTCPSend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connect {

    public final int id; //Номер соединения в массиве в gameServer
    public final String ipRemote; //Ip клиента
    public final int portUDP; //Порт клиента для отправки UDP

    public final DataInputStream in; //Сокет для приёма TCP
    public final DataOutputStream out; //Сокет для отправки TCP
    public final MessagePackTCPSend messagePackTCPSend; //Очередь для отправки сообщений на клиент по TCP. Обрабатывается отдельным потоком

    public final MessagePack messagePack; //Хранение принятых сообщений, очередь на обработку
    public final ServerReadTCP serverReadTCP; //Поток для приёма сообщений

    public boolean disconnect = false; //Отключён ли этот игрок
    public int numberSend = 0; //Кол-во отправленных сообщений клиенту

    public Connect(int id, Socket socket, int portUDP) throws IOException {
        this.id = id;
        this.ipRemote = socket.getInetAddress().getHostAddress();
        this.portUDP = portUDP;

        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.messagePackTCPSend = new MessagePackTCPSend(this);

        this.messagePack = new MessagePack(id);
        this.serverReadTCP = new ServerReadTCP(id);
    }
}
