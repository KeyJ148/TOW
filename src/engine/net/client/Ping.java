package engine.net.client;

import engine.Global;

public class Ping {
	
	private long pingTime;
	private int pingSum;
	private int pingNumber;
	
	private int ping; //текущий
	private int pingMin;
	private int pingMid;
	private int pingMax;
	
	private boolean work = false;
	
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
		
		work = true;
	}
	
	public void send(){
		if (work){
			work = false;
			pingTime = System.currentTimeMillis();
			Global.tcpControl.send(1, "");
		}
	}
	
	public void clear(){
		pingSum = 0;
		pingNumber = 0;
		
		ping = 0;
		pingMin = 1000;
		pingMid = 0;
		pingMax = 0;
	}
	
	public void start(){
		work = true;
	}
	
	public int pingMax(){
		return pingMax;
	}
	
	public int pingMid(){
		return pingMid;
	}
	
	public int pingMin(){
		return (pingMin == 1000)? 0 : pingMin;
	}
}
