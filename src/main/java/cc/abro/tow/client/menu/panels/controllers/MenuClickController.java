package cc.abro.tow.client.menu.panels.controllers;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.menu.panels.controllers.main.CloseChildPanelController;
import cc.abro.orchengine.gui.BlockingGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ButtonBlockingGuiPanel;
import cc.abro.tow.client.menu.panels.gui.FirstEntryGuiPanel;
import cc.abro.tow.client.menu.panels.gui.LabelBlockingGuiPanel;

import java.util.Set;

public abstract class MenuClickController<T extends GuiElementEvent> extends GuiElementController<T> {

    protected EventableGuiPanelElement<ButtonBlockingGuiPanel> createButtonBlockingPanel (String message, int sizeX, int sizeY) {
        ButtonBlockingGuiPanel guiPanel = new ButtonBlockingGuiPanel(message, sizeX, sizeY,
                getGuiElement().getComponent());
        return addBlockingGuiPanel(guiPanel);
    }

    protected EventableGuiPanelElement<LabelBlockingGuiPanel> createLabelBlockingPanel (String message, int sizeX, int sizeY) {
        LabelBlockingGuiPanel guiPanel = new LabelBlockingGuiPanel(message, sizeX, sizeY,
                getGuiElement().getComponent());
        return addBlockingGuiPanel(guiPanel);
    }

    protected EventableGuiPanelElement<FirstEntryGuiPanel> createFirstEntryPanel () {
        FirstEntryGuiPanel guiPanel = new FirstEntryGuiPanel(getGuiElement().getComponent());
        return addBlockingGuiPanel(guiPanel);
    }

    private <P extends BlockingGuiPanel> EventableGuiPanelElement<P> addBlockingGuiPanel(P panel) {
        EventableGuiPanelElement<P> guiElement = new EventableGuiPanelElement<>(
                panel, Set.of(new CloseChildPanelController()));
        Vector2<Double> position = getGuiElement().getPosition();
        Manager.getService(GuiElementService.class).addGuiElementToLocationShiftedToCenter(guiElement,
                position.x.intValue(), position.y.intValue(),
                getGuiElement().getGameObject().getLocation());
        return guiElement;
    }
}
