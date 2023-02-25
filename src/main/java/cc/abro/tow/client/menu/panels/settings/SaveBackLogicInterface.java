package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;

public interface SaveBackLogicInterface extends MouseReleaseBlockingListeners {
    public void saveChanges();
    public void clearChanges();
    public void changeSaveButtons();
    public boolean isChanged();
}
