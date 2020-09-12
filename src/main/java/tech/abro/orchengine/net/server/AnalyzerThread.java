package tech.abro.orchengine.net.server;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.logger.Logger;

public class AnalyzerThread extends Thread{

    @Override
    public void run(){
        long timeAnalysis = System.currentTimeMillis();//Время анализа MPS
        int timeAnalysisDelta = 1000;

        while (true){
            if (System.currentTimeMillis() > timeAnalysis+timeAnalysisDelta){//Анализ MPS
                timeAnalysis = System.currentTimeMillis();
                Global.logger.print("[MPS] ", Logger.Type.MPS);
                for (int i = 0; i < GameServer.peopleMax; i++){
                    Global.logger.print(String.valueOf(GameServer.connects[i].numberSend), Logger.Type.MPS);
                    if (i != GameServer.peopleMax-1) Global.logger.print(" | ", Logger.Type.MPS);
                    GameServer.connects[i].numberSend = 0;
                }
                Global.logger.println("", Logger.Type.MPS);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }

    }
}
