package cc.abro.tow;

import cc.abro.orchengine.Loader;
import cc.abro.tow.client.Game;
import cc.abro.tow.client.NetGameRead;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;

public class GameStart {

    public static void main(String[] args) {
        Loader.start(new Game(), new NetGameRead(), new Server(), new NetServerRead());
    }
}
