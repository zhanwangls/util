package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 
 * VarificationCodeUtil
 * @author zhanwang  
 * @version 1.0.0
 * 创建时间：2015年11月19日-上午2:18:30
 * 文件用途：验证码工具类
 * 描述：生成验证码（有干扰线）：纯数字验证码、数字+大写字母验证码、数字+小写字母验证码、小写字母+大写字母验证码、数字+大写字母+小写字母验证码、四则运算验证码
 * 使用方式：
 * 	先创建输出流：ByteArrayOutputStream out = new ByteArrayOutputStream(); 
 *	1、纯数字验证码： VarificationCodeUtil.getNumberCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),out);
 *	2、数字+大写字母验证码：VarificationCodeUtil.getNumberUpperCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),out);
 *	3、数字+小写字母验证码：VarificationCodeUtil.getNumberLowerCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),out);
 *	4、小写字母+大写字母验证码：VarificationCodeUtil.getLowerUpperCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),out);
 *	5、数字+大写字母+小写字母验证码：VarificationCodeUtil.getNumberLowerUpperCode(4,true,60, 20,new Color(0,0,0),new Color(150,150,150),out);
 *	6、四则运算验证码：VarificationCodeUtil.getNumberOperationCode(60, 20,new Color(0,0,0),new Color(150,150,150),out);
 */
public class VarificationCodeUtil {
	
	//字符集合(除去易混淆的数字0、数字1、字母l、字母o、字母O) 
	private static final char[] codes={'1','2','3','4','5','6','7','8','9',  
									 'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z',  
									 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	/**
	 * 四则运算形式的验证码
	 * 方法名：getNumberOperationCode
	 * @author zhanwang 创建时间：2015年11月21日-下午11:04:18 
	 * @param w                 验证码宽度
	 * @param h					验证码高度
	 * @param backgroundColor   验证码背景色
	 * @param lineColor			验证码干扰线颜色
	 * @param output			验证码输出流
	 * @return String			result  用户输入时与之相比较的验证结果
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getNumberOperationCode(int w,int h,Color backgroundColor,Color lineColor,ByteArrayOutputStream output) {
		HashMap<String, Object> map = packNumberOperationResult();//生成随机验证码
		String content = map.get("a")+" "+map.get("operator")+" "+map.get("b")+" = ";
		packPicture(content, w, h, backgroundColor, lineColor, output);//将验证码绘制成图片以流的形式输出
		return map.get("result").toString();
	}
	
	/**
	 * 纯数字形式的验证码
	 * 方法名：getNumberCode
	 * @author zhanwang 创建时间：2015年11月21日-下午11:07:27 
	 * @param length			验证码位数
	 * @param isCanRepeat		是否可以有重复的数字
	 * @param w                 验证码宽度
	 * @param h					验证码高度
	 * @param backgroundColor   验证码背景色
	 * @param lineColor			验证码干扰线颜色
	 * @param output			验证码输出流
	 * @return String			result  用户输入时与之相比较的验证结果
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getNumberCode(int length,Boolean isCanRepeat,int w,int h,Color backgroundColor,Color lineColor,ByteArrayOutputStream output) {
		char[] usableCodes = Arrays.copyOfRange(codes, 0, 9);
		String result = packResult(usableCodes, length, isCanRepeat);
		packPicture(result, w, h, backgroundColor, lineColor, output);
		return result;
	}
	
	/**
	 * 数字+小写字母形式的验证码
	 * 方法名：getNumberLowerCode
	 * @author zhanwang 创建时间：2015年11月21日-下午11:07:27 
	 * @param length			验证码位数
	 * @param isCanRepeat		是否可以有重复的数字或字母
	 * @param w                 验证码宽度
	 * @param h					验证码高度
	 * @param backgroundColor   验证码背景色
	 * @param lineColor			验证码干扰线颜色
	 * @param output			验证码输出流
	 * @return String			result  用户输入时与之相比较的验证结果
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getNumberLowerCode(int length,Boolean isCanRepeat,int w,int h,Color backgroundColor,Color lineColor,ByteArrayOutputStream output) {
		char[] usableCodes = Arrays.copyOfRange(codes, 0, 33);
		String result = packResult(usableCodes, length, isCanRepeat);
		packPicture(result, w, h, backgroundColor, lineColor, output);
		return result;
	}
	
	/**
	 * 数字+大写字母形式的验证码
	 * 方法名：getNumberUpperCode
	 * @author zhanwang 创建时间：2015年11月21日-下午11:07:27 
	 * @param length			验证码位数
	 * @param isCanRepeat		是否可以有重复的数字或字母
	 * @param w                 验证码宽度
	 * @param h					验证码高度
	 * @param backgroundColor   验证码背景色
	 * @param lineColor			验证码干扰线颜色
	 * @param output			验证码输出流
	 * @return String			result  用户输入时与之相比较的验证结果
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getNumberUpperCode(int length,Boolean isCanRepeat,int w,int h,Color backgroundColor,Color lineColor,ByteArrayOutputStream output) {
		char[] usableCodes = {'1','2','3','4','5','6','7','8','9',  
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
		String result = packResult(usableCodes, length, isCanRepeat);
		packPicture(result, w, h, backgroundColor, lineColor, output);
		return result;
	}
	
	/**
	 * 小写字母+大写字母形式的验证码
	 * 方法名：getLowerUpperCode
	 * @author zhanwang 创建时间：2015年11月21日-下午11:07:27 
	 * @param length			验证码位数
	 * @param isCanRepeat		是否可以有重复的字母
	 * @param w                 验证码宽度
	 * @param h					验证码高度
	 * @param backgroundColor   验证码背景色
	 * @param lineColor			验证码干扰线颜色
	 * @param output			验证码输出流
	 * @return String			result  用户输入时与之相比较的验证结果
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getLowerUpperCode(int length,Boolean isCanRepeat,int w,int h,Color backgroundColor,Color lineColor,ByteArrayOutputStream output) {
		char[] usableCodes = Arrays.copyOfRange(codes, 9, 58);
		String result = packResult(usableCodes, length, isCanRepeat);
		packPicture(result, w, h, backgroundColor, lineColor, output);
		return result;
	}
	
	/**
	 * 数字+小写字母+大写字母形式的验证码
	 * 方法名：getLowerUpperCode
	 * @author zhanwang 创建时间：2015年11月21日-下午11:07:27 
	 * @param length			验证码位数
	 * @param isCanRepeat		是否可以有重复的数字或字母
	 * @param w                 验证码宽度
	 * @param h					验证码高度
	 * @param backgroundColor   验证码背景色
	 * @param lineColor			验证码干扰线颜色
	 * @param output			验证码输出流
	 * @return String			result  用户输入时与之相比较的验证结果
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getNumberLowerUpperCode(int length,Boolean isCanRepeat,int w,int h,Color backgroundColor,Color lineColor,ByteArrayOutputStream output) {
		char[] usableCodes = Arrays.copyOfRange(codes, 0, 58);
		String result = packResult(usableCodes, length, isCanRepeat);
		packPicture(result, w, h, backgroundColor, lineColor, output);
		return result;
	}
	
	/* ================================================== 封装的私有方法 ===================================================== */
	/***
	 * 封装数字/大小写字母结果
	 * 方法名：packResult
	 * @author zhanwang 创建时间：2015年11月21日-下午10:50:50 
	 * @param usableCodes		可用的数字或字母
	 * @param length			验证码位数
	 * @param isCanRepeat		是否可以有重复的数字或字母	
	 * @return String			result  用户输入时与之相比较的验证结果
	 * @exception 
	 * @since  1.0.0
	 */
	private static String packResult(char[] usableCodes,int length,Boolean isCanRepeat) {
		int requireLen = length;
		int len = usableCodes.length;
		if (requireLen > len) {
			throw new RuntimeException(  
                    String.format("调用getResult(%1$s,%2$s,%3$s)异常，传入参数%2$s不能大于%4$s",  
                    		usableCodes,requireLen,isCanRepeat,len)); 
		}
		char[] result=new char[requireLen]; 
		if(isCanRepeat){  
            for(int i=0;i<result.length;i++){  
                //索引 0 and len-1  
                int r=(int)(Math.random()*len);  
              
                //将result中的第i个元素设置为codes[r]存放的数值  
                result[i]=usableCodes[r];  
           }  
        }else{  
            for(int i=0;i<result.length;i++){  
                //索引 0 and n-1  
                int r=(int)(Math.random()*len);  
                 
                //将result中的第i个元素设置为codes[r]存放的数值  
                result[i]=usableCodes[r];  
                  
               //必须确保不会再次抽取到那个字符，因为所有抽取的字符必须不相同。  
                //因此，这里用数组中的最后一个字符改写codes[r]，并将n减1  
                usableCodes[r]=usableCodes[len-1];  
               len--;  
            }  
        }  
		return String.valueOf(result);
	}
	
	/**
	 * 封装四则运的结果
	 * (生成需要的随机数、随机运算符、计算结果)
	 * 方法名：packNumberOperationResult
	 * @author zhanwang 创建时间：2014-11-7-下午11:27:31 
	 * @return HashMap<String,Object>    除数、被除数、运算符、结果
	 * @exception 
	 * @since  1.0.0
	 */
	private static HashMap<String, Object> packNumberOperationResult() {
		// 获取随机数0~9。a：被除数，b：除数
		Random random = new Random();
		Integer a = random.nextInt(10);
		Integer b = random.nextInt(10);
		
		// 定义一个数组，保存四则运算符。
		Character[] arr = {'+','-','*','/'};
		// 获取随机数0~3。
		Integer index = random.nextInt(4);
		// 随机获取运算符。
		Character operator = arr[index];
		
		// 定义一个变量，接收运算结果。
		Integer result = 0;
		
		// 如果运算符为:'-'，要保证被减数大于减数，否则，重新生成随机数。
		if (index == 1) {
			while (a < b) {
				a = random.nextInt(10);
				b = random.nextInt(10);
			}
		}
		// 如果运算符为:'/'，要保证除数不为0，保证结果为整数，否则，重新生成除数。
		if (index == 3) {
			while (b == 0 || a % b != 0) {
				b = random.nextInt(10);
			}
		}
		
		// 根据不同的运算符，进行不同的算术运算，获取运算结果
		switch (index) {
		case 0:
			result = a+b;
			break;
		case 1:
			result = a-b;
			break;
		case 2:
			result = a*b;
			break;
		case 3:
			result = a/b;
			break;
		default:
			break;
		}
		
		// 将得到的值保存到HaspMap中，方便后面调用。
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		map.put("b", b);
		map.put("operator", operator);
		map.put("result", result);
		return map;
	}
	
	/**
	 * 封装产生验证码图片
	 * 方法名：packPicture
	 * @author zhanwang 创建时间：2014-11-7-下午10:32:32 
	 * @param content      生成的验证码图片的内容
	 * @param w      生成的验证码图片的宽度
	 * @param h      生成的验证码图片的高度
	 * @param h      生成的验证码图片的高度
	 * @param backgroundColor      生成的验证码图片的背景色
	 * @param lineColor      生成的验证码图片的干扰线颜色
	 * @param output      输出流
	 * @return 
	 * @return String result
	 * @exception 
	 * @since  1.0.0
	 */
	private static void packPicture(String content,int w,int h,Color backgroundColor,Color lineColor,ByteArrayOutputStream output) {
		try {
			// 创建绘制环境
			BufferedImage bufImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImage.createGraphics();
			// 绘制背景色
			g.setColor(new Color(204,204,204));
			g.fillRect(0, 0, w, h);
			// 绘制干扰线，增加识别难度
			Random random = new Random();
			g.setColor(lineColor);
			for (int i = 0; i < 50; i++) {
				int x1 = random.nextInt(w);
				int x2 = random.nextInt(w);
				int y1 = random.nextInt(h);
				int y2 = random.nextInt(h);
				g.drawLine(x1, y1, x2, y2);
			}
			new VarificationCodeUtil();
			// 绘制图片内容与样式
			g.setColor(backgroundColor);
			g.setFont(new Font("console", Font.BOLD, 16));
			g.drawString(content, 4, h-4);
			// 处理绘制环境中绘制的内容
			g.dispose();
			ImageIO.write(bufImage, "jpg", output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
