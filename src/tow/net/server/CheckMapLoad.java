package tow.net.server;

public class CheckMapLoad extends Thread{
	
	GameServer gS;
	
	public CheckMapLoad(GameServer gameServer){
		this.gS = gameServer;
		gS.tankGenComplite = false;
		for(int i=0;i<gS.peopleMax;i++){
			gS.connect[i] = false;
		}
		
		GameServer.p("Check map loading = false.");
		start();
	}
	
	public void run(){
		GameServer.p("Start thread CheckMapLoad.");
		
		gS.genTank();
		GameServer.p("Generation tank complited.");
		
		boolean mapDownAll;//все ли скачали карту
		
		do{
			mapDownAll = true;
			for(int i=0;i<gS.peopleMax;i++){
				if (gS.connect[i] == false){
					mapDownAll = false;
				}
			}
			
			try {
				Thread.sleep(0,0);
			} catch (InterruptedException e) {}
		}while(!mapDownAll);
		
		for(int i=0;i<gS.peopleMax;i++){
			gS.serverThread[i].mapDownAll = true;
		}
		
		gS.cml = null;
		
		GameServer.p("End thread CheckMapLoad.");
	}

}
