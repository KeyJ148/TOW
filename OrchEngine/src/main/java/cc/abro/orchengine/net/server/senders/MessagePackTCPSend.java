package cc.abro.orchengine.net.server.senders;

import cc.abro.orchengine.net.server.Connect;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log4j2
public class MessagePackTCPSend extends Thread {

    private final Connect connect;
    private final BlockingQueue<String> messages = new LinkedBlockingQueue<>();

    public MessagePackTCPSend(Connect connect) {
        this.connect = connect;
        start();
    }

    public void send(String message) {
        messages.add(message);
    }

    @Override
    public void run() {
        while (true) {
            try {
                connect.out.writeUTF(messages.take());
                connect.out.flush();
            } catch (InterruptedException | IOException e) {
                log.warn("Send message failed (TCP)", e);
            }
        }
    }
}
