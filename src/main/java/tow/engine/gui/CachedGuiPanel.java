package tow.engine.gui;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.ToggleButton;
import tow.engine.gameobject.components.render.CachedGuiElement;

/**
 * Абстрактный класс от которого наследуются классы готовых панелей меню
 */
public abstract class CachedGuiPanel extends Panel implements CachedComponent<Panel> {

    private CachedGuiElement<Panel> cachedGuiElementOnActiveLocation;

    @Override
    public void setCachedGuiElementOnActiveLocation(CachedGuiElement<Panel> cachedGuiElementOnActiveLocation) {
        this.cachedGuiElementOnActiveLocation = cachedGuiElementOnActiveLocation;
    }

    @Override
    public CachedGuiElement<Panel> getCachedGuiElementOnActiveLocation() {
        return cachedGuiElementOnActiveLocation;
    }

    @Override
    public Panel getComponent() {
        return this;
    }

    /**
     * Инициализация панели с элементами интерфейса. Должна вызываться из самого дочернего элемента в конструкторе,
     * чтобы инициализировать дефолтные стили.
     */
    public abstract void init();

    public void addComponent(Component component, int x, int y, int width, int height) {
        component.setPosition(x - width / 2, y - height / 2);
        component.setSize(width, height);
        add(component);
    }

    public void addComponentLU(Component component, int x, int y, int width, int height) {
        component.setPosition(x, y);
        component.setSize(width, height);
        add(component);
    }

    public void addToggleButton(int x, int y, int width, int height) {
        ToggleButton toggleButton = new ToggleButton();
        addComponent(toggleButton, x, y, width, height);
    }
}
