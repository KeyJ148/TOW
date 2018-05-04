package game.server;

import engine.net.server.GameServer;
import engine.net.server.MessagePack;
import engine.net.server.senders.ServerSendTCP;
import engine.net.server.senders.ServerSendUDP;
import game.server.data.ServerData;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class NetServerRead {

    public void readTCP(MessagePack.Message message){
        //Engine: вызывается каждый раз при получение сервером сообщения по протоколу TCP
        switch (message.type){
            case 1: take1(message); break;//Engine: Клиент пингует сервер
            case 6: take6(message); break;
            case 12: take12(message); break;
            case 13: take13(message); break;
            case 14: take14(message); break;
            case 15: take15(message); break;
            case 16: take16(message); break;
            case 17: take17(message); break;
            case 19: take19(message); break;
            case 20: take20(message); break;
            case 21: take21(message); break;
            case 22: take22(message); break;
            case 23: take23(message); break;
            case 24: take24(message); break;
            case 25: take25(message); break;
            //Engine: Различные действия с уникальными индексами
        }
    }

    public void readUDP(MessagePack.Message message) {
        switch (message.type){
            case 2: take2(message); break;
        }
        //Engine: вызывается каждый раз при получение сервером сообщения по протоколу UDP
    }

    public void take1(MessagePack.Message message){
        ServerSendTCP.send(message.authorId, 1, "");
    }

    public void take2(MessagePack.Message message){
        ServerData.playerData[message.authorId].x = Integer.parseInt(message.text.split(" ")[0]);
        ServerData.playerData[message.authorId].y = Integer.parseInt(message.text.split(" ")[1]);
        ServerSendUDP.sendAllExceptId(message.authorId, 2, message.text + " " + message.authorId);
    }

    public void take6(MessagePack.Message message){
        ServerData.playerData[message.authorId].gameReady = true;
    }

    public void take12(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 12, String.valueOf(message.authorId));
        ServerData.deadPlayerCount++;

        //Если игра одиночка и все мерты
        //Или если игра сетевая и жив ровно один (если проверять < 1, то рестарт может быть дважды при одновременной смерти)
        if ((GameServer.peopleMax == 1 && ServerData.deadPlayerCount == 1) ||
                (GameServer.peopleMax > 1 && GameServer.peopleMax - ServerData.deadPlayerCount == 1)){

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

    public void take13(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 13, message.text + " " + message.authorId);
    }

    public void take14(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 14, message.text + " " + message.authorId);
    }

    public void take15(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 15, message.text + " " + message.authorId);
    }

    public void take16(MessagePack.Message message){
        int answerDataId = Integer.parseInt(message.text.split(" ")[0]);
        if (ServerData.playerData[answerDataId].color == null || ServerData.playerData[answerDataId].name == null) return;

        String s = ServerData.playerData[answerDataId].color.getRed()
                + " " + ServerData.playerData[answerDataId].color.getGreen()
                + " " + ServerData.playerData[answerDataId].color.getBlue()
                + " " + ServerData.playerData[answerDataId].name
                + " " + answerDataId;

        ServerSendTCP.send(message.authorId, 18, s);
    }

    public void take17(MessagePack.Message message){
        int red = Integer.parseInt(message.text.split(" ")[0]);
        int green = Integer.parseInt(message.text.split(" ")[1]);
        int blue = Integer.parseInt(message.text.split(" ")[2]);
        String name = message.text.split(" ")[3];

        ServerData.playerData[message.authorId].color = new Color(red, green, blue);
        ServerData.playerData[message.authorId].name = name;
    }

    public void take19(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 19, message.text + " " + message.authorId);
    }

    public void take20(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 20, message.text + " " + message.authorId);
    }

    public void take21(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 21, message.text);
    }

    public void take22(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 22, message.text);
    }

    public void take23(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 23, message.text);
    }

    public void take24(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 24, String.valueOf(message.authorId));
    }

    public void take25(MessagePack.Message message){
        ServerSendTCP.sendAllExceptId(message.authorId, 25, message.text);
    }
}
