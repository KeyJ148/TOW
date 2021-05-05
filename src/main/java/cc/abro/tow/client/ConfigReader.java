package cc.abro.tow.client;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.resources.ResourceLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);

    public static final String PATH_SETTING_DIR = "res/game-settings/";
    private String path;

    public ConfigReader(String fileName) {
        this.path = PATH_SETTING_DIR + fileName;
    }

    public String findString(String findName) {
        try {
            BufferedReader fileReader = ResourceLoader.getResourceAsBufferedReader(path);
            String s, varName;
            while (true) {
                s = fileReader.readLine();

                if (s == null) {
                    break;
                }

                if ((s.length() != 0) && (s.charAt(0) != '#')) {
                    varName = s.substring(0, s.indexOf(' '));
                    if (findName.equals(varName)) {
                        fileReader.close();
                        return s.substring(s.indexOf('"') + 1, s.lastIndexOf('"'));
                    }
                }

            }
            log.error("No find setting: " + findName);
            fileReader.close();
            return "";
        } catch (IOException e) {
            log.error("Exception in read " + path);
        }
        log.error("No find setting: " + findName);
        return "";
    }

    public int findInteger(String findName) {
        String s = findString(findName);
        if (s.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    public double findDouble(String findName) {
        String s = findString(findName);
        if (s.equals("")) {
            return 0;
        } else {
            return Double.parseDouble(s);
        }
    }

    public boolean findBoolean(String findName) {
        String result = findString(findName);
        if (result.equals("True") || result.equals("true") || result.equals("TRUE") || result.equals("T"))
            return true;
        return false;
    }
}

