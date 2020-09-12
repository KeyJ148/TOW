package tech.abro.orchengine.net.client;

import tech.abro.orchengine.Global;

public class Connector extends Thread{

    public boolean connectSuccess = false;

    public void connect(String ip, int port){
        Global.tcpControl.connect(ip, port);
        Global.udpControl.connect(ip, port);

        start();
        do {
            Global.udpControl.send(0, "");
            try{ Thread.sleep(50); } catch (InterruptedException e){}
        } while (!connectSuccess);

        Global.tcpRead.start();
        Global.udpRead.start();
    }

    @Override
    public void run(){
        int type;
        do {
            String str = Global.tcpControl.read();
            type = Integer.parseInt(str.split(" ")[0]);
        } while (type != 0);

        connectSuccess = true;
    }
}
