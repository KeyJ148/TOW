package cc.abro.orchengine.net.client.udp;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.net.client.Message;

import java.util.ArrayList;

public class UDPRead extends Thread {

	private volatile ArrayList<Message> messages = new ArrayList<>();

	@Override
	public void run() {
		//Постоянный обмен данными на UDP
		String str;
		while (true) {
			str = Global.udpControl.read();

			int type = Integer.parseInt(str.split(" ")[0]);
			String data = str.substring(str.indexOf(" ") + 1);
			long timeReceipt = System.nanoTime();
			Message message = new Message(type, data, timeReceipt);

			synchronized (messages) {
				messages.add(message); //Ждём update и там обрабатываем
			}
		}
	}

	public void update() {
		synchronized (messages) {
			for (int i = 0; i < messages.size(); i++) {
				Message message = messages.get(i);
				Global.netGameRead.readUDP(message);
			}

			messages.clear();
		}
	}
}
