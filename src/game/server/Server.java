package game.server;

import engine.Vector2;
import engine.image.TextureManager;
import engine.net.server.GameServer;
import game.server.assistants.BoxCreator;
import game.server.assistants.MapLoader;
import game.server.data.PlayerData;
import game.server.data.ServerData;

import java.io.File;

public class Server {

    public static final String PATH_MAP = "res/map";

    public void init(){
        //Engine: Инициализация сервера сразу после создания сокета (Цикл подключения ещё не запущен, но сокет существует)
        if (ServerLoader.startServerListener != null) ServerLoader.startServerListener.serverStart();
    }

    public void startProcessingData(){
        //Engine: Инициализация сервера перед запуском главного цикла, после подключения всех игроков
        ServerData.playerData = new PlayerData[GameServer.peopleMax];
        for (int i=0; i<ServerData.playerData.length; i++){
            ServerData.playerData[i] = new PlayerData();
        }

        //Отправка данных при страте сервера (кол-во игроков)
        for (int id = 0; id < GameServer.peopleMax; id++) {
            GameServer.send(id, 4, GameServer.peopleMax + " " + id);
        }

        startNewGame();
    }

    public void startNewGame(){
        //Приостанавливаем текущую игру
        ServerData.battle = false;
        for (int i=0; i<ServerData.playerData.length; i++){
            ServerData.playerData[i].gameReady = false;
        }
        ServerData.deadPlayerCount = 0;

        //Запускаем новую карту
        if (ServerLoader.mapPath != null) {
            new MapLoader().loadMap(new File(ServerLoader.mapPath));
        } else {
            new MapLoader().loadRandomMap(PATH_MAP);
        }

        //Запускаем поток создания ящиков
        ServerData.boxCreator = new BoxCreator();
    }

    public void update(long delta){
        //Engine: Выполняется каждый степ перед обработкой сообщений
        ServerData.boxCreator.update(delta);
    }

    public static Vector2<Integer> genObject(int width, int height){
        double size = Math.sqrt(width*width + height*height)/2;
        boolean collision;
        int xRand, yRand, x, y, w, h;
        double disHome, disPointToHome, dxRand, dyRand;

        //Цикл генерации позиции
        do{
            collision = false;
            dxRand = Math.random()*(ServerData.widthMap-200)+100; //Предполагаемая позиция объекта
            dyRand = Math.random()*(ServerData.heightMap-200)+100; //Если генерить сразу в инт - ошибка
            xRand = (int) dxRand;
            yRand = (int) dyRand;

            //Перебираем все имеющиеся объекты
            //Если ни с одним не столкнулись - генерация успешна
            for(ServerData.MapObject mapObject : ServerData.map){
                if ( TextureManager.getTexture(mapObject.textureName).type != "road"){

                    x = mapObject.x;//Коры объекта
                    y = mapObject.y;
                    w = mapObject.textureHandler.getWidth();//Размеры объекта
                    h = mapObject.textureHandler.getHeight();
                    disHome = Math.sqrt(w*w + h*h)/2;
                    disPointToHome = Math.sqrt((x-xRand)*(x-xRand)+(y-yRand)*(y-yRand));

                    if ((disHome+size+30) > (disPointToHome)){
                        collision = true;
                        break;
                    }
                }
            }
        } while(collision);

        return new Vector2<>(xRand, yRand);
    }
}
