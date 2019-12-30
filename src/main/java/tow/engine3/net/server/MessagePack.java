package tow.engine3.net.server;

import tow.engine2.io.Logger;

import java.util.LinkedList;

public class MessagePack {
	
	private LinkedList<Message> messages;//Список сообщений
	public int id;
	
	public MessagePack(int id){
		this.messages = new LinkedList<>();
		this.id = id;
	}
	
	public void add(String str, Message.InetType inetType){
		if (str.length() > 0){
			int type = Integer.parseInt(str.split(" ")[0]);
			String mes = str.substring(str.indexOf(" ")+1);
			add(new Message(type, mes, id, inetType));
		}
	}

	public void add(Message message){
		messages.add(message);
	}
	
	public Message get(){
		if (size()%10 == 0){
			Logger.println("Messages detained: " + size() + " (id: " + id + ")", Logger.Type.SERVER_DEBUG);
		}
		
		return messages.removeFirst();
	}
	
	public int size(){
		return messages.size();
	}
	
	public boolean haveMessage(){
		if (messages.isEmpty()) return false;
		return true;
	}

	public static class Message{

		public enum InetType {TCP, UDP};

		public int type;
		public String text;
		public int authorId;
		public InetType inetType;

		public Message(int type, String text, int authorId, InetType inetType){
			this.type = type;
			this.text = text;
			this.authorId = authorId;
			this.inetType = inetType;
		}
	}
	
}
