package com.icbc.match.utils;

import java.util.ResourceBundle;

/**
 * 用于获取工程目录（src）下properties.properties里面的属性值
 *
 */
public class PropertiesUtil {

	/**
	 * 
	 * @param key properties文件属性名
	 * @return
	 */
	public static String getProperty(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("properties");
		return bundle.getString(key);
	}

}
