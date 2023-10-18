package cc.abro.tow.client.gui.menu.panels.settings;

import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;

public interface SaveBackLogicInterface extends MouseReleaseBlockingListeners {
    void saveChanges();
    void clearChanges();
    void changeSaveButtons();
    boolean isChanged();
}
