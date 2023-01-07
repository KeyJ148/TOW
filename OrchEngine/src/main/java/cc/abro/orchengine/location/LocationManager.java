package cc.abro.orchengine.location;


import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.input.keyboard.KeyboardHandler;
import cc.abro.orchengine.input.mouse.MouseHandler;

import java.util.HashSet;
import java.util.Set;

@EngineService
public class LocationManager {

    private Location activeLocation;
    private Set<Location> updatedLocations = new HashSet<>();

    public Location getActiveLocation() {
        return activeLocation;
    }

    public void setActiveLocation(Location location) {
        setActiveLocation(location, true);
    }

    //Сделать локацию активной (update и render), одновременно может быть максимум одна активная локация
    public void setActiveLocation(Location location, boolean saveInput) {
        //Перенести нажатые клавиши и настройки мыши/курсора или нет
        GuiLocationFrame newFrame = location.getGuiLocationFrame();
        if (activeLocation != null && saveInput) {
            GuiLocationFrame oldFrame = activeLocation.getGuiLocationFrame();
            newFrame.setKeyboard(new KeyboardHandler(newFrame.getGuiFrame(), oldFrame.getKeyboard()));
            newFrame.setMouse(new MouseHandler(newFrame.getGuiFrame(), oldFrame.getMouse()));
        } else {
            newFrame.setKeyboard(new KeyboardHandler(newFrame.getGuiFrame()));
            newFrame.setMouse(new MouseHandler(newFrame.getGuiFrame()));
        }
        activeLocation = location;
        newFrame.setFocus();
    }

    public Set<Location> getUpdatedLocations() {
        return new HashSet<>(updatedLocations);
    }

    public void setUpdatedLocations(Set<Location> locations) {
        updatedLocations = new HashSet<>(locations);
    }

    public void addUpdatedLocation(Location location) {
        updatedLocations.add(location);
    }

    public void removeUpdatedLocation(Location location) {
        updatedLocations.remove(location);
    }
}
