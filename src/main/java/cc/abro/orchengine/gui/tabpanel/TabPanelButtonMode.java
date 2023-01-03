package cc.abro.orchengine.gui.tabpanel;

import org.joml.Vector2f;

import java.util.List;

public interface TabPanelButtonMode {
    void updateButtons(List<TabPanel.TiedButtonPanel> content, Vector2f panelSize);
}
