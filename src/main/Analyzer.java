package main;

import main.net.Ping;

public class Analyzer {
	
	Game game;
	
	//Для подсчёта fps
	int loopsRender = 0; 
	int loopsRenderMid = 0;
	int loopsAnalysis = 0;
	long lastAnalysis;
	
	//Пинг
	int ping=0, pingMin=0, pingMax=0, pingMid=0;
			
	//Скорость сети
	int send=0, load=0;
	
	public Analyzer(Game game){
		this.game = game;
		lastAnalysis = System.currentTimeMillis();
		Global.pingCheck = new Ping();
	}
	
	public void loops(){
		loopsRender++;
		if (System.currentTimeMillis() >= lastAnalysis + 1000){
			analysisData();
		}
	}

	public void analysisData(){
		loopsAnalysis++;
		loopsRenderMid += loopsRender;
		
		int objSize = 0;
		for (int i=0;i<Global.obj.size();i++){
			if (Global.obj.get(i) != null){
				objSize++;
			}
		}
				
		int enemySize = 0;
		for (int i=0;i<Global.enemy.length;i++){
			if ((Global.enemy[i] != null) && (!Global.enemy[i].getDestroy())){
				enemySize++;
			}
		}
		
		if ((enemySize+1) == game.peopleMax){ //Если все подключены - данные сетевой игры
			ping = Global.pingCheck.ping();
			pingMin = Global.pingCheck.pingMin();
			pingMid = Global.pingCheck.pingMid();
			pingMax = Global.pingCheck.pingMax();
				
			
			send = Math.round(Global.clientSend.sizeData/1024);
			load = Math.round(Global.clientThread.sizeData/1024);
			Global.clientSend.sizeData = 0;
			synchronized (Global.clientThread.sizeDataMonitor){
				Global.clientThread.sizeData = 0;
			}
		}
				
		String strFPS = "FPS: " + loopsRender
						+ "          MidFPS: " + loopsRenderMid/loopsAnalysis
						+ "          Object: " + objSize
						+ "          Player: " + (enemySize+1) + "/" + game.peopleMax
						+ "          Ping: " + ping + " (" + pingMin + "-" + pingMid + "-" + pingMax + ")"
						+ "          Speed S/L: " + send + "/" + load + " kb/s";
		if (Global.setting.DEBUG_CONSOLE_FPS) System.out.println(strFPS);
		if (Global.setting.DEBUG_MONITOR_FPS) game.monitorStrFPS = strFPS;
		
		
		lastAnalysis = System.currentTimeMillis();
		loopsRender = 0;
	}
	
}
