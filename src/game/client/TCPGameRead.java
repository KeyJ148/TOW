package game.client;

import engine.Global;
import engine.image.Camera;
import engine.image.TextureHandler;
import engine.image.TextureManager;
import engine.io.Logger;
import engine.map.Border;
import engine.map.Room;
import engine.net.client.Message;
import engine.obj.Obj;
import game.client.map.MapObject;
import game.client.map.Box;
import game.client.map.Wall;
import game.client.person.player.Player;
import game.client.person.enemy.Enemy;
import game.client.person.enemy.EnemyBullet;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class TCPGameRead{

	public static void read(Message message){
        switch (message.type){
            case 1: take1(message.data); break;
            case 2: take2(message.data); break;
            case 3: take3(message.data); break;
            case 4: take4(message.data); break;
            case 5: take5(message.data); break;
            case 7: take7(message.data); break;
            case 8: take8(message.data); break;
            case 9: take9(message.data); break;
            case 10: take10(message.data); break;
            case 11: take11(message.data); break;
            case 12: take12(message.data); break;
            case 13: take13(message.data); break;
            case 14: take14(message.data); break;
            case 15: take15(message.data); break;
            case 18: take18(message.data); break;
            case 19: take19(message.data); break;
            case 20: take20(message.data); break;
            case 21: take21(message.data); break;
            case 22: take22(message.data); break;

            //Engine: Различные действия с уникальными индексами
		}
	}

    public static void take1(String str){
        //Engine: Сервер вернул пинг
        Global.pingCheck.takePing();
    }

	//координаты игрока - (int x, int y, int direction, int speed, int directionDraw, int id)
	public static void take2(String str){
        int x = Integer.parseInt(str.split(" ")[0]);
        int y = Integer.parseInt(str.split(" ")[1]);
        int direction = Integer.parseInt(str.split(" ")[2]);
        int directionGun = Integer.parseInt(str.split(" ")[3]);
        int speed = Integer.parseInt(str.split(" ")[4]);
        double moveDirection = Double.parseDouble(str.split(" ")[5]);
        int animSpeed = Integer.parseInt(str.split(" ")[6]);
        int enemyId = Integer.parseInt(str.split(" ")[7]);

        ClientData.enemy.get(enemyId).setData(x, y, direction, directionGun, speed, moveDirection, animSpeed);
	}

	//данные о карте - (int width, int height, String background)
    public static void take3(String str){
	    int width = Integer.parseInt(str.split(" ")[0]);
	    int height = Integer.parseInt(str.split(" ")[1]);
	    String background = str.split(" ")[2];

	    Global.room = new Room(width, height);
	    Global.room.background = TextureManager.getTexture(background);
        Border.createAll(Global.room);
    }

    //старт сервера - (int peopleMax, int myIdFromServer)
	public static void take4(String str){
		ClientData.peopleMax = Integer.parseInt(str.split(" ")[0]);
		ClientData.myIdFromServer = Integer.parseInt(str.split(" ")[1]);

		//Заполнение таблицы врагов (в соответствтие с id)
        for (int id = 0; id < ClientData.peopleMax; id++) {
            if (id != ClientData.myIdFromServer) ClientData.enemy.put(id, new Enemy(id));
        }

		//В ответ отправляем свои данные (цвет и ник)
        String message = ClientData.color.getRed()
                 + " " + ClientData.color.getGreen()
                 + " " + ClientData.color.getBlue()
                 + " " + ClientData.name;
        Global.tcpControl.send(17, message);

        //Запускаем пингатор
        Global.pingCheck.start();
	}

	//позиция танка игрока при генерации карты (int x, int y, int direction)
	public static void take5(String str){
        int x = Integer.parseInt(str.split(" ")[0]);
        int y = Integer.parseInt(str.split(" ")[1]);
        int direction = Integer.parseInt(str.split(" ")[2]);

	    ClientData.player = new Player(x, y, direction);
	    Global.room.objAdd(ClientData.player);
        Camera.setFollowObject(ClientData.player);
    }

    //ящик создан - (int x, int y, int type, int idBox)
    public static void take7(String str){
        int x = Integer.parseInt(str.split(" ")[0]);
        int y = Integer.parseInt(str.split(" ")[1]);
        int type = Integer.parseInt(str.split(" ")[2]);
        int idBox = Integer.parseInt(str.split(" ")[3]);

        Global.room.objAdd(new Box(x, y, type, idBox));
    }

	//начало рестарта
    public static void take8(String str){
        ClientData.battle = false;
        Global.room.destroy();
        Camera.deleteFollowObject();

        ClientData.mapObjects = new Vector<>();
        ClientData.enemyBullet = new ArrayList<>();
        for (Map.Entry<Integer, Enemy> entry: ClientData.enemy.entrySet()) {
            entry.getValue().armor = null;
            entry.getValue().gun = null;
            entry.getValue().camera = null;
            entry.getValue().alive = true;
        }
    }

    //объект карты - (int x, int y, int directiorn, String texture)
    public static void take9(String str){
        int x = Integer.parseInt(str.split(" ")[0]);
        int y = Integer.parseInt(str.split(" ")[1]);
        int direction = Integer.parseInt(str.split(" ")[2]);
        String texture = str.split(" ")[3];
        int mid = Integer.parseInt(str.split(" ")[4]);

        TextureHandler textureHandler = TextureManager.getTexture(texture);

        MapObject newObject;
        switch (textureHandler.type){
            case "home": newObject = new Wall(x, y, direction, textureHandler, mid); break;
            case "tree": newObject = new Wall(x, y, direction, textureHandler, mid); break;
            case "road": newObject = new MapObject(x, y, direction, textureHandler, mid); break;
            default:
                newObject = new MapObject(x, y, direction, textureHandler, mid);
                Logger.println("Not valid type for generate map: " + textureHandler.type, Logger.Type.ERROR);
            break;
        }

        Global.room.objAdd(newObject);
        ClientData.mapObjects.add(mid, newObject);
    }

    //конец отправки карты
    public static void take10(String str){
        Global.tcpControl.send(6, "");
    }

    //старт игры (рестарт полностью завершен)
    public static void take11(String str){
        ClientData.battle = true;
    }

    //я умер - (int id)
    public static void take12(String str){
        int id = Integer.parseInt(str.split(" ")[0]);

        ClientData.enemy.get(id).exploded(); //Взорвался
    }

    //я выстрелил - (int x, int y, double direction, double speed, String texture, long idNet, int id)
    public static void take13(String str){
        int x = Integer.parseInt(str.split(" ")[0]);
        int y = Integer.parseInt(str.split(" ")[1]);
        double direction = Double.parseDouble(str.split(" ")[2]);
        double speed = Double.parseDouble(str.split(" ")[3]);
        String texture = str.split(" ")[4];
        long idNet = Long.parseLong(str.split(" ")[5]);
        int idEmeny = Integer.parseInt(str.split(" ")[6]);

        EnemyBullet enemyBullet = new EnemyBullet(x, y, speed, direction, TextureManager.getTexture(texture), idEmeny, idNet);
        ClientData.enemyBullet.add(enemyBullet);
        Global.room.objAdd(enemyBullet);
    }

    //я нанёс урон игроку enemyId (double damage, int enemyId)
    public static void take14(String str){
        double damage = Double.parseDouble(str.split(" ")[0]);
        int idSuffer = Integer.parseInt(str.split(" ")[1]);

        if (idSuffer == ClientData.myIdFromServer){
            ClientData.player.armor.hp -= damage;
        }
    }

    //моя пуля уничтожилась (long idNet, int id)
    public static void take15(String str){
        long idNet = Long.parseLong(str.split(" ")[0]);
        int idEnemy = Integer.parseInt(str.split(" ")[1]);

        for(EnemyBullet bullet : ClientData.enemyBullet){
            if (bullet.idEnemy == idEnemy && bullet.idNet == idNet){
                bullet.destroy();
                break;
            }
        }
    }

    //сервер отправил данные об игроке id - (int red, int green, int blue, String name, int id)
    public static void take18(String str){
        int red = Integer.parseInt(str.split(" ")[0]);
        int green = Integer.parseInt(str.split(" ")[1]);
        int blue = Integer.parseInt(str.split(" ")[2]);
        String name = str.split(" ")[3];
        int id = Integer.parseInt(str.split(" ")[4]);

        if (ClientData.enemy.get(id).valid) return;

        ClientData.enemy.get(id).name = name;
        ClientData.enemy.get(id).color = new Color(red, green, blue);
        ClientData.enemy.get(id).setColor(ClientData.enemy.get(id).color);
        ClientData.enemy.get(id).valid = true;
    }

    //я сменил броню - (String armorName, int id)
    public static void take19(String str){
        String armorName = str.split(" ")[0];
        int enemyId = Integer.parseInt(str.split(" ")[1]);

        ClientData.enemy.get(enemyId).newArmor(armorName);
    }

    //я сменил оружие - (String gunName, int id)
    public static void take20(String str){
        String gunName = str.split(" ")[0];
        int enemyId = Integer.parseInt(str.split(" ")[1]);

        ClientData.enemy.get(enemyId).newGun(gunName);
    }

    //я подобрал ящик - (int idBox)
    public static void take21(String str){
        int idBox = Integer.parseInt(str.split(" ")[0]);
        for(Obj obj : Global.room.objects){
            if (obj != null && obj instanceof Box && ((Box) obj).idBox == idBox){
                obj.destroy();
            }
        }
    }

    //объект карты уничтожен бронёй - (int mid)
    public static void take22(String str){
        int mid = Integer.parseInt(str.split(" ")[0]);
        if (mid < ClientData.mapObjects.size()){
            ((Wall) ClientData.mapObjects.get(mid)).destroyByArmor();
            ClientData.mapObjects.set(mid, null);
        }
    }

}
