package cc.abro.tow.client;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.QueueComponent;
import cc.abro.orchengine.location.Location;

import java.util.Collections;
import java.util.List;

import static cc.abro.tow.client.menu.InterfaceStyles.LABEL_HEIGHT_DEBUG;

public class GameLocation extends Location {

    public GameLocation(int width, int height) {
        super(width, height);
    }

    protected void addDebugPanel(int positionX) {
        DebugInfoGuiPanel debugGuiPanel = new DebugInfoGuiPanel(Analyzer.STRING_COUNT);
        debugGuiPanel.setPosition(positionX,
                Manager.getService(Render.class).getHeight() - Analyzer.STRING_COUNT*LABEL_HEIGHT_DEBUG);
        getGuiLocationFrame().getGuiFrame().getContainer().add(debugGuiPanel);

        DebugInfoComponent debugInfoComponent = new DebugInfoComponent(debugGuiPanel);
        getMap().objAdd(GameObjectFactory.create(debugInfoComponent));
    }

    //TODO вынести в отдельный класс или упростить в новой системе компонент
    public class DebugInfoComponent extends QueueComponent {

        private final DebugInfoGuiPanel debugInfoGuiPanel;

        public DebugInfoComponent(DebugInfoGuiPanel debugInfoGuiPanel){
            this.debugInfoGuiPanel = debugInfoGuiPanel;
        }

        @Override
        protected void updateComponent(long delta) {
            if (ClientData.printAnalyzerInfo) {
                debugInfoGuiPanel.setText(Manager.getService(Analyzer.class).getAnalysisResult());
            } else {
                debugInfoGuiPanel.setText(Collections.nCopies(Analyzer.STRING_COUNT, ""));
            }
        }

        @Override
        protected void drawComponent() {}

        @Override
        public Class getComponentClass() {
            return DebugInfoComponent.class;
        }

        @Override
        public List<Class<? extends QueueComponent>> getPreliminaryUpdateComponents() {
            return Collections.emptyList();
        }

        @Override
        public List<Class<? extends QueueComponent>> getPreliminaryDrawComponents() {
            return Collections.emptyList();
        }
    }
}
