package cc.abro.orchengine.net.client.tcp;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.init.interfaces.NetGameReadInterface;
import cc.abro.orchengine.net.client.Message;

import java.util.ArrayList;

@EngineService
public class TCPRead extends Thread {

    private volatile ArrayList<Message> messages = new ArrayList<>();

    private final NetGameReadInterface netGameRead;
    private final TCPControl tcpControl;

    public TCPRead(NetGameReadInterface netGameRead, TCPControl tcpControl) {
        this.netGameRead = netGameRead;
        this.tcpControl = tcpControl;

        setDaemon(true);
    }

    @Override
    public void run() {
        //Постоянный обмен данными на TCP
        String str;
        while (true) {
            str = tcpControl.read();

            int type = Integer.parseInt(str.split(" ")[0]);
            String data = str.substring(str.indexOf(" ") + 1);
            long timeReceipt = System.nanoTime();
            Message message = new Message(type, data, timeReceipt);

            synchronized (messages) {
                messages.add(message); //Ждём update и там обрабатываем
            }
        }
    }

    public void update() {
        synchronized (messages) {
            for (int i = 0; i < messages.size(); i++) {
                Message message = messages.get(i);
                netGameRead.readTCP(message);
            }

            messages.clear();
        }
    }
}
