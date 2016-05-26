package com.nilsonmassarenti.test.docler.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * This Class ManagerFile is responsible to manipulate files 
 * @author Nilson Massarenti
 * @version 0.1
 */
public class ManagerFile {
	private Properties prop = ReadProperties.getProperties();
	private String hostName;
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	/**
	 * This method is responsible to create a file
	 * @param command String - Alias of file
	 * @return Boolean - check if file was created
	 */
	public Boolean createFile(String command){
		try {
			File file = new File(prop.getProperty("prop.ping.saveDir") + command + "_" + hostName);
			FileWriter pingFile = new FileWriter(file);
			pingFile.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method is responsible to save a string in file
	 * @param command String - Type of command
	 * @param readLine String - the line to save in file
	 */
	public void saveInformationFile(String command, String readLine){
		try {
			File file = new File(prop.getProperty("prop.ping.saveDir") + command + "_" + hostName);
			FileWriter pingFile = new FileWriter(file, true);
			pingFile.write("\n" + readLine);
			pingFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
