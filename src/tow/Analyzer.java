package tow;

import tow.net.Ping;

public class Analyzer {
	
	//Для подсчёта fps
	public int loopsRender = 0; 
	public int loopsUpdate = 0; 
	public long lastAnalysis;
	
	//Для подсчёта быстродействия
	long durationUpdate = 0;
	long durationRender = 0;
	
	//Пинг
	public int ping=0, pingMin=0, pingMax=0, pingMid=0;
			
	//Скорость сети
	public int send=0, load=0;
	
	public Analyzer(){
		lastAnalysis = System.currentTimeMillis();
		Global.pingCheck = new Ping();
	}
	
	public void loopsUpdate(long startUpdate){
		durationUpdate += System.nanoTime() - startUpdate;
		loopsUpdate++;
		if (System.currentTimeMillis() >= lastAnalysis + 1000){
			analysisData();
		}
	}
	
	public void loopsRender(long startRender){
		durationRender += System.nanoTime() - startRender;
		loopsRender++;
	}

	public void analysisData(){
		int objSize = 0;
		for (int i=0;i<Global.getSize();i++){
			if (Global.getObj(i) != null){
				objSize++;
			}
		}
				
		int enemySize = 0;
		for (int i=0;i<Global.enemy.length;i++){
			if ((Global.enemy[i] != null) && (!Global.enemy[i].getDestroy())){
				enemySize++;
			}
		}
		
		if ((enemySize+1) == Global.peopleMax){ //Если все подключены - данные сетевой игры
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
				
		//Для строк отладки, иначе делние на 0
		if (loopsRender == 0) loopsRender = 1;
		if (loopsUpdate == 0) loopsUpdate = 1;
		
		//Строки отладки
		String strFPS1 =  "Player: " + (enemySize+1) + "/" + Global.peopleMax
						+ "          Ping: " + ping + " (" + pingMin + "-" + pingMid + "-" + pingMax + ")"
						+ "          Speed S/L: " + send + "/" + load + " kb/s";
		String strFPS2 =   "FPS/TPS: " + loopsRender + "/" + loopsUpdate
				+ "          Duration update/render: " + (durationUpdate/loopsUpdate/1000) + "/" + (durationRender/loopsRender/1000) + " mks"
				+ "          Object: " + objSize;
				
		if (Global.setting.DEBUG_CONSOLE_FPS){
			System.out.println(strFPS1);
			System.out.println(strFPS2);
		}
		if (Global.setting.DEBUG_MONITOR_FPS){
			Global.game.render.strAnalysis1 = strFPS1;
			Global.game.render.strAnalysis2 = strFPS2;
		}
		
		
		lastAnalysis = System.currentTimeMillis();
		loopsUpdate = 0;
		loopsRender = 0;
		durationUpdate = 0;
		durationRender = 0;
	}
	
}
