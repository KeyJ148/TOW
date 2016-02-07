package tow.net;

import tow.Global;

public class Ping {
	
	private long pingTime;
	private int pingSum;
	private int pingNumber;
	
	private int ping; //текущий
	private int pingMin;
	private int pingMid;
	private int pingMax;
	
	private boolean returnData = true;
	
	public Ping(){
		clear();
	}
	
	public int ping(){
		send();
		return ping;
	}
	
	public void takePing(){
		int ping = (int) (System.currentTimeMillis() - pingTime);
		
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
			Global.tcpSend.sendM2();
		}
	}
	
	public void clear(){
		pingSum = 0;
		pingNumber = 0;
		
		ping = 0;
		pingMin = 999;
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
