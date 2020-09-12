package tech.abro.tow.client.menu;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.Loader;
import tech.abro.orchengine.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class MenuLocationStorage {

    private Map<Class<? extends MenuLocation>, MenuLocation> menuLocationByClass = new HashMap<>();

    public void registry(MenuLocation menuLocation){
        if (menuLocationByClass.containsKey(menuLocation.getClass())){
            Global.logger.println("MenuLocation \"" + menuLocation.getClass() + "\" already exists", Logger.Type.ERROR);
            Loader.exit();
        }
        menuLocationByClass.put(menuLocation.getClass(), menuLocation);
    }

    public <T> T getMenuLocation(Class<T> menuLocationClass){
        if (!menuLocationByClass.containsKey(menuLocationClass)) {
            Global.logger.print("MenuLocation \"" + menuLocationClass + "\" not found", Logger.Type.ERROR);
            return null;
        }

        return (T) menuLocationByClass.get(menuLocationClass);
    }

}
