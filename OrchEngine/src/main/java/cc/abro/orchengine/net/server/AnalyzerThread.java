package cc.abro.orchengine.net.server;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AnalyzerThread extends Thread {

    public AnalyzerThread() {
        setName("ServerAnalyzer");
    }

    @Override
    public void run() {
        long timeAnalysis = System.currentTimeMillis();//Время анализа MPS
        int timeAnalysisDelta = 1000;

        while (true) {
            if (System.currentTimeMillis() > timeAnalysis + timeAnalysisDelta) {//Анализ MPS
                timeAnalysis = System.currentTimeMillis();
                StringBuilder sb = new StringBuilder();
                sb.append("MPS: ");
                for (int i = 0; i < GameServer.peopleMax; i++) {
                    sb.append(GameServer.connects[i].numberSend);
                    if (i != GameServer.peopleMax - 1) sb.append(" | ");
                    GameServer.connects[i].numberSend = 0;
                }
                log.trace(sb.toString());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

    }
}
