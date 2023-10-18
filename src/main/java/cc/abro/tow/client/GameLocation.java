package cc.abro.tow.client;

import cc.abro.orchengine.analysis.AnalysisStringBuilder;
import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.gui.DebugInfoGuiPanel;

import java.util.Collections;
import java.util.Set;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.LABEL_HEIGHT_DEBUG;

public class GameLocation extends Location {

    private final static int CHUNK_SIZE = 100;

    public GameLocation(int width, int height) {
        super(width, height, CHUNK_SIZE);
    }

    protected void addDebugPanel(int positionX) {
        DebugInfoGuiPanel debugGuiPanel = new DebugInfoGuiPanel(AnalysisStringBuilder.STRING_COUNT);
        debugGuiPanel.setPosition(positionX,
                getRender().getHeight() - AnalysisStringBuilder.STRING_COUNT*LABEL_HEIGHT_DEBUG);
        getGuiLocationFrame().getGuiFrame().getContainer().add(debugGuiPanel);

        DebugInfoComponent debugInfoComponent = new DebugInfoComponent(debugGuiPanel);
        new GameObject(this, Set.of(debugInfoComponent));
    }

    public static class DebugInfoComponent extends Component implements Updatable {

        private final DebugInfoGuiPanel debugInfoGuiPanel;

        public DebugInfoComponent(DebugInfoGuiPanel debugInfoGuiPanel) {
            this.debugInfoGuiPanel = debugInfoGuiPanel;
        }

        @Override
        public void update(long delta) {
            if (Context.getService(ClientData.class).printAnalyzerInfo) {
                debugInfoGuiPanel.setText(Context.getService(Analyzer.class).getAnalysisResult());
            } else {
                debugInfoGuiPanel.setText(Collections.nCopies(AnalysisStringBuilder.STRING_COUNT, ""));
            }
        }
    }
}
