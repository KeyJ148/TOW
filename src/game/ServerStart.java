package game;

import engine.net.server.GameServer;

public class ServerStart {

    public static void main(String args[]){
        GameServer.initSettings(args);
        GameServer.waitConnect();
    }
}
