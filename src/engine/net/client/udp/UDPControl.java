package engine.net.client.udp;

import engine.Loader;
import engine.io.Logger;
import engine.net.NetTools;
import engine.net.client.NetControl;
import engine.setting.SettingStorage;

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
            socket.setSendBufferSize(SettingStorage.Net.SEND_BUF_SIZE);
            socket.setReceiveBufferSize(SettingStorage.Net.RECEIVE_BUF_SIZE);
            socket.setTrafficClass(SettingStorage.Net.TRAFFIC_CLASS);
        } catch (IOException e){
            Logger.println("Connection failed (UDP)", Logger.Type.ERROR);
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
            Logger.println("Connection lost (UDP send)", Logger.Type.ERROR);
            Loader.exit();
        }
    }

    @Override
    public String read() {
        int size = SettingStorage.Net.UDP_READ_BYTE_ARRAY_LEN;

        try{
            DatagramPacket packet = new DatagramPacket(new byte[size], size);
            socket.receive(packet);

            byte[] data = NetTools.clearByteData(packet.getData());
            analyzeRead(data.length);
            return new String(data);
        } catch (IOException e){
            Logger.println("Connection lost (TCP read)", Logger.Type.ERROR);
            Loader.exit();
            return null;
        }
    }
}
