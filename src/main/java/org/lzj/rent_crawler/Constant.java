package org.lzj.rent_crawler;

import org.lzj.rent_crawler.util.UrlBuilder;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月3日
 */
public class Constant {
	
	// "http://sz.58.com/chuzu/sub/ "
	
	public static final String subway_url = UrlBuilder.build58Url("/chuzu/sub/");
	
	public static final String have_send_suffix = "_haveSend.txt"; //mail_haveSend
	
	public static final String interest_point_file = "interestPoints.txt";
	
	public static int threadLoadNum = 0;
	
}
