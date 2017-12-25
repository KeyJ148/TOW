package engine.net.client;

import engine.Global;
import engine.io.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPControl {
	
	private DataInputStream in;
	private DataOutputStream out;

	public int sizeDataSend = 0;//bytes 
	public int sizeDataRead = 0;
	public Object sizeDataReadMonitor = new Object();//Нужен, чтобы не запрашивать sizeDataRead из потока считывания и анализатора
	
	public void connect(String ip, int port){
		try{
			@SuppressWarnings("resource")
			Socket sock = new Socket(InetAddress.getByName(ip), port);
			sock.setTcpNoDelay(Global.setting.TCP_NODELAY);
			sock.setKeepAlive(Global.setting.KEEP_ALIVE);
			sock.setSendBufferSize(Global.setting.SEND_BUF_SIZE);
			sock.setReceiveBufferSize(Global.setting.RECEIVE_BUF_SIZE);
			sock.setPerformancePreferences(Global.setting.PREFERENCE_CON_TIME, Global.setting.PREFERENCE_LATENCY, Global.setting.PREFERENCE_BANDWIDTH);
			sock.setTrafficClass(Global.setting.TRAFFIC_CLASS);
			
			InputStream inS = sock.getInputStream();
			OutputStream outS = sock.getOutputStream();
			DataInputStream in = new DataInputStream(inS);
			DataOutputStream out = new DataOutputStream(outS);
			
			this.in = in;
			this.out = out;
		} catch(IOException e){
			Logger.println("Connection failed", Logger.Type.ERROR);
			System.exit(0);
		}
	}
	
	public void send(int type, String str){
		try{
			sizeDataSend += str.length()*2;
			if (out != null) {
				out.flush();
				out.writeUTF(type + " " + str);
			}
		} catch (IOException e){
			Logger.println("Send internet message", Logger.Type.ERROR);
			System.exit(0);
		}
	}
	
	public String read(){
		try{
			String str = in.readUTF();
			synchronized (sizeDataReadMonitor){
				sizeDataRead += str.length()*2;
			}
			return str;
		} catch (IOException e){
			Logger.println("Read internet message", Logger.Type.ERROR);
			System.exit(0);
			return null;
		}
	}
}
