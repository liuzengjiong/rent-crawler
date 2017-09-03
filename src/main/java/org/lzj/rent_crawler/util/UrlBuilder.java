package org.lzj.rent_crawler.util;

import org.springframework.util.StringUtils;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
public class UrlBuilder {
	
	private static final String protocol = "http://";
	
	private static final String city = "sz";
	
	private static final String domain_58 = "58.com";
	
	private static final String suffix = "PGTID=0d3090a7-0000-44f9-bc55-8bae7d722c86&ClickID=2";
	
	//   http://sz.58.com/chuzu/sub/?PGTID=0d3090a7-0000-44f9-bc55-8bae7d722c86&ClickID=2
	public static String build58Url(String subUrl){
		
		StringBuilder url = new StringBuilder();
		
		url.append(protocol).append(city).append(".").append(domain_58).append("/").append(subUrl);
		doAppendPrefix(url);
		
		return url.toString();
	}
	
	public static String getNextHousePage(String url){
		
		String regex = "pn(\\d*)";
		String pageStr = RegexUtil.getOneParam(url, regex);
		if(!StringUtils.isEmpty(pageStr)){
			int nextPage = Integer.valueOf(pageStr) + 1;
			return url.replace("pn"+pageStr, "pn"+nextPage);
		}
		
		int index = url.indexOf("?");
		if(index != -1){
			return url.substring(0,index) + "/pn2" + url.substring(index,url.length());
		}
		return url + "/pn2";
		
	}
	
	private static void doAppendPrefix(StringBuilder url){
		if(url.indexOf("?") != -1){
			url.append("&");
		}else{
			url.append("?");
		}
		url.append(suffix);
	}
}
