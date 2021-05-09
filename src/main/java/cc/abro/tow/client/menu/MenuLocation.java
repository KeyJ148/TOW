package cc.abro.tow.client.menu;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gui.CachedGuiPanel;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.map.Location;
import cc.abro.orchengine.services.CachedGuiElementService;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

public class MenuLocation extends Location {

    public MenuLocation() {
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        background = new Background(new Color(0, 150, 14), Color.GREEN);

        CachedGuiPanel cachedGuiPanel = Global.cachedGuiPanelStorage.getPanel(MainMenuGuiPanel.class);
        new CachedGuiElementService().addCachedComponentToLocationShiftedToCenter(cachedGuiPanel,
                Global.engine.render.getWidth() / 2,
                Global.engine.render.getHeight() / 2,
                this);
    }
}
