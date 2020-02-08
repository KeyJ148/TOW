package tow.game;

import tow.engine.Loader;
import tow.game.client.Game;
import tow.game.client.NetGameRead;
import tow.game.client.Storage;
import tow.game.server.NetServerRead;
import tow.game.server.Server;

public class GameStart {

    public static void main(String[] args) {

        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());

        Loader.start(new Game(), new NetGameRead(), new Storage(), new Server(), new NetServerRead());
    }
}
