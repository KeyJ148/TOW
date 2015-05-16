package main.setting;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.Global;

public class ConfigReader {
	
	private String path;
	private BufferedReader fileReader;

	public ConfigReader(String fileName){
		this.path = "setting/" + fileName;
		
		try {
			this.fileReader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			Global.error("File not found " + path);
		}
	}
	
	public String findString(String findName){
		try {
			String s, varName;
			while (true){ 
				s = fileReader.readLine();
				
				if (s == null){
					break;
				}
				
				varName = s.substring(0, s.indexOf(' '));
				if (findName.equals(varName)){
					return s.substring(s.indexOf('"')+1, s.lastIndexOf('"'));
				}
				
			}
			
		} catch (IOException e){
			Global.error("Exception in read " + path);
		}
		return "";
	}
	
	public int findInteger(String findName){
		return Integer.parseInt(findString(findName));
	}
	
	public boolean findBoolean(String findName){
		String result = findString(findName);
		if (result.equals("True") || result.equals("true") || result.equals("T")) 
			return true;
		return false;
	}
	
	public void close(){
		try {
			fileReader.close();
		} catch (IOException e) {
			Global.error("Exception in close reader " + path);
		}
	}
	
}
