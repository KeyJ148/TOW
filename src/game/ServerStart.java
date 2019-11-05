package game;

import engine.net.server.GameServer;
import game.server.NetServerRead;
import game.server.Server;

public class ServerStart {

    public static void main(String args[]){
        GameServer.initSettings(args, new Server(), new NetServerRead());
        GameServer.waitConnect();
    }
}
