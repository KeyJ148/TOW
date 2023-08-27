package cc.abro.orchengine.gui;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.services.AnnotationScanService;
import cc.abro.orchengine.util.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import com.spinyowl.legui.component.Panel;
import org.picocontainer.Startable;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс кеширующий готовые панели меню, чтобы сохранять все вносимые изменения в интерфейс
 */
@Log4j2
@EngineService
@RequiredArgsConstructor
public class GuiPanelStorage {

    private final AnnotationScanService annotationScanService;

    private final Map<String, Panel> guiPanelByName = new HashMap<>();

    public void init() {
        var panels = ReflectionUtils.CreateInstances(annotationScanService.getClassesWithAnnotations(StoredGuiPanel.class));

        for(var panel : panels){
            if(panel instanceof Panel){
                registry((Panel)panel);
            }
        }
    }

    public void registry(Panel guiPanel) {
        registry(guiPanel.getClass().getCanonicalName(), guiPanel);
    }

    public void registry(String name, Panel guiPanel) {
        if (guiPanelByName.containsKey(name)) {
            log.error("GuiPanel \"" + name + "\" already exists");
            throw new IllegalStateException("GuiPanel \"" + name + "\" already exists");
        }
        guiPanelByName.put(name, guiPanel);
    }

    public <T> T getPanel(Class<T> guiPanelClass) {
        return getPanel(guiPanelClass.getCanonicalName());
    }

    @SuppressWarnings("unchecked")
    public <T> T getPanel(String name) {
        if (!guiPanelByName.containsKey(name)) {
            log.error("GuiPanel \"" + name + "\" not found");
            return null;
        }

        return (T) guiPanelByName.get(name);
    }
}
