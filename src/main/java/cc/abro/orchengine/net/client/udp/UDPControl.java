package cc.abro.orchengine.net.client.udp;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.exceptions.EngineException;
import cc.abro.orchengine.net.NetTools;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.net.client.NetControl;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Log4j2
@EngineService
public class UDPControl extends NetControl {

    private DatagramSocket socket;
    private String ip;
    private int port;

    public void connect(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            socket = new DatagramSocket();
            /*socket.setSendBufferSize(SettingsStorage.NETWORK.SEND_BUF_SIZE);
            socket.setReceiveBufferSize(SettingsStorage.NETWORK.RECEIVE_BUF_SIZE);
            socket.setTrafficClass(SettingsStorage.NETWORK.TRAFFIC_CLASS);*/
            socket.setSendBufferSize(4096);
            socket.setReceiveBufferSize(4096);
            socket.setTrafficClass(24);
        } catch (IOException e) {
            log.warn("Connection failed (UDP)");
            throw new ConnectException(e);
        }
    }

    @Override
    public void send(int type, String str) {
        try {
            byte[] data = (type + " " + str).getBytes();
            InetAddress addr = InetAddress.getByName(ip);

            if (socket != null) {
                DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
                socket.send(packet);

                analyzeSend(data.length);
            }
        } catch (IOException e) {
            log.warn("Connection lost (UDP send)");
            throw new EngineException(e);
        }
    }

    @Override
    public String read() {
        int size = 2048;//SettingsStorage.NETWORK.UDP_READ_BYTE_ARRAY_LEN;

        try {
            DatagramPacket packet = new DatagramPacket(new byte[size], size);
            socket.receive(packet);

            byte[] data = NetTools.clearByteData(packet.getData());
            analyzeRead(data.length);
            return new String(data);
        } catch (IOException e) {
            log.warn("Connection lost (TCP read)");
            throw new EngineException(e);
        }
    }
}
