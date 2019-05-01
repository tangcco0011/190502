package com.kgc.tangcco.util.spring;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;

public class BaseSpring {

	public String[] getParamNames(Object object) {
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		Method[] methods = object.getClass().getDeclaredMethods();
		String[] params = u.getParameterNames(methods[0]);
		return params;
	}
}
