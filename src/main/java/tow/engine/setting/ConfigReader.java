package tow.engine.setting;

import tow.engine.Global;
import tow.engine.logger.Logger;
import tow.engine.resources.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;

public class ConfigReader {

	public static final String PATH_SETTING_DIR = "res/game-settings/";
	private String path;

	public ConfigReader(String fileName){
		this.path = PATH_SETTING_DIR + fileName;
	}

	public String findString(String findName){
		try {
			BufferedReader fileReader = ResourceLoader.getResourceAsBufferedReader(path);
			String s, varName;
			while (true){
				s = fileReader.readLine();

				if (s == null){
					break;
				}

				if ((s.length() != 0) && (s.charAt(0) != '#')){
					varName = s.substring(0, s.indexOf(' '));
					if (findName.equals(varName)){
						fileReader.close();
						return s.substring(s.indexOf('"')+1, s.lastIndexOf('"'));
					}
				}

			}
			Global.logger.println("No find setting: " + findName, Logger.Type.ERROR);
			fileReader.close();
			return "";
		} catch (IOException e){
			Global.logger.println("Exception in read " + path, Logger.Type.ERROR);
		}
		Global.logger.println("No find setting: " + findName, Logger.Type.ERROR);
		return "";
	}

	public int findInteger(String findName){
		String s = findString(findName);
		if (s.equals("")){
			return 0;
		} else {
			return Integer.parseInt(s);
		}
	}

	public double findDouble(String findName){
		String s = findString(findName);
		if (s.equals("")){
			return 0;
		} else {
			return Double.parseDouble(s);
		}
	}

	public boolean findBoolean(String findName){
		String result = findString(findName);
		if (result.equals("True") || result.equals("true") || result.equals("TRUE") || result.equals("T"))
			return true;
		return false;
	}
}

