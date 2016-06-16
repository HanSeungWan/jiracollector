package com.daou.jiracollector.jiradatamaker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertiesController {

	/**
	 * properties 파일 위치
	 */
	private static final String PROPERTIES_FILE = "C:\\Users\\intern\\IdeaProjects\\jiracollector\\src\\main\\resources\\Jiracollector.properties";

	/**
	 * property 에서 key에 따라 값을 불러 오는 메서드
	 *
	 * @param keyName
	 *            properties 파일 key 값
	 * @return key 값에 해당 하는 value
	 */
	public String getProperty(String keyName) {
		String value = null;

		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
			props.load(new java.io.BufferedInputStream(fis));
			value = props.getProperty(keyName).trim();
			fis.close();
		} catch (java.lang.Exception e) {
			System.out.println(e.toString());
		}
		return value;
	}

	public void setProperty(String keyName, String value) {
		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
			props.load(new java.io.BufferedInputStream(fis));
			props.setProperty(keyName, value);
			props.store(new FileOutputStream(PROPERTIES_FILE), "");
			fis.close();
		} catch (java.lang.Exception e) {
			System.out.println(e.toString());
		}
	}
}
