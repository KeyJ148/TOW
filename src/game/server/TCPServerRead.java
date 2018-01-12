package game.server;

import engine.net.server.GameServer;
import game.server.data.ServerData;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class TCPServerRead {

    public void read(int id, int type, String str){
        //Engine: вызывается каждый раз при получение сервером сообщения
        switch (type){
            case 1: take1(id, str); break;//Engine: Клиент пингует сервер
            case 2: take2(id, str); break;
            case 6: take6(id, str); break;
            case 12: take12(id, str); break;
            case 13: take13(id, str); break;
            case 14: take14(id, str); break;
            case 15: take15(id, str); break;
            case 16: take16(id, str); break;
            case 17: take17(id, str); break;
            case 19: take19(id, str); break;
            case 20: take20(id, str); break;
            case 21: take21(id, str); break;
            case 22: take22(id, str); break;
            //Engine: Различные действия с уникальными индексами
        }
    }

    public void take1(int id, String str){
        GameServer.send(id, 1, "");
    }

    public void take2(int id, String str){
        ServerData.playerData[id].x = Integer.parseInt(str.split(" ")[0]);
        ServerData.playerData[id].y = Integer.parseInt(str.split(" ")[1]);
        GameServer.sendAllExceptId(id, 2, str + " " + id);
    }

    public void take6(int id, String str){
        ServerData.playerData[id].gameReady = true;
    }

    public void take12(int id, String str){
        GameServer.sendAllExceptId(id, 12, String.valueOf(id));
        ServerData.deadPlayerCount++;

        //Если кол-во живых игроков меньше двух и при этом кто-то умер (т.е. игра не одиночная)
        //Или если кол-во живых игроков 0 (даже при одиночной игре)
        if ((GameServer.peopleMax - ServerData.deadPlayerCount < 2 && ServerData.deadPlayerCount > 0)
                || (GameServer.peopleMax - ServerData.deadPlayerCount == 0)){

            //То через некоторое время перезапускам сервер
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    GameServer.server.startNewGame();
                }
            };

            timer.schedule(task, Server.DELAY_MAP_CHANGE);
        }
    }

    public void take13(int id, String str){
        GameServer.sendAllExceptId(id, 13, str + " " + id);
    }

    public void take14(int id, String str){
        GameServer.sendAllExceptId(id, 14, str);
    }

    public void take15(int id, String str){
        GameServer.sendAllExceptId(id, 15, str + " " + id);
    }

    public void take16(int id, String str){
        int answerDataId = Integer.parseInt(str.split(" ")[0]);
        if (ServerData.playerData[answerDataId].color == null || ServerData.playerData[answerDataId].name == null) return;

        String s = ServerData.playerData[answerDataId].color.getRed()
           + " " + ServerData.playerData[answerDataId].color.getGreen()
           + " " + ServerData.playerData[answerDataId].color.getBlue()
           + " " + ServerData.playerData[answerDataId].name
           + " " + answerDataId;

        GameServer.send(id, 18, s);
    }

    public void take17(int id, String str){
        int red = Integer.parseInt(str.split(" ")[0]);
        int green = Integer.parseInt(str.split(" ")[1]);
        int blue = Integer.parseInt(str.split(" ")[2]);
        String name = str.split(" ")[3];

        ServerData.playerData[id].color = new Color(red, green, blue);
        ServerData.playerData[id].name = name;
    }

    public void take19(int id, String str){
        GameServer.sendAllExceptId(id, 19, str + " " + id);
    }

    public void take20(int id, String str){
        GameServer.sendAllExceptId(id, 20, str + " " + id);
    }

    public void take21(int id, String str){
        GameServer.sendAllExceptId(id, 21, str);
    }

    public void take22(int id, String str){
        GameServer.sendAllExceptId(id, 22, str);
    }

}
