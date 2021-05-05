package cc.abro.orchengine.analysis;

import cc.abro.orchengine.Global;

public class AnalysisStringBuilder {

    public Analyzer analyzer;

    public AnalysisStringBuilder(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public String getAnalysisString1() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ping: ");
        sb.append(analyzer.ping);
        sb.append(" (");
        sb.append(analyzer.pingMin);
        sb.append("-");
        sb.append(analyzer.pingMid);
        sb.append("-");
        sb.append(analyzer.pingMax);
        sb.append(")");
        sb.append("          ");

        sb.append("Speed TCP S/L, UDP S/L: ");
        sb.append(analyzer.sendTCP);
        sb.append("/");
        sb.append(analyzer.loadTCP);
        sb.append(", ");
        sb.append(analyzer.sendUDP);
        sb.append("/");
        sb.append(analyzer.loadUDP);
        sb.append(" kb/s");
        sb.append("          ");

        sb.append("Package TCP S/L, UDP S/L: ");
        sb.append(analyzer.sendPackageTCP);
        sb.append("/");
        sb.append(analyzer.loadPackageTCP);
        sb.append(", ");
        sb.append(analyzer.sendPackageUDP);
        sb.append("/");
        sb.append(analyzer.loadPackageUDP);
        sb.append("          ");

        return sb.toString();
    }

    public String getAnalysisString2() {
        StringBuilder sb = new StringBuilder();
        sb.append("FPS: ");
        sb.append(analyzer.loopsRender);
        sb.append("          ");

        sb.append("Duration update/render/sync: ");
        sb.append((analyzer.durationUpdate / analyzer.loopsUpdate / 1000));
        sb.append("/");
        sb.append((analyzer.durationRender / analyzer.loopsRender / 1000));
        sb.append("/");
        sb.append((analyzer.durationSync / analyzer.loopsSync / 1000));
        sb.append(" mks");
        sb.append("          ");

        sb.append("Objects: ");
        sb.append(Global.location.objCount());
        sb.append("          ");

        sb.append("Chunks: ");
        sb.append(Global.location.mapControl.chunkRender);
        sb.append(" (");
        sb.append(analyzer.chunkInDepthVector);
        sb.append("*");
        sb.append(Global.location.mapControl.getCountDepthVectors());
        sb.append(")");
        sb.append("          ");

        return sb.toString();
    }
}
