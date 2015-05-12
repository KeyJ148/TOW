package main.net;

import main.Global;

public class Ping {
	
	private long pingTime;
	private int pingSum;
	private int pingNumber;
	
	private int ping = 9999; //текущий
	private int pingMin = 9999;
	private int pingMid = 0;
	private int pingMax = 0;
	
	public int ping(){
		pingTime = System.currentTimeMillis();
		Global.clientSend.sendM2();
		
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
