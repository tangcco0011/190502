package com.kgc.tangcco.util.path;

import javax.servlet.http.HttpServletRequest;

public class BasePath {
	public static String getPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	}
}
