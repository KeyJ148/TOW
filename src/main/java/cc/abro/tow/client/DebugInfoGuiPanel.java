package cc.abro.tow.client;

import cc.abro.orchengine.gui.EventableGuiPanel;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.style.font.FontRegistry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class DebugInfoGuiPanel extends EventableGuiPanel {

    private static final int LABEL_LENGTH = 100;

    private final ArrayList<Label> labels = new ArrayList<Label>();

    public DebugInfoGuiPanel(int countLabel) {
        setFocusable(false);
        setStyle(createInvisibleStyle());
        setSize(1000, MENU_TEXT_FIELD_HEIGHT*2);

        for (int i = 0; i < countLabel; i++) {
            labels.add(new Label("", 0, i*LABEL_HEIGHT_DEBUG, LABEL_LENGTH, LABEL_HEIGHT_DEBUG));
            labels.get(i).getTextState().setFont(FontRegistry.ROBOTO_BOLD);
            labels.get(i).getTextState().setFontSize(LABEL_FONT_SIZE);
            add(labels.get(i));
        }

    }

    public void setText(List<String> texts){
        for(int i = 0; i < texts.size(); i++) {
            labels.get(i).getTextState().setText(texts.get(i));
        }
    }

}
