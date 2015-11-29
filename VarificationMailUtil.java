package util;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * VarificationMailUtil
 * 文件用途：邮箱注册验证工具类
 * @author zhanwang  
 * @version 1.0.0
 * 创建时间：2015年11月20日-下午5:09:56
 * 使用方式：
 * VarificationMailUtil.sendHtmlMail(mailServiceHost, mailServicePort, formAddress, formUserName, formPassword, toAddress, subject, htmlContent);
 */
public class VarificationMailUtil {
	
	String userName = null;
	String passWord = null;
	
	/**
	 * (邮件发送的核心方法)
	 * 方法名：sendHtmlMail
	 * @author zhanwang 创建时间：2015年11月20日-下午9:28:31 
	 * @param mailServiceHost    服务器IP
	 * @param mailServicePort    服务器端口号
	 * @param formAddress        发送者IP
	 * @param formUserName       发送者用户名
	 * @param formPassword       发送者密码
	 * @param toAddress          接受者IP
	 * @param subject            发送主题
	 * @param htmlContent        发送html格式的内容
	 * @return boolean
	 * @exception 
	 * @since  1.0.0
	 */
	public static void sendHtmlMail(String mailServiceHost, String mailServicePort,String formAddress,String formUserName,String formPassword,String[] toAddress,String subject,String htmlContent) {
		/* 一、身份验证 */
			// 邮件会话信息
		Properties properties = new Properties();//与map用法相同
		properties.put("mail.smtp.host", mailServiceHost);//服务器的主机地址
		properties.put("mail.smtp.port", mailServicePort);//服务器的端口号
		properties.put("mail.smtp.auth", "true");//很重要。设置发送授权认证。如果不设置为true，则不能发送成功。
		/* 根据邮件会话属性和身份验证器构建一个发送邮件的session。 */
		Session mailSession = Session.getDefaultInstance(properties, new MyAuthenticator(formUserName,formPassword));
		try {
		/* 二、定义发送邮件的主体 */
			/* 1、根据session创建一个邮件消息 */
			Message message = new MimeMessage(mailSession);
			/* 2、设置发送者地址 */
			Address form = new InternetAddress(formAddress);
			message.setFrom(form);
			/* 3、设置接收者地址 */
			int len = toAddress.length;
			Address[] to = new InternetAddress[len];
			for (int i=0;i<len;i++) {
				to[i] = new InternetAddress(toAddress[i]);
			}
			message.setRecipients(Message.RecipientType.TO, to);
			/* 4、设置邮件主题 */
			message.setSubject(subject);
			/* 5、设置邮件发送时间 */
			message.setSentDate(new Date());
			/* 6、设置邮件的发送内容 */
				/* 先创建容器 */
			Multipart part = new MimeMultipart();
				/* 创建一个子容器，用来存储html内容，然后将该子容器放到容器中。 */
			BodyPart html = new MimeBodyPart();
			html.setContent(htmlContent, "text/html;charset=utf-8");
			part.addBodyPart(html);
				/* 将容器中的内容设置为邮件的内容 */
			message.setContent(part);
		/* 三、设置html格式邮件的信息头----固定格式 */
			MailcapCommandMap mc = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		    mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		    mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		    mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		    mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		    CommandMap.setDefaultCommandMap(mc);
		/* 四、发送邮件 */
		    Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}

/* 身份验证 */
class MyAuthenticator extends Authenticator {

	String userName = null;
	String passWord = null;
	
	public MyAuthenticator() {}

	// 传递用户名与密码
	public MyAuthenticator(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	// 用户名、密码验证：该类实例化之后，会自动调用该方法进行验证
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, passWord);
	}
	
}

