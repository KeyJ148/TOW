package engine.net.client;

import engine.Loader;
import engine.io.Logger;
import engine.setting.SettingStorage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPControl {

	private DataInputStream in;
	private DataOutputStream out;

	//Статистика отправки и получения сообщений
	public int sizeDataSend = 0, sizeDataRead = 0;//В байтах
	public int countPackageSend = 0, countPackageRead = 0;//В кол-ве сообщений
	public Object sizeDataReadMonitor = new Object();//Нужен, чтобы не запрашивать sizeDataRead из потока считывания и анализатора

	public void connect(String ip, int port){
		try{
			@SuppressWarnings("resource")
			Socket sock = new Socket(InetAddress.getByName(ip), port);
			sock.setTcpNoDelay(SettingStorage.Net.TCP_NODELAY);
			sock.setKeepAlive(SettingStorage.Net.KEEP_ALIVE);
			sock.setSendBufferSize(SettingStorage.Net.SEND_BUF_SIZE);
			sock.setReceiveBufferSize(SettingStorage.Net.RECEIVE_BUF_SIZE);
			sock.setPerformancePreferences(SettingStorage.Net.PREFERENCE_CON_TIME, SettingStorage.Net.PREFERENCE_LATENCY, SettingStorage.Net.PREFERENCE_BANDWIDTH);
			sock.setTrafficClass(SettingStorage.Net.TRAFFIC_CLASS);

			InputStream inS = sock.getInputStream();
			OutputStream outS = sock.getOutputStream();
			DataInputStream in = new DataInputStream(inS);
			DataOutputStream out = new DataOutputStream(outS);

			this.in = in;
			this.out = out;
		} catch(IOException e){
			Logger.println("Connection failed", Logger.Type.ERROR);
			Loader.exit();
		}
	}

	public void send(int type, String str){
		try{
			countPackageSend++;
			sizeDataSend += str.length()*2;
			if (out != null) {
				out.flush();
				out.writeUTF(type + " " + str);
			}
		} catch (IOException e){
			Logger.println("Connection lost (Send)", Logger.Type.ERROR);
			Loader.exit();
		}
	}

	public String read(){
		try{
			String str = in.readUTF();
			synchronized (sizeDataReadMonitor){
				countPackageRead++;
				sizeDataRead += str.length()*2;
			}
			return str;
		} catch (IOException e){
			Logger.println("Connection lost (Read)", Logger.Type.ERROR);
			Loader.exit();
			return null;
		}
	}
}
