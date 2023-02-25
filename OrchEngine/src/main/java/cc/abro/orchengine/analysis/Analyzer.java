package cc.abro.orchengine.analysis;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.location.ObjectsContainer;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.net.client.udp.UDPControl;
import lombok.extern.log4j.Log4j2;
import org.picocontainer.Startable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@EngineService
public class Analyzer implements Startable {

    //Для подсчёта fps, ups
    protected int loopsRender;
    protected int loopsUpdate;
    protected int loopsSync;
    private long startUpdate, startRender, startSync, lastAnalysis;

    //Для подсчёта быстродействия
    protected long durationUpdate;
    protected long durationRender;
    protected long durationSync;

    //Пинг
    protected int ping, pingMin, pingMax, pingMid;

    //Скорость сети
    protected int sendTCP, loadTCP, sendPackageTCP, loadPackageTCP;
    protected int sendUDP, loadUDP, sendPackageUDP, loadPackageUDP;

    //Использование памяти
    protected long freeMem, totalMem, maxMem;

    //Использование чанков
    protected ObjectsContainer.Statistic statistic;
    protected int chunksRenderedSum, objectsRenderedSum, unsuitableObjectsRenderedSum, layersCountRenderer;

    //Результаты анализа построчно
    private List<String> analysisResultStrings = Collections.emptyList();

    private AnalysisStringBuilder analysisStringBuilder;
    private final TCPControl tcpControl;
    private final UDPControl udpControl;
    private final PingChecker pingCheckerCheck;

    public Analyzer(TCPControl tcpControl, UDPControl udpControl, PingChecker pingCheckerCheck) {
        this.tcpControl = tcpControl;
        this.udpControl = udpControl;
        this.pingCheckerCheck = pingCheckerCheck;
    }

    @Override
    public void start() {
        analysisStringBuilder = new AnalysisStringBuilder(this);
        lastAnalysis = System.currentTimeMillis();
    }

    @Override
    public void stop() {
    }

    public void startUpdate() {
        startUpdate = System.nanoTime();
    }

    public void endUpdate() {
        durationUpdate += System.nanoTime() - startUpdate;
        loopsUpdate++;
    }

    public void startRender() {
        startRender = System.nanoTime();
    }

    public void endRender() {
        durationRender += System.nanoTime() - startRender;
        loopsRender++;
    }

    public void startSync() {
        startSync = System.nanoTime();
    }

    public void endSync() {
        durationSync += System.nanoTime() - startSync;
        loopsSync++;
    }

    public void update() {
        //Если прошло меньше секунды - выходим
        if (System.currentTimeMillis() < lastAnalysis + 1000) return;

        analysisData();
        analysisResultStrings = analysisStringBuilder.getAllStrings();
        analysisResultStrings.forEach(log::trace);
        clearData();
    }

    public List<String> getAnalysisResult() {
        return new ArrayList<>(analysisResultStrings);
    }

    //Анализ данных
    private void analysisData() {
        ping = pingCheckerCheck.ping();
        pingMin = pingCheckerCheck.pingMin();
        pingMid = pingCheckerCheck.pingMid();
        pingMax = pingCheckerCheck.pingMax();

        sendTCP = Math.round(tcpControl.sizeDataSend / 1024);
        loadTCP = Math.round(tcpControl.sizeDataRead / 1024);
        sendPackageTCP = tcpControl.countPackageSend;
        loadPackageTCP = tcpControl.countPackageRead;
        tcpControl.analyzeClear();

        sendUDP = Math.round(udpControl.sizeDataSend / 1024);
        loadUDP = Math.round(udpControl.sizeDataRead / 1024);
        sendPackageUDP = udpControl.countPackageSend;
        loadPackageUDP = udpControl.countPackageRead;
        udpControl.analyzeClear();

        freeMem = Runtime.getRuntime().freeMemory();
        totalMem = Runtime.getRuntime().totalMemory();
        maxMem = Runtime.getRuntime().maxMemory();

        statistic = Context.getService(LocationManager.class).getActiveLocation().getStatistic();
        chunksRenderedSum = statistic.chunksRenderedByLayerZ().values().stream().reduce(0, Integer::sum);
        objectsRenderedSum = statistic.objectsRenderedByLayerZ().values().stream().reduce(0, Integer::sum);
        unsuitableObjectsRenderedSum = statistic.unsuitableObjectsRenderedByLayerZ().values().stream().reduce(0, Integer::sum);
        layersCountRenderer = statistic.chunksRenderedByLayerZ().size();

        //Для строк отладки, иначе деление на 0
        if (loopsRender == 0) loopsRender = 1;
        if (loopsUpdate == 0) loopsUpdate = 1;
        if (loopsSync == 0) loopsSync = 1;
    }

    //Обнуление счётчиков
    private void clearData() {
        lastAnalysis = System.currentTimeMillis();
        loopsUpdate = 0;
        loopsRender = 0;
        durationUpdate = 0;
        durationRender = 0;
        durationSync = 0;
    }
}
