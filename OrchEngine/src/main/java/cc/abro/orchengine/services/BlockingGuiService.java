package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;
import com.spinyowl.legui.component.Component;

import java.util.HashSet;
import java.util.Set;

@EngineService
public class BlockingGuiService {

    public GuiBlock createGuiBlock(Component component) {
        return new BlockingGuiService.GuiBlock(component);
    }

    public final static class GuiBlock {
        private final Set<Component> unfocusedComponents = new HashSet<>();

        public GuiBlock(Component blockableComponent) {
            blockAndAddToSet(blockableComponent);
        }

        public Set<Component> getUnfocusedComponents() {
            return unfocusedComponents;
        }

        public void unblock() {
            getUnfocusedComponents().iterator().forEachRemaining(c -> c.setFocusable(true));
        }

        private void blockAndAddToSet(Component blockableComponent) {
            if (blockableComponent.isFocusable()) {
                unfocusedComponents.add(blockableComponent);
                blockableComponent.setFocusable(false);
            }

            for (Component childComponent : blockableComponent.getChildComponents()) {
                blockAndAddToSet(childComponent);
            }
        }
    }
}
