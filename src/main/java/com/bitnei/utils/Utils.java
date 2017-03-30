package com.bitnei.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName: Utils
 * 
 * @Description: 工具类
 * @author luhaiyou
 * @date 2015年7月9日
 */
public class Utils {

	/**
	 * 
	 * @Description: 把不包含{}的json串解析成map
	 * @param message json串
	 * @return Map<String,String>  
	 * @throws
	 * @author luhaiyou
	 * @date 2015年7月9日
	 */
	public static Map<String,String>  processMessageToMap(String message){
		if(message.indexOf("{") != -1){
			message = message.substring(message.indexOf("{"));
		}
		// 把{}去掉
		if (message.startsWith("{")) {
			message = message.substring(1);
		}
		if(message.endsWith("}")){
			message = message.substring(0, message.length()-1);
		}
		Map<String,String> map = new HashMap<>();
		String[] keyValues = message.split(",");
		for(String kv : keyValues){
			String[] fields = kv.split(":");
			if(fields.length==1){
				// map.put(StringUtils.trim(fields[0]).replaceAll("\"", ""), "");
			}else{
				map.put(StringUtils.trim(fields[0]).replaceAll("\"", ""), StringUtils.trim(fields[1]).replaceAll("\"", ""));
			}
		}
		return map;
	}

	/**
	 * 字符串时间转换成UTC时间
	 *
	 * @MethodName convertToUTC
	 * @author zhaogd
	 * @date 2017年3月30日 下午3:23:04
	 * @param time
	 *            20150717173636
	 * @return long 毫秒值
	 * @throws ParseException
	 */
	public static long convertToUTC(String time) throws ParseException {
		if (time.length() == "150714140400".length()) {
			time = "20" + time;
		}
		SimpleDateFormat timeSdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return timeSdf.parse(time).getTime();
	}
}
