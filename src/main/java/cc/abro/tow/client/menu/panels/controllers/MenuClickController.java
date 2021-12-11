package cc.abro.tow.client.menu.panels.controllers;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.BlockingGuiPanel;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.menu.panels.gui.ButtonBlockingGuiPanel;
import cc.abro.tow.client.menu.panels.gui.FirstEntryGuiPanel;
import cc.abro.tow.client.menu.panels.gui.LabelBlockingGuiPanel;

public abstract class MenuClickController<T extends GuiElementEvent> extends GuiElementController<T> {

    protected EventableGuiElement<ButtonBlockingGuiPanel> createButtonBlockingPanel (String message, int sizeX, int sizeY) {
        ButtonBlockingGuiPanel guiPanel = new ButtonBlockingGuiPanel(message, sizeX, sizeY,
                getGuiElement().getComponent());
        return addBlockingGuiPanel(guiPanel);
    }

    protected EventableGuiElement<LabelBlockingGuiPanel> createLabelBlockingPanel (String message, int sizeX, int sizeY) {
        LabelBlockingGuiPanel guiPanel = new LabelBlockingGuiPanel(message, sizeX, sizeY,
                getGuiElement().getComponent());
        return addBlockingGuiPanel(guiPanel);
    }

    protected EventableGuiElement<FirstEntryGuiPanel> createFirstEntryPanel () {
        FirstEntryGuiPanel guiPanel = new FirstEntryGuiPanel(getGuiElement().getComponent());
        return addBlockingGuiPanel(guiPanel);
    }

    private <P extends BlockingGuiPanel> EventableGuiElement<P> addBlockingGuiPanel(P panel) {
        EventableGuiElement<P> guiElement = new EventableGuiElement<>(panel);
        Vector2<Double> position = getGuiElement().getPosition();
        Manager.getService(GuiElementService.class).addGuiElementToLocationShiftedToCenter(guiElement,
                position.x.intValue(), position.y.intValue(),
                getGuiElement().getGameObject().getLocation());
        return guiElement;
    }
}
