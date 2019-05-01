package com.kgc.tangcco.util.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

// +activeUrl+"?mid="+mid +"&code=" + code
public class EmailSendUtils {
	public static boolean sendEmail(String emailAddress, String activeUrl, String mid, String code) throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.host", "smtp.exmail.qq.com");// 设置邮件服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 设置邮件服务器是否需要登录认证
		Authenticator auth = new Authenticator() { // 创建认证器
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("lihaozhe@tangcco.com", "Admin0011"); // 用户名和密码
			}
		};
		Session session = Session.getInstance(props, auth); // 获取Session对象
		session.setDebug(true);

		/*** 2.创建邮件对象MimeMessage ***/
		MimeMessage msg = new MimeMessage(session); // 创建邮件对象
		msg.setFrom(new InternetAddress("lihaozhe@tangcco.com")); // 设置发件人
		msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailAddress)); // 设置收件人
		msg.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(emailAddress)); // 设置收件人（抄送）
		msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(emailAddress));// 设置收件人（暗送）
		msg.setSubject("用户邮箱账号激活");// 设置发送的邮件的标题
		if (activeUrl == "") {
			msg.setContent(code, "text/html;charset=utf-8");
		} else {
			msg.setContent("点击邮箱链接激活账号：<a href=" + activeUrl + "&mid=" + mid + "&code=" + code + ">" + "点击激活" + "</a>",
					"text/html;charset=utf-8"); // 指定邮件内容，以及内容的MIME类型
		}
		Transport.send(msg);
		return true;
	}
	/**
	 * 调用 EmailSendUtils.sendEmail(mid, activeUrl, mid, uuid);
	 */
}