package com.kgc.tangcco.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;

public class BaseServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("methodName");
		String name = null;
		String value = null;
		try {
			if (methodName != null && !methodName.isEmpty()) {
				Enumeration enumeration = request.getHeaderNames();
				System.out.println("请求头：");
				while (enumeration.hasMoreElements()) {
					name = (String) enumeration.nextElement();
					value = request.getHeader(name);
					if ("Content-Type".equalsIgnoreCase(name)) {
						System.out.println(name + " " + value);
						break;
					}
				}
				Method method;
				if (value.contains("application/json") || value.contains("text/plain")) {
					BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
					StringBuffer sb = new StringBuffer();
					String temp;
					while ((temp = br.readLine()) != null) {
						sb.append(temp);
					}
					br.close();
					//获取到的json字符串
					String acceptjson = new String(sb.toString());
					System.out.println(acceptjson);

					method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, String.class);
					method.invoke(this, request, response, acceptjson);
					method.setAccessible(true);
				} else {
					method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
					method.invoke(this, request, response);
					method.setAccessible(true);
				}

			}else {
				PrintWriter out = response.getWriter();
				out.println(this.getClass().getName() + "中参数methodName未赋值");
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
	public void print(HttpServletRequest request, HttpServletResponse response, String json) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}

	public String prefix() {
		return "/WEB-INF/jsp/";
	}

	public String suffix() {
		return ".jsp";
	}

	public void forward(HttpServletRequest request, HttpServletResponse response, String forward)
			throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(this.prefix());
		sb.append(forward);
		sb.append(suffix());
		request.getRequestDispatcher(sb.toString()).forward(request, response);
	}
}
