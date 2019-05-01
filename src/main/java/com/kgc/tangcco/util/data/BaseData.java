package com.kgc.tangcco.util.data;

import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 : 李昊哲
 * @version 创建时间：2019年3月20日 下午3:53:44 类说明
 */
public class BaseData {
	public static String dataMap(List list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (list.isEmpty()) {
			map.put("code", 0);
			map.put("msg", null);
		} else {
			map.put("code", 1);
			map.put("msg", "OK");
			map.put("count", list.size());
			map.put("data", list);
		}
		return JSON.toJSONString(map);
	}

	public static String dataList(List list) {
		return JSON.toJSONString(list);
	}
}
