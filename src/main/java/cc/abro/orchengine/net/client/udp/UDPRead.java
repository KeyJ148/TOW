package cc.abro.orchengine.net.client.udp;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.init.interfaces.NetGameReadInterface;
import cc.abro.orchengine.net.client.Message;

import java.util.ArrayList;

@EngineService
public class UDPRead extends Thread {

    private volatile ArrayList<Message> messages = new ArrayList<>();

    private final NetGameReadInterface netGameRead;
    private final UDPControl udpControl;

    public UDPRead(NetGameReadInterface netGameRead, UDPControl udpControl) {
        this.netGameRead = netGameRead;
        this.udpControl = udpControl;

        setDaemon(true);
    }

    @Override
    public void run() {
        //Постоянный обмен данными на UDP
        String str;
        while (true) {
            str = udpControl.read();

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
                netGameRead.readUDP(message);
            }

            messages.clear();
        }
    }
}
