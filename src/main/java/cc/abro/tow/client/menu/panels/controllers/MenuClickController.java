package cc.abro.tow.client.menu.panels.controllers;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.menu.panels.controllers.main.CloseChildPanelController;
import cc.abro.tow.client.menu.panels.gui.BlockingGuiPanel;
import cc.abro.tow.client.menu.panels.gui.BlockingGuiPanelWithButton;

import java.util.Set;

public abstract class MenuClickController<T extends GuiElementEvent> extends GuiElementController<T> {

    protected EventableGuiPanelElement<EventableGuiPanel> createBlockingPanelWithButton(String message, int sizeX, int sizeY) {
        BlockingGuiPanelWithButton guiPanel = new BlockingGuiPanelWithButton(message, sizeX, sizeY,
                getGuiElement().getComponent());
        EventableGuiPanelElement<EventableGuiPanel> guiElement = new EventableGuiPanelElement<>(
                guiPanel, Set.of(new CloseChildPanelController()));
        Vector2<Double> position = getGuiElement().getPosition();
        Manager.getService(GuiElementService.class).addGuiElementToLocationShiftedToCenter(guiElement,
                position.x.intValue(), position.y.intValue(),
                getGuiElement().getGameObject().getComponent(Position.class).location);
        return guiElement;
    }

    protected EventableGuiPanelElement<BlockingGuiPanel> createBlockingPanel(String message, int sizeX, int sizeY) {
        BlockingGuiPanel guiPanel = new BlockingGuiPanel(message, sizeX, sizeY, getGuiElement().getComponent());
        EventableGuiPanelElement<BlockingGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of(new CloseChildPanelController()));
        Vector2<Double> position = getGuiElement().getPosition();
        Manager.getService(GuiElementService.class).addGuiElementToLocationShiftedToCenter(guiElement,
                position.x.intValue(), position.y.intValue(),
                getGuiElement().getGameObject().getComponent(Position.class).location);
        return guiElement;
    }
}
