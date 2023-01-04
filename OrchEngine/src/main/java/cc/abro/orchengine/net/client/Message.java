package cc.abro.orchengine.net.client;

public class Message {

    public int type;
    public String data;
    public long timeReceipt;

    public Message(int type, String data, long timeReceipt) {
        this.type = type;
        this.data = data;
        this.timeReceipt = timeReceipt;
    }
}
