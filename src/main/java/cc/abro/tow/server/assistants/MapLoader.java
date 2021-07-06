package cc.abro.tow.server.assistants;

import cc.abro.orchengine.util.Vector2;
import cc.abro.orchengine.net.server.senders.ServerSendTCP;
import cc.abro.tow.client.map.specification.MapSpecificationLoader;
import cc.abro.tow.server.Server;
import cc.abro.tow.server.data.ServerData;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.Random;

@Log4j2
public class MapLoader implements Runnable {

    public void loadRandomMap(String allMapsPath) {
        loadMap(chooseRandomMap(allMapsPath));
    }

    public void loadMap(File map) {
        log.info("Loading map: " + map.getName());

        disableBattle();
        loadMapToMemory(map);
        genTanks();
        sendMap();
    }

    private void disableBattle() {
        //Отправка всем сообщения о начале рестарта
        ServerSendTCP.sendAll(8, "");

        //Ставим всем игркокам флаг о том, что они ещё не готовы к старту
        ServerData.battle = false;
        for (int i = 0; i < ServerData.playerData.length; i++) {
            ServerData.playerData[i].gameReady = false;
        }

    }

    //Выбор рандомной карты из папки с картами
    private File chooseRandomMap(String allMapsPath) {
        File[] allFiles = new File(allMapsPath).listFiles();

        String path;
        do {
            int randFileIndex = (int) Math.round(Math.random() * (allFiles.length - 1));
            String nameOfRandFile = allFiles[randFileIndex].getName();

            path = allMapsPath + nameOfRandFile.substring(0, nameOfRandFile.lastIndexOf('.')) + ".json";
        } while (!new File(path).exists()); //Если у файла расширение .json, то выходим из цикла

        return new File(path);
    }

    //Загрузка всех объектов карты в память сервера (ServerData)
    private void loadMapToMemory(File map) {
        ServerData.mapPath = map.getPath();
        ServerData.map = MapSpecificationLoader.getMapSpecification(map.getPath());
    }

    //Генерация танков на свободных позициях и сохранение результата в PlayerData
    private void genTanks() {
        int wTank = 30;
        int hTank = 60;

        for (int i = 0; i < ServerData.playerData.length; i++) {
            Vector2<Integer> point = Server.genObject(wTank, hTank);
            ServerData.playerData[i].x = point.x;
            ServerData.playerData[i].y = point.y;
            ServerData.playerData[i].direction = new Random().nextInt(360);
        }
    }

    //Отправка карты всем подключенным игрокам
    private void sendMap() {
        //Отправляем всем игрокам карту
        ServerSendTCP.sendAll(3, ServerData.mapPath);

        //Говорим всем, что отправка карты завершена
        ServerSendTCP.sendAll(10, "");

        //Отправляем всем игркоам их позиции
        for (int i = 0; i < ServerData.playerData.length; i++) {
            ServerSendTCP.send(i, 5, ServerData.playerData[i].x
                    + " " + ServerData.playerData[i].y
                    + " " + ServerData.playerData[i].direction);
        }

        //Ждем, пока всем игроки будут готовы (в отдельном потоке)
        new Thread(this).start();
    }

    @Override
    public void run() {
        //Все ли игроки готовы к старту игры
        boolean allReady;

        do {
            allReady = true;

            for (int i = 0; i < ServerData.playerData.length; i++) {
                if (!ServerData.playerData[i].gameReady) {
                    allReady = false;
                    break;
                }
            }

            //Отдаем управление другому потоку
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e) {
            }
        } while (!allReady);

        //Говорим всем, что игра началась
        ServerData.battle = true;
        ServerSendTCP.sendAll(11, "");
    }
}

