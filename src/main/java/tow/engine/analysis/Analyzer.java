package tow.engine.analysis;

import tow.engine.Global;
import tow.engine2.io.Logger;
import tow.engine2.resources.settings.SettingsStorage;

public class Analyzer {

	//Для подсчёта fps, ups
	public int loopsRender = 0;
	public int loopsUpdate = 0;
	public int loopsSync = 0;
	private long startUpdate, startRender, startSync, lastAnalysis;

	//Для подсчёта быстродействия
	public long durationUpdate = 0;
	public long durationRender = 0;
	public long durationSync = 0;

	//Пинг
	public int ping=0, pingMin=0, pingMax=0, pingMid=0;

	//Скорость сети
	public int sendTCP=0, loadTCP=0, sendPackageTCP=0, loadPackageTCP=0;
	public int sendUDP=0, loadUDP=0, sendPackageUDP=0, loadPackageUDP=0;

	public int chunkInDepthVector;

	private AnalysisStringBuilder analysisStringBuilder;

	public Analyzer(){
		analysisStringBuilder = new AnalysisStringBuilder(this);
		lastAnalysis = System.currentTimeMillis();
	}

	public void startUpdate(){
		startUpdate = System.nanoTime();
	}

	public void endUpdate(){
		durationUpdate += System.nanoTime() - startUpdate;
		loopsUpdate++;
	}

	public void startRender(){
		startRender = System.nanoTime();
	}

	public void endRender(){
		durationRender += System.nanoTime() - startRender;
		loopsRender++;
	}

	public void startSync(){
		startSync = System.nanoTime();
	}

	public void endSync(){
		durationSync += System.nanoTime() - startSync;
		loopsSync++;
	}

	public void update(){
		//Если прошло меньше секунды - выходим
		if (System.currentTimeMillis() < lastAnalysis + 1000) return;

		analysisData();
		outputResult();
		clearData();
	}

	//Анализ данных
	public void analysisData(){
		ping = Global.pingCheck.ping();
		pingMin = Global.pingCheck.pingMin();
		pingMid = Global.pingCheck.pingMid();
		pingMax = Global.pingCheck.pingMax();

		sendTCP = Math.round(Global.tcpControl.sizeDataSend/1024);
		loadTCP = Math.round(Global.tcpControl.sizeDataRead/1024);
		sendPackageTCP = Global.tcpControl.countPackageSend;
		loadPackageTCP = Global.tcpControl.countPackageRead;
		Global.tcpControl.analyzeClear();

		sendUDP = Math.round(Global.udpControl.sizeDataSend/1024);
		loadUDP = Math.round(Global.udpControl.sizeDataRead/1024);
		sendPackageUDP = Global.udpControl.countPackageSend;
		loadPackageUDP = Global.udpControl.countPackageRead;
		Global.udpControl.analyzeClear();

		chunkInDepthVector = (Global.room.mapControl.getCountDepthVectors() == 0)? 0: Global.room.mapControl.chunkRender / Global.room.mapControl.getCountDepthVectors();

		//Для строк отладки, иначе делние на 0
		if (loopsRender == 0) loopsRender = 1;
		if (loopsUpdate == 0) loopsUpdate = 1;
		if (loopsSync == 0) loopsSync = 1;
	}

	//Обнуление счётчиков
	public void clearData(){
		lastAnalysis = System.currentTimeMillis();
		loopsUpdate = 0;
		loopsRender = 0;
		durationUpdate = 0;
		durationRender = 0;
	}

	//Вывод результатов
	public void outputResult(){
		//Получение строк с результатами
		String str1 = analysisStringBuilder.getAnalysisString1();
		String str2 = analysisStringBuilder.getAnalysisString2();

		//Вывод результатов в консоль
		Logger.println(str1, Logger.Type.CONSOLE_FPS);
		Logger.println(str2, Logger.Type.CONSOLE_FPS);

		//Вывод результатов на монитор
		if (SettingsStorage.LOGGER.DEBUG_MONITOR_FPS){
			//TODO: надпись - компонент объекта
			//Отрисвока надписей
			/*
			addTitle(new Title(1, getHeight()-27,strAnalysis1, Color.black, 12, Font.BOLD));
			addTitle(new Title(1, getHeight()-15,strAnalysis2, Color.black, 12, Font.BOLD));
			*/
		}
	}

}
