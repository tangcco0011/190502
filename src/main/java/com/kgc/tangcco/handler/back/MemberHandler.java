package com.kgc.tangcco.handler.back;

import com.google.gson.Gson;
import com.kgc.tangcco.pojo.Member;
import com.kgc.tangcco.util.email.EmailSendUtils;
import com.kgc.tangcco.util.path.BasePath;
import com.kgc.tangcco.util.servlet.BaseServlet;
import com.kgc.tangcco.util.validate.ValidateUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Classname MemberHandler
 * @Description TODO
 * @Date 2019/4/1 10:19
 * @Created by 李昊哲
 */
// @WebServlet("member.action")
public class MemberHandler extends BaseServlet {
    /**
     * @param 用户详细信息
     * @Description 用户注册
     * @date 2019/4/1 10:20
     * @auther 李昊哲
     */
    public void reg(HttpServletRequest request, HttpServletResponse response,String param) {
        Gson gson = new Gson();
        Member member = gson.fromJson(param,Member.class);
        // 1、取得前端的参数
        String email = member.getEmail();
        String password = member.getPassword();
        String phone = member.getPhone();
        String license = UUID.randomUUID().toString();
        Map<String,Object> map = new HashMap<String,Object>();
        // 2、验证数据
        if (ValidateUtils.validateEmpty(email) && ValidateUtils.validateEmpty(password)&& ValidateUtils.validateEmpty(phone)) {
            // 3、将用户注册的参数保存在对象中
            member.setPassword(new com.kgc.tangcco.util.md5.MD5Code().getMD5ofStr(password));
            member.setLicense(license); // 激活码
            member.setStatus(0); // 待激活状态
            member.setPhoto("myPhoto.jpg");
            member.setRegdate(new Date());
            try {
                if (EmailSendUtils.sendEmail(email, BasePath.getPath(request) + "/member.action?methodName=active", email, license)) {
                    map.put("member",member);
                    map.put("regStatus",1);
                    print(request,response,gson.toJson(map));
                }else {
                    map.put("regStatus",0);
                    print(request,response,gson.toJson(map));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("regStatus",2);
            print(request,response,gson.toJson(map));
        }
    }
    public void active(HttpServletRequest request,HttpServletResponse response){
        String email = request.getParameter("mid");
        String license = request.getParameter("code");
        System.out.println(email);
        System.out.println(license);
        try {
            response.sendRedirect("http://www.tangccolay.com:5500/html/user/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
