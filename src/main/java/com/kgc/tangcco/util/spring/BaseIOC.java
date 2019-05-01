package com.kgc.tangcco.util.spring;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BaseIOC {
	public static Object[] getInstance(Set objectNameSet, Set attributeNameSet, Map<String, String> attributeMap) {
		// 获取传入类名字集合的迭代器
		Iterator<String> objectNameIterator = objectNameSet.iterator();

		Object object;
		Object[] objects = new Object[objectNameSet.size()];
		// 定义完整类名字前缀
		String prefixName = "com.kgc.tangcco.web.pojo.";
		while (objectNameIterator.hasNext()) {
			int j = 0;
			try {
				// 讲class文件加载到JVM
				Class clazz = Class.forName(prefixName + objectNameIterator.next());
				// 实例化对象
				object = clazz.newInstance();
				// 获取该class的属性数组
				Field[] Fields = clazz.getDeclaredFields();
				for (int i = 0; i < Fields.length; i++) {
					// AccessibleTest类中的成员变量为private,故必须进行此操作
					// 取消属性的访问权限控制，即使private属性也可以进行访问。

					Fields[i].setAccessible(true);
					//// 如果有属性名和key相同
					String name = Fields[i].getName();
					for (Iterator iterator = attributeNameSet.iterator(); iterator.hasNext();) {
						String string = (String) iterator.next();
						if (string.equals(name)) {
							Fields[i].set(object, attributeMap.get(name));
						}
					}

				}
				objects[j] = object;
				j++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return objects;
	}
}
