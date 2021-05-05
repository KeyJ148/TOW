package cc.abro.tow.server.assistants;

import cc.abro.orchengine.Vector2;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.net.server.senders.ServerSendTCP;
import cc.abro.tow.server.Server;
import cc.abro.tow.server.data.ServerData;

import java.util.Random;

public class BoxCreator {

    public static final long BOX_CREATE_TIME = (long) (5 * Math.pow(10, 9));//Промежуток времени между созданием ящиков (на одного игрока)
    private long timeBoxDelta = BOX_CREATE_TIME; //Сколько осталось до создания нового ящика
    private int idBox = 0;

    public void update(long delta) {
        timeBoxDelta -= delta;

        if (timeBoxDelta <= 0 && ServerData.battle) {//Создание ящиков
            timeBoxDelta = BOX_CREATE_TIME / GameServer.peopleMax;
            createBox();
            idBox++;
        }
    }

    private void createBox() {
        //Генерируем новый ящик
        int w = 16;
        int h = 16;
        Vector2<Integer> pos = Server.genObject(w, h);

        Random rand = new Random();
        int typeBox = rand.nextInt(5);

        //Отправляем всем сообщение
        ServerSendTCP.sendAll(7, pos.x + " " + pos.y + " " + typeBox + " " + idBox);
    }
}
