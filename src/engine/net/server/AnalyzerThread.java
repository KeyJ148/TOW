package engine.net.server;

import engine.io.Logger;

public class AnalyzerThread extends Thread{

    @Override
    public void run(){
        long timeAnalysis = System.currentTimeMillis();//Время анализа MPS
        int timeAnalysisDelta = 1000;

        while (true){
            if (System.currentTimeMillis() > timeAnalysis+timeAnalysisDelta){//Анализ MPS
                timeAnalysis = System.currentTimeMillis();
                Logger.print("MPS: ", Logger.Type.MPS);
                for (int i = 0; i < GameServer.peopleMax; i++){
                    Logger.print(String.valueOf(GameServer.numberSend[i]), Logger.Type.MPS);
                    if (i != GameServer.peopleMax-1) Logger.print(" | ", Logger.Type.MPS);
                    GameServer.numberSend[i] = 0;
                }
                Logger.println("", Logger.Type.MPS);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }

    }
}
