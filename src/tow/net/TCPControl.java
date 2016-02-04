package tow.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import tow.Global;

public class TCPControl {
	
	private DataInputStream in;
	private DataOutputStream out;
	
	public int sizeDataSend = 0;//bytes 
	public int sizeDataRead = 0;
	public Object sizeDataReadMonitor = new Object();//Нужен, т.к tcpReceive работает в отдельном потоке
	
	public void connect(){
		initSettings();
		
		try{
			@SuppressWarnings("resource")
			Socket sock = new Socket(InetAddress.getByName(Global.ip), Global.port);
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
			Global.error("Connection failed");
			System.exit(0);
		}
	}
	
	private void initSettings(){
		
	}
	
	public void send(String str){
		try{
			sizeDataSend += str.length()*2;
			out.flush();
			out.writeUTF(str);
		} catch (IOException e){
			Global.error("Send internet message");
			System.exit(0);
		}
	}
	
	public String read(){
		try{
			String str = in.readUTF();
			sizeDataRead += str.length()*2;
			return str;
		} catch (IOException e){
			Global.error("Read internet message");
			System.exit(0);
			return null;
		}
	}
}
