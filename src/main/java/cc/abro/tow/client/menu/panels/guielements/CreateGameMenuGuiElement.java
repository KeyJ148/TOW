package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.HostingListener;
import cc.abro.tow.client.menu.panels.events.CreateGameMenuGuiEvent;
import cc.abro.tow.client.menu.panels.gui.CreateGameMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.PrintErrorGuiPanel;

import static cc.abro.tow.client.menu.panels.events.CreateGameMenuGuiEvent.CreateGameMenuGuiEventType.CLICK_BUTTON_BACK;
import static cc.abro.tow.client.menu.panels.events.CreateGameMenuGuiEvent.CreateGameMenuGuiEventType.CLICK_BUTTON_CREATE;

public class CreateGameMenuGuiElement extends EventableGuiElement<CreateGameMenuGuiPanel> {

    private final HostingListener hostingListener;

    public CreateGameMenuGuiElement(CreateGameMenuGuiPanel component) {
        super(component);
        component.addListener(this);
        hostingListener = new HostingListener(error -> new PrintErrorGuiPanel(error.getText(), component));
    }

    @Override
    public void processEvent(GuiElementEvent event) {
        if(event.getType() == CLICK_BUTTON_BACK) {
            MainMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(MainMenuGuiPanel.class);
            MainMenuGuiElement guiElement = new MainMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }
        if(event.getType() == CLICK_BUTTON_CREATE) {
            CreateGameMenuGuiEvent reinterpret_event = (CreateGameMenuGuiEvent) event;
            hostingListener.host("0", reinterpret_event.maxPeople);
        }
    }
}
