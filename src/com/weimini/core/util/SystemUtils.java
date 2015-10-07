package com.weimini.core.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 公共工具类
 * @author rails
 *
 */
public class SystemUtils {
	
	
	/**
	 * 将String类型转换成Byte类型
	 * @param str 参数
	 * @return byte
	 */
	public static byte strToByte(String str) {
		if(str != null && !"".equals(str)) {
			return Byte.parseByte(str);
		}
		return 0;
	}
	
	
	/**
	 * 将String类型转换成Short类型
	 * @param str 参数
	 * @return short
	 */
	public static short strToShort(String str) {
		if(str != null && !"".equals(str)) {
			return Short.parseShort(str);
		}
		return 0;
	}
	
	/**
	 * 将String类型转换成Integer类型
	 * @param str 参数
	 * @return int
	 */
	public static int strToInt(String str) {
		if(str != null && !"".equals(str)) {
			if(str.indexOf(",") == -1) {
				return Integer.parseInt(str);
			}
		}
		return 0;
	}
	
	/**
	 * 将String类型转换成Long类型
	 * @param str 参数
	 * @return long
	 */
	public static long strToLong(String str) {
		if(str != null && !"".equals(str)) {
			return Long.parseLong(str);
		}
		return 0;
	}
	
	/**
	 * 将String类型转换成Float类型
	 * @param str 参数
	 * @return float
	 */
	public static float strToFloat(String str) {
		if(str != null && !"".equals(str)) {
			return Float.parseFloat(str);
		}
		return 0;
	}
	
	
	/**
	 * 将String类型转换成Double类型
	 * @param str 参数
	 * @return double
	 */
	public static double strToDouble(String str) {
		if(str != null && !"".equals(str)) {
			return Double.parseDouble(str);
		}
		return 0;
	}
	
	
	/**
	 * 将String转化成java.util.Date类的对象
	 * @param str  参数
	 * @param format  日期格式  (例:yyyy-MM-dd HH:mm:ss)
	 * @return java.util.Date类的对象
	 * @throws ParseException
	 */
	public static Date strToDate(String str, String format) {
		if(str != null && !"".equals(str) && StringUtils.isNotEmpty(format)) {
			try {
				return new Date(new SimpleDateFormat(format).parse(str).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 将String转化成java.sql.Timestamp类的对象
	 * @param str  参数
	 * @param format  日期格式  (例:yyyy-MM-dd HH:mm:ss)
	 * @return java.sql.Timestamp类的对象
	 * @throws ParseException
	 */
	public static Timestamp strToTimestamp(String str, String format)  {
		if(str != null && !"".equals(str) && StringUtils.isNotEmpty(format)) {
			try {
				return new Timestamp(new SimpleDateFormat(format).parse(str).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 将String转化成java.sql.Time类的对象
	 * @param str 参数
	 * @return java.sql.Time类的对象
	 * @throws ParseException
	 */
	public static Time strToTime(String str)  {
		if(str != null && !"".equals(str)) {
			try {
				return new Time(new SimpleDateFormat("HH-mm-ss").parse(str).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 将java.util.Date类的对象转化成字符串
	 * @param date java.util.Date类的对象
	 * @param format 日期格式  (例:yyyy-MM-dd HH:mm:ss)
	 * @return 字符串
	 * @throws ParseException
	 */
	public static String dateToStr(Date date, String format)  {
		if(date != null) {
			return new SimpleDateFormat(format).format(date);
		}
		return "";
	}
	
	/**
	 * 将java.sql.Timestamp类的对象转化成字符串
	 * @param timestamp java.sql.Timestamp类的对象
	 * @param format 日期格式  (例:yyyy-MM-dd HH:mm:ss)
	 * @return 字符串
	 * @throws ParseException
	 */
	public static String timestampToStr(Timestamp timestamp, String format)  {
		if(timestamp != null && StringUtils.isNotEmpty(format)) {
			return new SimpleDateFormat(format).format(timestamp);
		}
		return "";
	}
	
	/**
	 * 将java.sql.Time类的对象转化成字符串
	 * @param time java.sql.Time类的对象
	 * @return 字符串
	 * @throws ParseException
	 */
	public static String timeToStr(Time time) throws ParseException {
		if(time != null) {
			return new SimpleDateFormat("HH:mm:ss").format(time);
		}
		return "";
	}
	
	/**
	 * 按字节截取字符串
	 * @param str  要截取的字符串
	 * @param maxByte 截取的长度
	 * @param endStr  拼接的字符串
	 * @return
	 */
	public static String subString(String str, int maxByte, String endStr) {
		
		if (str == null || "".equals(str)) {

			// 如果源字符串为空或null，返回空字符串
			str = "";
		} else {
			if(str.indexOf("&nbsp;") != -1) {
				str = str.replace("&nbsp;", " ");
			}
			if(str.indexOf("&ldquo;") != -1 || str.indexOf("&rdquo;") != -1) {
				str = str.replace("&ldquo;", "“").replace("&rdquo;", "”");
			}
			// 计算字节长度
			int byteLength = 0;
			// 计算字符长度
			int charLength = 0;
			for (; charLength < str.length(); charLength++) {
				// 计算每个字符的字节数，每个汉字+2byte，其它+1
				byteLength = (int) str.charAt(charLength) > 256 ? byteLength + 2 : byteLength + 1;
				// 超过最大限制字节时，按当前charLength截取字符串
				if (byteLength > maxByte) {
					// 当前长度减去结尾省略字符串的长度的一半（此处将省略字符串假设为半字节字符）
					charLength = charLength - endStr.length() / 2;
					// 截取字符串，加上省略字符串
					str = str.substring(0, charLength > 0 ? charLength : 0) + endStr;
					// 跳出循环
					break;
				}
			}
		}
		return str;
	}
	
	
	/**
	 * 恢复XSS过滤后的字符串到原始字符串
	 * @param value
	 * @return
	 */
	public static String resumeXSS(String value) {
		// You'll need to remove the spaces from the html entities below
		value = value.replaceAll("& lt;","<").replaceAll("& gt;",">");
		value = value.replaceAll("& #40;","\\(").replaceAll("& #41;","\\)");
		value = value.replaceAll("& #39;","'");
		return value;
	}


}
