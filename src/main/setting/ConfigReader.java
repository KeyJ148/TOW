package main.setting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.Global;

public class ConfigReader {
	
	private String path;

	public ConfigReader(String fileName){
		this.path = "res/setting/" + fileName;
	}
	
	public String findString(String findName){
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(path));
			String s, varName;
			while (true){ 
				s = fileReader.readLine();
				
				if (s == null){
					break;
				}
				
				varName = s.substring(0, s.indexOf(' '));
				if (findName.equals(varName)){
					fileReader.close();
					return s.substring(s.indexOf('"')+1, s.lastIndexOf('"'));
				}
				
			}
			Global.error("No find setting: " + findName);
			fileReader.close();
			return "";
		} catch (IOException e){
			Global.error("Exception in read " + path);
		}
		Global.error("No find setting: " + findName);
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
