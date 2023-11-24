package cc.abro.orchengine.net.client;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.net.client.tcp.TCPRead;
import cc.abro.orchengine.net.client.udp.UDPControl;
import cc.abro.orchengine.net.client.udp.UDPRead;

public class Connector extends Thread {

    public boolean connectSuccess = false;

    private final TCPRead tcpRead;
    private final UDPRead udpRead;
    private final TCPControl tcpControl;
    private final UDPControl udpControl;

    public Connector() {
        this.tcpRead = Context.getService(TCPRead.class);
        this.udpRead = Context.getService(UDPRead.class);
        this.tcpControl = Context.getService(TCPControl.class);
        this.udpControl = Context.getService(UDPControl.class);
    }

    public void connect(String ip, int port) {
        tcpControl.connect(ip, port);
        udpControl.connect(ip, port);

        start();
        do {
            udpControl.send(0, "");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        } while (!connectSuccess);

        tcpRead.start();
        udpRead.start();
    }

    @Override
    public void run() {
        int type;
        do {
            String str = tcpControl.read();
            type = Integer.parseInt(str.split(" ")[0]);
        } while (type != 0);

        connectSuccess = true;
    }
}
