package cc.abro.tow.client.menu;

import org.apache.commons.lang3.tuple.Pair;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuGuiService {

    public static Panel createMenuPanel(ButtonConfiguration... buttonConfigurations) {
        List<String> textButtons = Arrays.stream(buttonConfigurations)
                .map(ButtonConfiguration::getText)
                .collect(Collectors.toList());
        List<MouseClickEventListener> eventListenerButtons = Arrays.stream(buttonConfigurations)
                .map(ButtonConfiguration::getEventListener)
                .collect(Collectors.toList());

        Pair<Panel, List<Button>> pair = MenuGuiComponents.createMenuPanel(textButtons);
        Panel menu = pair.getLeft();
        List<Button> buttons = pair.getRight();

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).getListenerMap().addListener(MouseClickEvent.class, eventListenerButtons.get(i));
        }
        return menu;
    }

    public static Button createMenuButton(String text, int x, int y, int width, int height,
                                          MouseClickEventListener mouseClickEventListener) {
        Button button = MenuGuiComponents.createMenuButton(text, x, y, width, height);
        button.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        return button;
    }

    public static Button createButton(String text, int x, int y, int width, int height,
                                      MouseClickEventListener mouseClickEventListener) {
        Button button = MenuGuiComponents.createButton(text, x, y, width, height);
        button.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        return button;
    }

    public static class ButtonConfiguration {

        private final String text;
        private final MouseClickEventListener eventListener;

        public ButtonConfiguration(String text, MouseClickEventListener eventListener) {
            this.text = text;
            this.eventListener = eventListener;
        }

        public String getText() {
            return text;
        }

        public MouseClickEventListener getEventListener() {
            return eventListener;
        }
    }
}
