package tow.game.server.assistants;

import tow.engine.Vector2;
import tow.engine.image.TextureManager;
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
        // Удаляем имеющиеся танки
        for (int i=0; i<ServerData.map.size(); i++) {
            ServerData.MapObject mapObject = ServerData.map.get(i);

            if (mapObject.textureHandler == TextureManager.getTexture("sys_tank")) {
                ServerData.map.remove(i);
                i--;
            }
        }

        //Обновляем позицию танков
        for (int i = 0; i < ServerData.playerData.length; i++) {
            ServerData.MapObject player = new ServerData.MapObject();

            player.x = ServerData.playerData[i].x;
            player.y = ServerData.playerData[i].y;
            player.textureHandler = TextureManager.getTexture("sys_tank");
            player.textureName = "sys_tank";

            ServerData.map.add(player);
        }

        //Генерируем новый ящик
        int w = TextureManager.getTexture("box_armor").getWidth();
        int h = TextureManager.getTexture("box_armor").getHeight();
        Vector2<Integer> pos = Server.genObject(w, h);

        Random rand = new Random();
        int typeBox = rand.nextInt(5);

        //Отправляем всем сообщение
        ServerSendTCP.sendAll(7, pos.x + " " + pos.y + " " + typeBox + " " + idBox);
    }
}
