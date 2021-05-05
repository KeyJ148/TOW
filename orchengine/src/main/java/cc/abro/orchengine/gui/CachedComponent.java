package cc.abro.orchengine.gui;

import cc.abro.orchengine.gameobject.components.render.CachedGuiElement;
import org.liquidengine.legui.component.Component;

/**
 * Это обертка для {@link Component}, который может быть закеширован в {@link CachedGuiPanelStorage}.
 * Подробнее в {@link CachedGuiElement}
 *
 * @param <T> тип компонента, для которого выполнена обертка
 */
public interface CachedComponent<T extends Component> {

    void setCachedGuiElementOnActiveLocation(CachedGuiElement<T> cachedGuiElementOnActiveLocation);

    CachedGuiElement<T> getCachedGuiElementOnActiveLocation();

    T getComponent();
}
