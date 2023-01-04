package cc.abro.orchengine.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectFactory {

    public static Connect createConnect(ServerSocket serverSocket, DatagramSocket socketUDP, int id) throws IOException {
        Socket socketTCP = serverSocket.accept();
        /*socketTCP.setTcpNoDelay(SettingsStorage.NETWORK.TCP_NODELAY);
        socketTCP.setKeepAlive(SettingsStorage.NETWORK.KEEP_ALIVE);
        socketTCP.setSendBufferSize(SettingsStorage.NETWORK.SEND_BUF_SIZE);
        socketTCP.setReceiveBufferSize(SettingsStorage.NETWORK.RECEIVE_BUF_SIZE);
        socketTCP.setPerformancePreferences(SettingsStorage.NETWORK.PREFERENCE_CON_TIME,
                SettingsStorage.NETWORK.PREFERENCE_LATENCY,
                SettingsStorage.NETWORK.PREFERENCE_BANDWIDTH);
        socketTCP.setTrafficClass(SettingsStorage.NETWORK.TRAFFIC_CLASS);*/
        socketTCP.setTcpNoDelay(true);
        socketTCP.setKeepAlive(true);
        socketTCP.setSendBufferSize(4096);
        socketTCP.setReceiveBufferSize(4096);
        socketTCP.setPerformancePreferences(0, 2, 1);
        socketTCP.setTrafficClass(24);

        int size = 2048;//SettingsStorage.NETWORK.UDP_READ_BYTE_ARRAY_LEN;
        while (true) {
            DatagramPacket connectionPacket = new DatagramPacket(new byte[size], size);
            socketUDP.receive(connectionPacket);

            String data = new String(connectionPacket.getData());
            if (connectionPacket.getAddress().equals(socketTCP.getInetAddress()) &&
                    Integer.parseInt(data.split(" ")[0]) == 0) {

                Connect connect = new Connect(id, socketTCP, connectionPacket.getPort());
                connect.out.writeUTF("0 ");
                return connect;
            }
        }

    }

}
