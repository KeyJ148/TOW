package tow.game;

import tow.engine3.net.server.GameServer;
import tow.game.server.NetServerRead;
import tow.game.server.Server;

public class ServerStart {

    public static void main(String args[]){
        GameServer.initSettings(args, new Server(), new NetServerRead());
        GameServer.waitConnect();
    }
}
