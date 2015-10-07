package com.weimini.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @作者 qiuhongba
 * @创建日期 2011-8-18
 * @版本 V 1.0
 *
 */
public class SystemConfig {
	
	private static Properties configProperties = new Properties();
	
	static{
		init();
	}
	
	
	/**
	 * 初始化配置文件
	 */
	public static synchronized void init() {
		configProperties.clear();
		if(configProperties.isEmpty()) {
			try {
				InputStream inputStream = new FileInputStream(new File(SystemConfig.class.getResource("/").getPath().replaceAll("%20", " ").replace("/classes/", "/config/config.properties")));
				configProperties.load(inputStream);
				
				/*InputStream inputStream1 = new FileInputStream(new File(SystemConfig.class.getResource("/").getPath().replaceAll("%20", " ").replace("/classes/", "/config/config_product.properties")));
				Properties configProducts = new Properties();
				configProducts.load(inputStream1);
				Iterator it = configProducts.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry entry = (Entry) it.next();
					configProperties.put(entry.getKey(), entry.getValue());
				}*/
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据key得到key所对应的value
	 * @param key 
	 * @return value
	 */
	public static String getValue(String key) {
		return configProperties.getProperty(key);
	}
	
	/**
	 * 根据key得到key所对应的value,如果得不到返回defaultValue
	 * @param key key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public static String getValue(String key, String defaultValue) {
		return configProperties.getProperty(key, defaultValue);
	}
	
	/**
	 * 判断项目运行的模式,是否是生产模式
	 * @return
	 */
	public static boolean isProduct() {
		boolean isProduct = false;
		String runMode = SystemConfig.getValue("runMode");
		if("product".equals(runMode)) {
			isProduct = true;
		}
		return isProduct;
	}
	
	

}
