package game.server.assistants;

import engine.Vector2;
import engine.image.TextureManager;
import engine.io.Logger;
import engine.net.server.GameServer;
import game.server.Server;
import game.server.data.ServerData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MapLoader implements Runnable{

    public void loadRandomMap(String allMapsPath){
        loadMap(chooseRandomMap(allMapsPath));
    }

    public void loadMap(File map){
        Logger.println("Loading map: " + map.getName(), Logger.Type.SERVER_INFO);

        ServerData.map.clear();

        loadMapToMemory(map);
        genTanks();

        sendMap();
    }

    //Выбор рандомной карты из папки с картами
    private File chooseRandomMap(String allMapsPath){
        File[] allFiles = new File(allMapsPath).listFiles();

        String path;
        do {
            int randFileIndex = (int) Math.round(Math.random()*(allFiles.length-1));
            String nameOfRandFile = allFiles[randFileIndex].getName();

            path = allMapsPath + "/" + nameOfRandFile.substring(0, nameOfRandFile.lastIndexOf('.')) + ".map";
        } while (!new File(path).exists()); //Если у файла расширение .map, то выходим из цикла

        return new File(path);
    }

    //Загрузка всех объектов карты в память сервера (ServerData)
    private void loadMapToMemory(File map){
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(map));

            String line = fileReader.readLine();
            ServerData.widthMap = Integer.parseInt(line.split(" ")[0]);
            ServerData.heightMap = Integer.parseInt(line.split(" ")[1]);
            ServerData.background = line.split(" ")[2];

            while ((line = fileReader.readLine()) != null){
                ServerData.MapObject mapObject = new ServerData.MapObject();

                mapObject.x = Integer.parseInt(line.split(" ")[0]);
                mapObject.y = Integer.parseInt(line.split(" ")[1]);
                mapObject.direction = Integer.parseInt(line.split(" ")[2]);
                mapObject.textureHandler = TextureManager.getTexture((line.split(" ")[3]));
                mapObject.textureName = line.split(" ")[3];

                ServerData.map.add(mapObject);
            }

            fileReader.close();
        } catch (IOException e) {
            Logger.println("Error map loading from: " + map.getAbsolutePath(), Logger.Type.SERVER_ERROR);
        }
    }

    //Генерация танков на свободных позициях и сохранение результата в PlayerData
    private void genTanks(){
        int wTank = TextureManager.getTexture("sys_tank").getWidth();
        int hTank = TextureManager.getTexture("sys_tank").getHeight();

        for(int i=0; i<ServerData.playerData.length; i++){
            Vector2<Integer> point = Server.genObject(wTank, hTank);
            ServerData.playerData[i].x = point.x;
            ServerData.playerData[i].y = point.y;
            ServerData.playerData[i].direction = new Random().nextInt(360);

            ServerData.MapObject player = new ServerData.MapObject();
            player.x = point.x;
            player.y = point.y;
            player.direction = ServerData.playerData[i].direction;
            player.textureHandler = TextureManager.getTexture("sys_tank");
            player.textureName = "sys_tank";

            ServerData.map.add(player);
        }
    }

    //Отправка карты всем подключенным игрокам
    private void sendMap(){
        //Отправка всем сообщения о начале рестарта
        GameServer.sendAll(8, "");

        //Ставим всем игркокам флаг о том, что они ещё не готовы к старту
        ServerData.battle = false;
        for(int i=0; i<ServerData.playerData.length; i++){
            ServerData.playerData[i].gameReady = false;
        }

        //Отправляем всем игрокам карту
        GameServer.sendAll(3, ServerData.widthMap + " " + ServerData.heightMap + " " + ServerData.background);
        for (ServerData.MapObject mapObject : ServerData.map){
            if (mapObject.textureName.equals("sys_tank")) continue;
            GameServer.sendAll(9, mapObject.x + " " + mapObject.y + " " + mapObject.direction + " " + mapObject.textureName);
        }

        //Отправляем всем игркоам их позиции
        for (int i = 0; i<ServerData.playerData.length; i++){
            GameServer.send(i, 5, ServerData.playerData[i].x + " " + ServerData.playerData[i].y + " " + ServerData.playerData[i].direction);
        }

        //Говорим всем, что отправка карты завершена
        GameServer.sendAll(10, "");

        //Ждем, пока всем игроки будут готовы (в отдельном потоке)
        new Thread(this).start();
    }

    @Override
    public void run(){
        //Все ли игроки готовы к старту игры
        boolean allReady;

        do {
            allReady = true;

            for (int i=0; i<ServerData.playerData.length; i++){
                if (!ServerData.playerData[i].gameReady){
                    allReady = false;
                    break;
                }
            }

            //Отдаем управление другому потоку
            try { Thread.sleep(0, 1); } catch (InterruptedException e){}
        } while (!allReady);

        //Говорим всем, что игра началась
        ServerData.battle = true;
        GameServer.sendAll(11, "");
    }
}
