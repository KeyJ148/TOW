package tow.net;

import tow.Global;

public class Ping {
	
	private long pingTime;
	private int pingSum = 0;
	private int pingNumber = 0;
	
	private int ping = 9999; //текущий
	private int pingMin = 9999;
	private int pingMid = 0;
	private int pingMax = 0;
	
	private boolean returnData = true;
	
	public int ping(){
		send();
		return ping;
	}
	
	public void takePing(){
		long pingL = System.currentTimeMillis() - pingTime;
		int ping = (int) pingL;
		
		this.ping = ping;
		if (ping < pingMin) pingMin = ping;
		if (ping > pingMax) pingMax = ping;
		
		pingSum += ping;
		pingNumber++;
		pingMid = pingSum/pingNumber;
		
		returnData = true;
	}
	
	public void send(){
		if (returnData){
			returnData = false;
			pingTime = System.currentTimeMillis();
			Global.clientSend.sendM2();
		}
	}
	
	public void clear(){
		pingSum = 0;
		pingNumber = 0;
		
		ping = 9999;
		pingMin = 9999;
		pingMid = 0;
		pingMax = 0;
	}
	
	public int pingMax(){
		return pingMax;
	}
	
	public int pingMid(){
		return pingMid;
	}
	
	public int pingMin(){
		return pingMin;
	}
}
