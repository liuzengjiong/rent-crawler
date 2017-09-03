package org.lzj.rent_crawler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lzj.rent_crawler.exception.MyRegexException;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
public class RegexUtil {
	
	public static List<String> getOneGroupByPattern(String parentContent,String patternStr){
		
		List<String> result = new ArrayList<>();
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(parentContent);
		while(matcher.find()){
			if(matcher.groupCount() != 1){
				throw new MyRegexException("此方法只允许且必须设置一个Group");
			}
			result.add(matcher.group(1));
		}
		return result;
	}
	
	public static String getOneParam(String oneHouseStr,String pattern){
		
		return getFirstStringFromList(getOneGroupByPattern(oneHouseStr, pattern));
	}
	
	private static String getFirstStringFromList(List<String> list){
		if(list == null || list.size() == 0){
			return "";
		}
		return list.get(0).trim();
	}
}
