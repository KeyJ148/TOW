package tow.game.server.assistants;

import tow.engine.Vector2;
import tow.engine.net.server.GameServer;
import tow.engine.net.server.senders.ServerSendTCP;
import tow.game.server.Server;
import tow.game.server.data.ServerData;

import java.util.Random;

public class BoxCreator {

    public static final long BOX_CREATE_TIME = (long) (5 * Math.pow(10, 9));//Промежуток времени между созданием ящиков (на одного игрока)
    private long timeBoxDelta = BOX_CREATE_TIME; //Сколько осталось до создания нового ящика
    private int idBox = 0;

    public void update(long delta){
        timeBoxDelta -= delta;

        if (timeBoxDelta <= 0 && ServerData.battle){//Создание ящиков
            timeBoxDelta = BOX_CREATE_TIME/GameServer.peopleMax;
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
