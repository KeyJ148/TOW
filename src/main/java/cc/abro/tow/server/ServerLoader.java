package cc.abro.tow.server;

import cc.abro.tow.ServerStart;

public class ServerLoader extends Thread {

    //Параметры, которые необходимо передать от клиента серверу
    //null, если этот параметр не передавался
    public static String mapPath = null;
    public static Runnable startServerListener = null;

    //Параметры передаваемые серверу при запуске
    private int port;
    private int peopleMax;
    private boolean maxPower;

    public ServerLoader(int port, int peopleMax, boolean maxPower) {
        this.port = port;
        this.peopleMax = peopleMax;
        this.maxPower = maxPower;

        start();
    }

    @Override
    public void run() {
        String[] args = new String[3];
        args[0] = String.valueOf(port);
        args[1] = String.valueOf(peopleMax);
        args[2] = String.valueOf(maxPower);

        ServerStart.main(args);
    }

}
