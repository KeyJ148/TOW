package cc.abro.orchengine.net.server;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log4j2
public class MessagePack {

    private final BlockingQueue<Message> messages;//Список сообщений
    public int id;

    public MessagePack(int id) {
        this.messages = new LinkedBlockingQueue<>();
        this.id = id;
    }

    public void add(String str, Message.InetType inetType) {
        if (str.length() > 0) {
            int type = Integer.parseInt(str.split(" ")[0]);
            String mes = str.substring(str.indexOf(" ") + 1);
            add(new Message(type, mes, id, inetType));
        }
    }

    public void add(Message message) {
        messages.add(message);
    }

    public Message get() {
        if (size() % 10 == 0) {
            log.trace("Messages detained: " + size() + " (id: " + id + ")");
        }

        return messages.poll();
    }

    public int size() {
        return messages.size();
    }

    public boolean haveMessage() {
        return !messages.isEmpty();
    }

    public static class Message {

        public enum InetType {TCP, UDP}

        public int type;
        public String text;
        public int authorId;
        public InetType inetType;

        public Message(int type, String text, int authorId, InetType inetType) {
            this.type = type;
            this.text = text;
            this.authorId = authorId;
            this.inetType = inetType;
        }
    }

}
