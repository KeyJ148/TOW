package main.net;

import java.util.ArrayList;

public class MessagePack {
	
	public ArrayList<String> message;//Список сообщений
	public long lastTrim;
	
	public MessagePack(){
		this.message = new ArrayList<String>();
		this.lastTrim = System.currentTimeMillis();//Каждые пять секунд
	}
	
	public void add(String str){
		message.add(str);
	}
	
	public String get(){
		if (size()%25 == 0) 
			System.out.println("Messages detained: " + size());
		if (System.currentTimeMillis() > lastTrim + 5000)
			message.trimToSize();
		return message.remove(0);
	}
	
	public int size(){
		return message.size();
	}
	
	public void clear(){
		message.clear();
		message.trimToSize();
	}
	
	public boolean haveMessage(){
		if (message.isEmpty()) return false;
		return true;
	}
	
}
