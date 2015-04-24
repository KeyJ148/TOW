package main.net;

import java.util.ArrayList;

public class MessagePack {
	int maxSize = 0;
	
	public ArrayList<String> message;//Список сообщений
	
	public MessagePack(){
		this.message = new ArrayList<String>();
	}
	
	public void add(String str){
		message.add(str);
		if (size() > maxSize) {
			maxSize = size();
			System.out.println(maxSize);
		}
	}
	
	public String get(){
		return message.remove(0);
	}
	
	public int size(){
		return message.size();
	}
	
	public boolean haveMessage(){
		//message.trimToSize();
		if (message.size() == 0){
			return false;
		} else {
			return true;
		}
	}
}
