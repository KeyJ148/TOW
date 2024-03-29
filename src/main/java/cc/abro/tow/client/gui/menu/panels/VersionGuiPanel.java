package cc.abro.tow.client.gui.menu.panels;

import cc.abro.orchengine.context.Context;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.settings.DevSettingsService;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.style.font.FontRegistry;

public class VersionGuiPanel extends MenuGuiPanel {

    private final static String DEV_VERSION = "vX.X.X-dev";

    public VersionGuiPanel() {
        setStyle(InterfaceStyles.createInvisibleStyle());
        setSize(1000, InterfaceStyles.LABEL_HEIGHT_VERSION*2);
        Label label = new Label(getVersion(), 0, 0, InterfaceStyles.LABEL_LENGTH_VERSION, InterfaceStyles.LABEL_HEIGHT_VERSION);
        label.getStyle().setFont(FontRegistry.ROBOTO_BOLD);
        label.getStyle().setFontSize(InterfaceStyles.ANALYZER_INFO_FONT_SIZE);
        label.getStyle().setTextColor(InterfaceStyles.BLACK_COLOR);
        add(label);
    }

    private String getVersion() {
        String versionFromDevSettings = Context.getService(DevSettingsService.class).getDevSettings().getVersion();
        return versionFromDevSettings != null ? versionFromDevSettings : DEV_VERSION;
    }
}
