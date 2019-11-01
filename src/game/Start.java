package game;

import engine.Loader;
import game.client.Game;
import game.client.NetGameRead;
import game.client.Storage;
import game.server.NetServerRead;
import game.server.Server;

public class Start {

    public static void main(String[] args) {
        Loader.start(new Game(), new NetGameRead(), new Storage(), new Server(), new NetServerRead());
    }
}
