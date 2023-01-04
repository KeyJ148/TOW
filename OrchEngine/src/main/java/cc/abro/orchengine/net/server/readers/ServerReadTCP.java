package cc.abro.orchengine.net.server.readers;

import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.net.server.MessagePack;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class ServerReadTCP extends Thread {

    private int id; //номер соединения в массиве в gameServer

    public ServerReadTCP(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        //Постоянный обмен данными (на TCP)
        //Только после подключения всех игроков
        String str;
        try {
            while (true) {
                str = GameServer.connects[id].in.readUTF();
                GameServer.connects[id].messagePack.add(str, MessagePack.Message.InetType.TCP);
            }
        } catch (IOException e) {
            log.info("Player disconnect (id: " + id + ")");
            GameServer.disconnect++;
            GameServer.connects[id].disconnect = true;
        }
    }

}