package tech.abro.orchengine.net.client.udp;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.Loader;
import tech.abro.orchengine.logger.Logger;
import tech.abro.orchengine.net.NetTools;
import tech.abro.orchengine.net.client.NetControl;
import tech.abro.orchengine.resources.settings.SettingsStorage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPControl extends NetControl {

    private DatagramSocket socket;
    private String ip;
    private int port;

    public void connect(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            socket = new DatagramSocket();
            socket.setSendBufferSize(SettingsStorage.NETWORK.SEND_BUF_SIZE);
            socket.setReceiveBufferSize(SettingsStorage.NETWORK.RECEIVE_BUF_SIZE);
            socket.setTrafficClass(SettingsStorage.NETWORK.TRAFFIC_CLASS);
        } catch (IOException e){
            Global.logger.println("Connection failed (UDP)", Logger.Type.ERROR);
            Loader.exit();
        }
    }

    @Override
    public void send(int type, String str) {
        try{
            byte[] data = (type + " " + str).getBytes();
            InetAddress addr = InetAddress.getByName(ip);

            if (socket != null) {
                DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
                socket.send(packet);

                analyzeSend(data.length);
            }
        } catch (IOException e){
            Global.logger.println("Connection lost (UDP send)", Logger.Type.ERROR);
            Loader.exit();
        }
    }

    @Override
    public String read() {
        int size = SettingsStorage.NETWORK.UDP_READ_BYTE_ARRAY_LEN;

        try{
            DatagramPacket packet = new DatagramPacket(new byte[size], size);
            socket.receive(packet);

            byte[] data = NetTools.clearByteData(packet.getData());
            analyzeRead(data.length);
            return new String(data);
        } catch (IOException e){
            Global.logger.println("Connection lost (TCP read)", Logger.Type.ERROR);
            Loader.exit();
            return null;
        }
    }
}
