package cc.abro.tow.client;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.analysis.AnalysisStringBuilder;
import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.location.Location;

import java.util.Collections;

import static cc.abro.tow.client.menu.InterfaceStyles.LABEL_HEIGHT_DEBUG;

public class GameLocation extends Location {

    public GameLocation(int width, int height) {
        super(width, height);
    }

    protected void addDebugPanel(int positionX) {
        DebugInfoGuiPanel debugGuiPanel = new DebugInfoGuiPanel(AnalysisStringBuilder.STRING_COUNT);
        debugGuiPanel.setPosition(positionX,
                Manager.getService(Render.class).getHeight() - AnalysisStringBuilder.STRING_COUNT*LABEL_HEIGHT_DEBUG);
        getGuiLocationFrame().getGuiFrame().getContainer().add(debugGuiPanel);

        DebugInfoComponent debugInfoComponent = new DebugInfoComponent(debugGuiPanel);
        getMap().add(GameObjectFactory.create(debugInfoComponent));
    }

    //TODO вынести в отдельный класс или упростить в новой системе компонент
    public class DebugInfoComponent extends Component {

        private final DebugInfoGuiPanel debugInfoGuiPanel;

        public DebugInfoComponent(DebugInfoGuiPanel debugInfoGuiPanel){
            this.debugInfoGuiPanel = debugInfoGuiPanel;
        }

        @Override
        public void update(long delta) {
            if (ClientData.printAnalyzerInfo) {
                debugInfoGuiPanel.setText(Manager.getService(Analyzer.class).getAnalysisResult());
            } else {
                debugInfoGuiPanel.setText(Collections.nCopies(AnalysisStringBuilder.STRING_COUNT, ""));
            }
        }

        @Override
        public void draw() {}

        @Override
        public Class<? extends Component> getComponentClass() {
            return DebugInfoComponent.class;
        }
    }
}
