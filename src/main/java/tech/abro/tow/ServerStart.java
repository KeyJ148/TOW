package tech.abro.tow;

import tech.abro.orchengine.net.server.GameServer;
import tech.abro.tow.server.NetServerRead;
import tech.abro.tow.server.Server;

public class ServerStart {

    public static void main(String args[]){
        GameServer.initSettings(args, new Server(), new NetServerRead());
        GameServer.waitConnect();
    }
}
