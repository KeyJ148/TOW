package cc.abro.tow.client;

import cc.abro.orchengine.analysis.AnalysisStringBuilder;
import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.location.Location;

import java.util.Collections;

import static cc.abro.tow.client.menu.InterfaceStyles.LABEL_HEIGHT_DEBUG;

public class GameLocation extends Location {

    private final static int CHUNK_SIZE = 100;

    public GameLocation(int width, int height) {
        super(width, height, CHUNK_SIZE);
    }

    protected void addDebugPanel(int positionX) {
        DebugInfoGuiPanel debugGuiPanel = new DebugInfoGuiPanel(AnalysisStringBuilder.STRING_COUNT);
        debugGuiPanel.setPosition(positionX,
                Context.getService(Render.class).getHeight() - AnalysisStringBuilder.STRING_COUNT*LABEL_HEIGHT_DEBUG);
        getGuiLocationFrame().getGuiFrame().getContainer().add(debugGuiPanel);

        DebugInfoComponent debugInfoComponent = new DebugInfoComponent(debugGuiPanel);
        GameObjectFactory.create(this, debugInfoComponent);
    }

    //TODO вынести в отдельный класс или упростить в новой системе компонент
    public class DebugInfoComponent extends Component implements Updatable {

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

        @Override
        public Class<? extends Component> getComponentClass() {
            return DebugInfoComponent.class;
        }
    }
}
