package com.kgc.tangcco.util.properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author 作者 : 李昊哲
 * @version 创建时间：2019年3月21日 下午1:55:19 类说明
 */
public class BaseProperties {
	public static String getProperties(String filePath, String keyWord) {
		Properties prop = null;
		String value = null;
		try {
			// 通过Spring中的PropertiesLoaderUtils工具类进行获取
			prop = PropertiesLoaderUtils.loadAllProperties(filePath);
			// prop = PropertiesLoaderUtils.loadAllProperties("ftpconfig.properties");
			// 根据关键字查询相应的值
			value = prop.getProperty(keyWord);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
