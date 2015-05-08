package main.net;

import main.Global;

public class Ping {
	
	private final int size = 10;
	
	private int idPing = 0;
	private long[] pingTime;
	private int pingTimeFirstIndex = 0;//idPing первый в массиве
	private int ping = 999; //текущий
	
	public Ping(){
		pingTime = new long[size];
	}
	
	public int ping(){
		
		boolean arrayFull = true;
		
		for (int i=0; i< pingTime.length; i++){
			if (pingTime[i] == 0){
				pingTime[i] = System.currentTimeMillis();
				arrayFull = false;
				break;
			}
		}
		
		if (arrayFull){
			clearArray();
			pingTime[0] = System.currentTimeMillis();
		}
		
		Global.clientSend.sendM2(idPing);
		idPing++;
		
		return ping;
	}
	
	public void takePing(int idPing){
		long ping = System.currentTimeMillis() - pingTime[idPing-pingTimeFirstIndex];
		this.ping = (int) ping;
	}
	
	private void clearArray(){
		pingTimeFirstIndex += size; 
		for (int i=0; i< pingTime.length; i++){
			pingTime[i] = 0;
		}
	}
}
