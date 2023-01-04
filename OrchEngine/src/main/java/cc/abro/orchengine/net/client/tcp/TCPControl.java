package cc.abro.orchengine.net.client.tcp;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.exceptions.EngineException;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.net.client.NetControl;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

@Log4j2
@EngineService
public class TCPControl extends NetControl {

    private DataInputStream in;
    private DataOutputStream out;

    public void connect(String ip, int port) {
        try {
            Socket socket = new Socket(InetAddress.getByName(ip), port);
			/*socket.setTcpNoDelay(SettingsStorage.NETWORK.TCP_NODELAY);
			socket.setKeepAlive(SettingsStorage.NETWORK.KEEP_ALIVE);
			socket.setSendBufferSize(SettingsStorage.NETWORK.SEND_BUF_SIZE);
			socket.setReceiveBufferSize(SettingsStorage.NETWORK.RECEIVE_BUF_SIZE);
			socket.setPerformancePreferences(SettingsStorage.NETWORK.PREFERENCE_CON_TIME,
					SettingsStorage.NETWORK.PREFERENCE_LATENCY,
					SettingsStorage.NETWORK.PREFERENCE_BANDWIDTH);
			socket.setTrafficClass(SettingsStorage.NETWORK.TRAFFIC_CLASS);*/
            socket.setTcpNoDelay(true);
            socket.setKeepAlive(true);
            socket.setSendBufferSize(4096);
            socket.setReceiveBufferSize(4096);
            socket.setPerformancePreferences(0, 2, 1);
            socket.setTrafficClass(24);

            InputStream inS = socket.getInputStream();
            OutputStream outS = socket.getOutputStream();
            DataInputStream in = new DataInputStream(inS);
            DataOutputStream out = new DataOutputStream(outS);

            this.in = in;
            this.out = out;
        } catch (IOException e) {
            log.warn("Connection failed (TCP)");
            throw new ConnectException(e);
        }
    }

    @Override
    public void send(int type, String str) {
        try {
            if (out != null) {
                out.writeUTF(type + " " + str);
                out.flush();

                analyzeSend(str.length() * 2);
            }
        } catch (IOException e) {
            log.warn("Connection lost (TCP send)");
            throw new EngineException(e);
        }
    }

    @Override
    public String read() {
        try {
            String str = in.readUTF();
            analyzeRead(str.length() * 2);

            return str;
        } catch (IOException e) {
            log.warn("Connection lost (TCP read)");
            throw new EngineException(e);
        }
    }
}
