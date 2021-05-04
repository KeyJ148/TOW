package tow.engine.gameobject.components.render;

import org.liquidengine.legui.component.Component;
import tow.engine.gui.CachedComponent;
import tow.engine.gui.CachedGuiPanelStorage;

/**
 * Хранит обертку для кеширования {@link Component}. Сделано для того, чтобы сохранить возможность замораживать и
 * размораживать локации с игровыми объектами, содержащими {@link GuiElement}
 * с объектом из {@link CachedGuiPanelStorage} внутри. Таким образом в {@link CachedComponent}
 * всегда поддерживается актуальная ссылка на один {@link GuiElement} из текущей активной комнаты.
 */
public class CachedGuiElement<T extends Component> extends GuiElement<T> {

    private final CachedComponent<T> cachedComponent;

    public CachedGuiElement(CachedComponent<T> cachedComponent) {
        this(cachedComponent, false);
    }

    public CachedGuiElement(CachedComponent<T> cachedComponent, boolean moveComponentToGameObjectPosition) {
        super(cachedComponent.getComponent(), moveComponentToGameObjectPosition);
        this.cachedComponent = cachedComponent;
        cachedComponent.setCachedGuiElementOnActiveLocation(this);
    }

    public CachedGuiElement(CachedComponent<T> cachedComponent, int weight, int height) {
        this(cachedComponent, false, weight, height);
    }

    public CachedGuiElement(CachedComponent<T> cachedComponent, boolean moveComponentToGameObjectPosition, int weight, int height) {
        super(cachedComponent.getComponent(), moveComponentToGameObjectPosition, weight, height);
        this.cachedComponent = cachedComponent;
        cachedComponent.setCachedGuiElementOnActiveLocation(this);
    }

    @Override
    public void unfreeze() {
        cachedComponent.setCachedGuiElementOnActiveLocation(this);
    }
}
