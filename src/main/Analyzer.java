package main;

import main.net.Ping;

public class Analyzer {
	
	//Для подсчёта fps
	public int loopsRender = 0; 
	public int loopsAnalysis = 0;
	public long lastAnalysis;
	
	//Для подсчёта быстродействия
	long durationLoops = 0;
	
	//Пинг
	public int ping=0, pingMin=0, pingMax=0, pingMid=0;
			
	//Скорость сети
	public int send=0, load=0;
	
	public int draw = 0, drawBack = 0;//Количество объектов в поле зрения камеры
	public int background = 0;
	
	public Analyzer(){
		lastAnalysis = System.currentTimeMillis();
		Global.pingCheck = new Ping();
	}
	
	public void loops(long startLoops){
		durationLoops += System.nanoTime() - startLoops;
		loopsRender++;
		if (System.currentTimeMillis() >= lastAnalysis + 1000){
			analysisData();
		}
	}

	public void analysisData(){
		loopsAnalysis++;
		
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
				
		
		String strFPS1 =  "Player: " + (enemySize+1) + "/" + Global.peopleMax
						+ "          Ping: " + ping + " (" + pingMin + "-" + pingMid + "-" + pingMax + ")"
						+ "          Speed S/L: " + send + "/" + load + " kb/s";
		String strFPS2 =   "FPS: " + loopsRender
				+ "          Duration loop: " + (durationLoops/loopsRender/1000) + " mks"
				+ "          Object: " + objSize + " (D " + draw + ")"
				+ "          Background: " + background + " (D " + drawBack + ")";
				
		if (Global.setting.DEBUG_CONSOLE_FPS){
			System.out.println(strFPS1);
			System.out.println(strFPS2);
		}
		if (Global.setting.DEBUG_MONITOR_FPS){
			Global.game.render.strAnalysis1 = strFPS1;
			Global.game.render.strAnalysis2 = strFPS2;
		}
		
		
		lastAnalysis = System.currentTimeMillis();
		loopsRender = 0;
		durationLoops = 0;
	}
	
}
