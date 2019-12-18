package tow.game;

import tow.engine.Loader;
import tow.game.client.Game;
import tow.game.client.NetGameRead;
import tow.game.client.Storage;
import tow.game.server.NetServerRead;
import tow.game.server.Server;

import static tow.engine.Global.engine;

public class GameStart {

    public static void main(String[] args) {
        Loader.start(new Game(), new NetGameRead(), new Storage(), new Server(), new NetServerRead());
    }
}
