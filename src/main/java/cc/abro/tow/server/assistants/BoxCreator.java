package cc.abro.tow.server.assistants;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.net.server.senders.ServerSendTCP;
import cc.abro.orchengine.services.RandomService;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.server.Server;
import cc.abro.tow.server.data.ServerData;

public class BoxCreator {

    public static final long BOX_CREATE_TIME = (long) (10 * Math.pow(10, 9));//Промежуток времени между созданием ящиков (на одного игрока на блок карты 1000х1000)
    private long timeBoxDelta = BOX_CREATE_TIME; //Сколько осталось до создания нового ящика
    private int idBox = 0;

    public void update(long delta) {
        timeBoxDelta -= delta;

        if (timeBoxDelta <= 0 && ServerData.battle) {//Создание ящиков
            double mapSize = (double) ServerData.map.getWidth() * ServerData.map.getHeight();
            timeBoxDelta = (long) (BOX_CREATE_TIME / GameServer.peopleMax / (mapSize/1000000));
            createBox();
            idBox++;
        }
    }

    private void createBox() {
        //Генерируем новый ящик
        int w = 16;
        int h = 16;
        Vector2<Integer> pos = Server.genObject(w, h);

        //Каждый тип ящика по 25%, при этом хилки делятся 5/20 на фулл и нет
        int typeBox = Context.getService(RandomService.class).getRandomIndexByWeight(25, 25, 25, 20, 5);

        //Отправляем всем сообщение
        ServerSendTCP.sendAll(7, pos.x + " " + pos.y + " " + typeBox + " " + idBox);
    }
}
