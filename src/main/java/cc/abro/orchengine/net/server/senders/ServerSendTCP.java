package cc.abro.orchengine.net.server.senders;

import cc.abro.orchengine.net.server.GameServer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServerSendTCP {

    public static void send(int id, int type, String str) {
        if (!GameServer.connects[id].disconnect) {
            GameServer.connects[id].messagePackTCPSend.send(type + " " + str);
            GameServer.connects[id].numberSend++; //Кол-во отправленных пакетов
        }
    }

    public static void sendAllExceptId(int id, int type, String str) {
        for (int i = 0; i < GameServer.peopleMax; i++) {//Отправляем сообщение всем
            if (i != id) {//Кроме игрока, приславшего сообщение
                send(i, type, str);
            }
        }
    }

    public static void sendAll(int type, String str) {
        for (int i = 0; i < GameServer.peopleMax; i++) {//Отправляем сообщение всем
            send(i, type, str);
        }
    }

}
