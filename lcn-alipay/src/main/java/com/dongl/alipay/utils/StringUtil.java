package com.dongl.alipay.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 项目通用工具类
 * @author Administrator
 *
 */
public class StringUtil {
	
	
	/**
	 * 返回指定格式的日期字符串
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String getFormatterDate(Date date,String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}
	
	/**
	 * 判断请求是否是ajax
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(header))return true;
		return false;
	}
	
	/**
	 * 从流读取字符串
	 * @param inputStream
	 * @return
	 */
	public static String getStringFromInputStream(InputStream inputStream){
		String string = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
			String buf = null;
			try {
				while((buf = bufferedReader.readLine()) != null){
					string += buf;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return string;
	}
	
	/**
	 * 生成可带前后缀的唯一字符串
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String generateSn(String prefix,String suffix){
		return prefix + UUID.randomUUID().toString().replace("-", "").toUpperCase() + suffix;
	}
	
	/**
	 * 判断是否是手机浏览器
	 * @param request
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest request){
		// 如果有HTTP_X_WAP_PROFILE则一定是移动设备
	    if (request.getHeader("X-WAP-PROFILE") != null)return true;
	    // 此条摘自TPM智能切换模板引擎，适合TPM开发
	    if (request.getHeader("CLIENT") != null && "PhoneClient".equals(request.getHeader("HTTP_CLIENT"))){
	    	return true;
	    }
	    // 如果via信息含有wap则一定是移动设备,部分服务商会屏蔽该信息
	    if (request.getHeader("VIA") != null){
	    	// 找不到为flase,否则为true
	    	return request.getHeader("HTTP-VIA").indexOf("wap") >= 0 ? true : false;
	    }
	    if(request.getHeader("User-Agent") != null){
	    	String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
	                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
	                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
	                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
	                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
	                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
	                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
	                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
	                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
	                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
	                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
	                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
	                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
	                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
	                "Googlebot-Mobile" };
	    	String agent=request.getHeader("User-Agent");
            for (String mobileAgent : mobileAgents){
            	if (agent.toLowerCase().indexOf(mobileAgent) >= 0&&agent.toLowerCase().indexOf("windows nt")<=0 &&agent.toLowerCase().indexOf("macintosh")<=0){
            		return true;
            	}
            }
	    }
		return false;
	}
}
