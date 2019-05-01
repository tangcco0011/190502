package com.kgc.tangcco.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("*.action")
public class CORS implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 强制转换参数的数据类型
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestMethod = req.getMethod();
        String remoteHost = req.getRemoteHost();
        System.out.println(remoteHost);
        System.out.println(requestMethod);
        if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
            // ajax跨域
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            resp.setHeader("Access-Control-Max-Age", "3600");
            resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            try {
                PrintWriter writer = resp.getWriter();
                writer.println();
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        } else if (!"OPTIONS".equalsIgnoreCase(requestMethod)) {
            // ajax跨域
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            resp.setHeader("Access-Control-Max-Age", "3600");
            resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            // 放行通过过滤器
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
