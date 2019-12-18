package tow.game.server;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.image.TextureManager;
import tow.engine.implementation.ServerInterface;
import tow.engine.net.server.GameServer;
import tow.engine.net.server.senders.ServerSendTCP;
import tow.game.server.assistants.BoxCreator;
import tow.game.server.assistants.MapLoader;
import tow.game.server.data.PlayerData;
import tow.game.server.data.ServerData;

public class Server implements ServerInterface {

    public static final String PATH_MAP = "map";
    public static final long DELAY_MAP_CHANGE = 2000; //Задержка перед рестартом сервера (милисекунды)

    @Override
    public void init(){
        //Engine: Инициализация сервера сразу после создания сокета (Цикл подключения ещё не запущен, но сокет существует)
        if (ServerLoader.startServerListener != null){
            new Thread(() -> ServerLoader.startServerListener.serverStart()).start();
        }
    }

    @Override
    public void startProcessingData(){
        //Engine: Инициализация сервера перед запуском главного цикла, после подключения всех игроков
        ServerData.playerData = new PlayerData[GameServer.peopleMax];
        for (int i=0; i<ServerData.playerData.length; i++){
            ServerData.playerData[i] = new PlayerData();
        }

        //Отправка данных при страте сервера (кол-во игроков)
        for (int id = 0; id < GameServer.peopleMax; id++) {
            ServerSendTCP.send(id, 4, GameServer.peopleMax + " " + id);
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
            new MapLoader().loadMap(Global.getFile(ServerLoader.mapPath));
        } else {
            new MapLoader().loadRandomMap(PATH_MAP);
        }

        //Запускаем поток создания ящиков
        ServerData.boxCreator = new BoxCreator();
    }

    @Override
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
