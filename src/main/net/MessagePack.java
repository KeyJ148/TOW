package main.net;

import java.util.ArrayList;

public class MessagePack {
	
	public ArrayList<String> message;//Список сообщений
	
	public MessagePack(){
		this.message = new ArrayList<String>();
	}
	
	public void add(String str){
		message.add(str);
	}
	
	public String get(){
		if (size()%20 == 0) 
			System.out.println("Messages detained: " + size());
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
		//message.trimToSize();
		if (message.size() > 0){
				return true;
		}
		return false;
	}
	
}
