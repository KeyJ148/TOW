package engine.net.server;

import engine.setting.SettingStorage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectFactory {

    public static Connect createConnect(ServerSocket serverSocket, DatagramSocket socketUDP, int id) throws IOException{
        Socket socketTCP = serverSocket.accept();
        socketTCP.setTcpNoDelay(SettingStorage.Net.TCP_NODELAY);
        socketTCP.setKeepAlive(SettingStorage.Net.KEEP_ALIVE);
        socketTCP.setSendBufferSize(SettingStorage.Net.SEND_BUF_SIZE);
        socketTCP.setReceiveBufferSize(SettingStorage.Net.RECEIVE_BUF_SIZE);
        socketTCP.setPerformancePreferences(SettingStorage.Net.PREFERENCE_CON_TIME, SettingStorage.Net.PREFERENCE_LATENCY, SettingStorage.Net.PREFERENCE_BANDWIDTH);
        socketTCP.setTrafficClass(SettingStorage.Net.TRAFFIC_CLASS);

        int size = SettingStorage.Net.UDP_READ_BYTE_ARRAY_LEN;
        while (true){
            DatagramPacket connectionPacket = new DatagramPacket(new byte[size], size);
            socketUDP.receive(connectionPacket);

            String data = new String(connectionPacket.getData());
            if (connectionPacket.getAddress().equals(socketTCP.getInetAddress()) &&
                    Integer.parseInt(data.split(" ")[0]) == 0){

                Connect connect = new Connect(id, socketTCP, connectionPacket.getPort());
                connect.out.writeUTF("0 ");
                return connect;
            }
        }

    }

}
