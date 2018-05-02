package engine.net.client.tcp;

import engine.Loader;
import engine.io.Logger;
import engine.net.client.NetControl;
import engine.setting.SettingStorage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPControl extends NetControl {
	
	private DataInputStream in;
	private DataOutputStream out;

	public void connect(String ip, int port){
		try{
			Socket socket = new Socket(InetAddress.getByName(ip), port);
			socket.setTcpNoDelay(SettingStorage.Net.TCP_NODELAY);
			socket.setKeepAlive(SettingStorage.Net.KEEP_ALIVE);
			socket.setSendBufferSize(SettingStorage.Net.SEND_BUF_SIZE);
			socket.setReceiveBufferSize(SettingStorage.Net.RECEIVE_BUF_SIZE);
			socket.setPerformancePreferences(SettingStorage.Net.PREFERENCE_CON_TIME, SettingStorage.Net.PREFERENCE_LATENCY, SettingStorage.Net.PREFERENCE_BANDWIDTH);
			socket.setTrafficClass(SettingStorage.Net.TRAFFIC_CLASS);
			
			InputStream inS = socket.getInputStream();
			OutputStream outS = socket.getOutputStream();
			DataInputStream in = new DataInputStream(inS);
			DataOutputStream out = new DataOutputStream(outS);
			
			this.in = in;
			this.out = out;
		} catch(IOException e){
			Logger.println("Connection failed (TCP)", Logger.Type.ERROR);
			Loader.exit();
		}
	}

	@Override
	public void send(int type, String str){
		try{
			if (out != null) {
				out.writeUTF(type + " " + str);
				out.flush();

				analyzeSend(str.length()*2);
			}
		} catch (IOException e){
			Logger.println("Connection lost (TCP send)", Logger.Type.ERROR);
			Loader.exit();
		}
	}

	@Override
	public String read(){
		try{
			String str = in.readUTF();
			analyzeRead(str.length()*2);

			return str;
		} catch (IOException e){
			Logger.println("Connection lost (TCP read)", Logger.Type.ERROR);
			Loader.exit();
			return null;
		}
	}
}
