package cc.abro.tow.client;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.gui.GuiElement;
import cc.abro.orchengine.map.Location;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.services.GuiElementService;

import java.util.Collections;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class GameLocation extends Location {

    public GameLocation(int width, int height) {
        super(width, height);
    }

    protected GuiElement<DebugInfoGuiPanel> createDebugPanel(int positionX) {
        DebugInfoGuiPanel debugGuiPanel = new DebugInfoGuiPanel(Analyzer.STRING_COUNT);
        GuiElement<DebugInfoGuiPanel> debugGuiElement = new GuiElement<>(debugGuiPanel) {
            @Override
            public void updateComponent(long delta) {
                super.updateComponent(delta);
                if (ClientData.printAnalyzerInfo) {
                    getComponent().setText(Manager.getService(Analyzer.class).getAnalysisResult());
                } else {
                    getComponent().setText(Collections.nCopies(Analyzer.STRING_COUNT, ""));
                }
            }
        };
        Manager.getService(GuiElementService.class).addGuiElementToLocation(debugGuiElement,
                positionX, Manager.getService(Render.class).getHeight() - Analyzer.STRING_COUNT*LABEL_HEIGHT_DEBUG, this);
        return debugGuiElement;
    }
    protected GuiElement<GameTabGuiPanel> createTabPanel() {
        GameTabGuiPanel gameTabGuiPanel = new GameTabGuiPanel(GameServer.peopleMax);
        GuiElement<GameTabGuiPanel> gameTabGuiElement = new GuiElement<>(gameTabGuiPanel){
            @Override
            public void updateComponent(long delta) {
                super.updateComponent(delta);
                if (ClientData.showGameTabMenu) {
                    getComponent().setSize(0, 0);
                } else {
                    getComponent().setSize(TAB_SIZE_X, (TAB_LINE_SIZE_Y + 2) * (GameServer.peopleNow - GameServer.disconnect + 1) - 2);

                }
            }
        };
        Manager.getService(GuiElementService.class).addGuiElementToLocation(gameTabGuiElement,
                (Manager.getService(Render.class).getWidth() - TAB_SIZE_X)/2, (Manager.getService(Render.class).getHeight() - TAB_LINE_SIZE_Y)/2, this);
        return gameTabGuiElement;
    }
}
