package main.net;

import main.GameServer;

public class CheckMapLoad extends Thread{
	
	GameServer gS;
	
	public CheckMapLoad(GameServer gameServer){
		this.gS = gameServer;
		gS.tankGenComplite = false;
		for(int i=0;i<gS.peopleMax;i++){
			gS.connect[i] = false;
		}
		gS.genTank();
		System.out.println("Check map loading = false.");
		start();
	}
	
	public void run(){
		System.out.println("Start thread CheckMapLoad.");
		boolean mapDownAll;//все ли скачали карту
		
		do{
			mapDownAll = true;
			for(int i=0;i<gS.peopleMax;i++){
				if (gS.connect[i] == false){
					mapDownAll = false;
				}
			}
			
			try {
				Thread.sleep(0,1);
			} catch (InterruptedException e) {}
		}while(!mapDownAll);
		
		for(int i=0;i<gS.peopleMax;i++){
			gS.serverThread[i].mapDownAll = true;
		}
		
		gS.cml = null;
		
		System.out.println("End thread CheckMapLoad.");
	}

}
