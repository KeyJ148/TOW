package cc.abro.orchengine.gui.tabpanel.modes;

import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.gui.tabpanel.TabPanelButtonMode;
import org.joml.Vector2f;
import com.spinyowl.legui.style.border.SimpleLineBorder;

import java.util.List;

public class AlignAllTabPanelButtonMode implements TabPanelButtonMode {

    @Override
    public void updateButtons(List<TabPanel.TiedButtonPanel> content, Vector2f panelSize) {
        for (int i = 0; i < content.size(); i++) {
            TabPanel.TiedButtonPanel pair = content.get(i);
            float thickness = pair.button().getStyle().getBorder() == null ? 0 : ((SimpleLineBorder) pair.button().getStyle().getBorder()).getThickness();
            pair.button().setSize(
                    (panelSize.x - thickness * (content.size() + 1)) / content.size(),
                    pair.button().getSize().y);
            pair.button().setPosition(
                    pair.button().getSize().x * i + thickness * (i + 1),
                    thickness);
        }
    }
}
