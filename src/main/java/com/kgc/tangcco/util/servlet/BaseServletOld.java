package com.kgc.tangcco.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class BaseServletOld extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("methodName");
		if (methodName != null && !methodName.isEmpty()) {
			try {
				Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
				method.invoke(this, request, response);
				method.setAccessible(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			PrintWriter out = response.getWriter();
			out.println(this.getClass().getName() + "中参数methodName未赋值");
			out.flush();
			out.close();
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
