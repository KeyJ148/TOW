package cc.abro.orchengine.localization;


// В задачи данного сервиса входит хранение блоков ключ-значение

import cc.abro.orchengine.resources.JsonContainerLoader;
import org.picocontainer.Startable;

import java.io.IOException;

public class LocalizationService{

    private static String path;
    private static Localization localization = new Localization();

    public static void loadlocalization(String language) throws IOException {
        setPath(language);
        localization.setLanguage(language);

        try {
            localization = JsonContainerLoader.loadInternalFile(Localization.class, path);
        }catch (Exception e){
            System.err.println("Error with loading localization: " + e);
        }
    }

    private static void setPath(String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("locale/").append(language).append(".json");
        path = stringBuilder.toString();
    }

    public String getStringToken(String tokenName){
        return localization.getText(tokenName);
    }

}
