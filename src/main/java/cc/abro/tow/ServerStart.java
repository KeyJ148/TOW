package cc.abro.tow;

import cc.abro.orchengine.net.server.GameServer;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;

public class ServerStart {

    public static void main(String args[]) {
        GameServer.initSettings(args, new Server(), new NetServerRead());
        GameServer.waitConnect();
    }
}
