package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Loader;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.tow.client.menu.panels.gui.*;

import static cc.abro.tow.client.menu.panels.events.MainMenuGuiEvent.MainMenuGuiEventType.*;

public class MainMenuGuiElement extends EventableGuiElement<MainMenuGuiPanel> {

    public MainMenuGuiElement(MainMenuGuiPanel component) {
        super(component);
        component.addListener(this); //TODO поменять весь класс на лямбду? Или анонимный класс.
        // TODO Запихать MainMenuGuiEvent как внутренний класс?
        // TODO  Избавиться от лишнего слоя в типе эвента. Но если надо передавать доп. данные в эвенте?
        // TODO вынести конструктор такой в общий класс?
    }

    @Override
    public void processEvent(GuiElementEvent event) {
        if (event.getType() == CLICK_CONNECT){
            ConnectMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ConnectMenuGuiPanel.class);
            ConnectMenuGuiElement guiElement = new ConnectMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }
        if(event.getType() == CLICK_CREATE_GAME) {
            CreateGameMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(CreateGameMenuGuiPanel.class);
            CreateGameMenuGuiElement guiElement = new CreateGameMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }
        if(event.getType() == CLICK_SETTINGS) {
            SettingsMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(SettingsMenuGuiPanel.class);
            SettingsMenuGuiElement guiElement = new SettingsMenuGuiElement(guiPanel);
            destroyAndCreateGuiElement(guiElement);
        }
        if (event.getType() == CLICK_EXIT){
            Loader.exit();
        }
    }
}
