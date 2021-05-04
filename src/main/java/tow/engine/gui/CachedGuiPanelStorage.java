package tow.engine.gui;

import tow.engine.Global;
import tow.engine.Loader;
import tow.engine.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс кеширующий готовые панели меню, чтобы сохранять все вносимые изменения в интерфейс
 */
public class CachedGuiPanelStorage {

    private final Map<String, CachedGuiPanel> cachedGuiPanelByName = new HashMap<>();

    public void registry(CachedGuiPanel cachedGuiPanel) {
        registry(cachedGuiPanel.getClass().getCanonicalName(), cachedGuiPanel);
    }

    public void registry(String name, CachedGuiPanel cachedGuiPanel) {
        if (cachedGuiPanelByName.containsKey(name)) {
            Global.logger.println("CachedGuiPanel \"" + name + "\" already exists", Logger.Type.ERROR);
            Loader.exit();
        }
        cachedGuiPanelByName.put(name, cachedGuiPanel);
    }

    public <T> T getPanel(Class<T> cachedGuiPanelClass) {
        return getPanel(cachedGuiPanelClass.getCanonicalName());
    }

    @SuppressWarnings("unchecked")
    public <T> T getPanel(String name) {
        if (!cachedGuiPanelByName.containsKey(name)) {
            Global.logger.print("CachedGuiPanel \"" + name + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return (T) cachedGuiPanelByName.get(name);
    }
}
