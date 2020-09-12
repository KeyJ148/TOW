package tech.abro.tow;

import tech.abro.orchengine.Loader;
import tech.abro.tow.client.Game;
import tech.abro.tow.client.NetGameRead;
import tech.abro.tow.server.NetServerRead;
import tech.abro.tow.server.Server;

public class GameStart {

    public static void main(String[] args) {
        Loader.start(new Game(), new NetGameRead(), new Server(), new NetServerRead());
    }
}
