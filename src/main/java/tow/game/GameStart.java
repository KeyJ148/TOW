package tow.game;

import tow.engine.Loader;
import tow.game.client.Game;
import tow.game.client.NetGameRead;
import tow.game.server.NetServerRead;
import tow.game.server.Server;

public class GameStart {

    public static void main(String[] args) {
        Loader.start(new Game(), new NetGameRead(), new Server(), new NetServerRead());
    }
}
