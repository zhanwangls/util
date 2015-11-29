# util
java util

### VarificationCodeUtil    验证码生成工具类
###### 调用方式：
1. 纯数字验证码：   
  VarificationCodeUtil.getNumberCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),outStream);
2. 数字+大写字母验证码：  
  VarificationCodeUtil.getNumberUpperCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),outStream);
3. 数字+小写字母验证码：  
  VarificationCodeUtil.getNumberLowerCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),outStream);
4. 小写字母+大写字母验证码：  
  VarificationCodeUtil.getLowerUpperCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),outStream);
5. 数字+大写字母+小写字母验证码：  
  VarificationCodeUtil.getNumberLowerUpperCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),outStream);
  
  
### VarificationMailUtil    邮件发送工具类
###### 对jdk的javax.mail进行封装。使用前请先下载[mail.jar](http://www.oracle.com/technetwork/java/index-138643.html)
###### 调用方式：
  VarificationMailUtil.sendHtmlMail(mailServiceHost, mailServicePort, formAddress, formUserName, formPassword, toAddress, subject, htmlContent);
