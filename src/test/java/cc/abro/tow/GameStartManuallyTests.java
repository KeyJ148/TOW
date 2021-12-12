package cc.abro.tow;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.OrchEngine;
import cc.abro.orchengine.net.client.Connector;
import cc.abro.orchengine.profiles.Profile;
import cc.abro.orchengine.profiles.Profiles;
import cc.abro.tow.client.NetGameRead;
import cc.abro.tow.server.NetServerRead;
import cc.abro.tow.server.Server;
import cc.abro.tow.server.ServerLoader;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GameStartManuallyTests extends GameStartTests {

    private static final String DEFAULT_IP = "127.0.0.1";
    private static final int DEFAULT_PORT = 25566;

    @BeforeAll
    public static void setUpAll() {
        Assumptions.assumeTrue(Profiles.getActiveProfile() == Profile.LOCAL);
    }

    @Test
    public void gameStartAndCreateServerTestManually() {
        Manager.addService(createStartServerAfterStartGameService(1));
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndCreateServer2PlayerTestManually() {
        Manager.addService(createStartServerAfterStartGameService(2));
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndConnectToLocalhostTestManually() {
        Manager.addService(createConnectAfterStartGameService());
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    @Test
    public void gameStartAndConnectToLocalhostTestManually2() {
        Manager.addService(createConnectAfterStartGameService());
        OrchEngine.start(GameProxyService.class, NetGameRead.class, Server.class, NetServerRead.class);
    }

    private GameAfterStartService createStartServerAfterStartGameService(int peopleMax){
        return createStartServerAfterStartGameService(DEFAULT_PORT, peopleMax);
    }

    private GameAfterStartService createStartServerAfterStartGameService(int port, int peopleMax){
        ServerLoader.startServerListener = new StartServerListenerImpl(port);
        return new GameAfterStartService(() -> new ServerLoader(port, peopleMax, false));
    }

    private GameAfterStartService createConnectAfterStartGameService(){
        return createConnectAfterStartGameService(DEFAULT_IP, DEFAULT_PORT);
    }

    private GameAfterStartService createConnectAfterStartGameService(String ip, int port){
        return new GameAfterStartService(() -> Manager.createBean(Connector.class).connect(ip, port));
    }

}
