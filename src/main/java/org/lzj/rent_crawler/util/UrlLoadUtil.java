package org.lzj.rent_crawler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.lzj.rent_crawler.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
public class UrlLoadUtil {
	
	static Logger logger = LoggerFactory.getLogger(UrlLoadUtil.class);
	
	private static String lastDomain = "";
	
	public static String get(String url){
		
		if(UrlBuilder.getDomain(url).equals(lastDomain)){
			try {
				int seconds = Constant.threadLoadNum == 0?1:Constant.threadLoadNum;
//				logger.info("域名{}相同，休眠{}秒",lastDomain,seconds);
				Thread.sleep(seconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		lastDomain = UrlBuilder.getDomain(url);
		
		StringBuilder result = new StringBuilder();
		
		HttpURLConnection connection = null;
        InputStream is = null;
        
        try {
			URL urlObj =  new URL(url);
			connection = (HttpURLConnection) urlObj.openConnection();
			is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String inputLine  = "";
            while ((inputLine = reader.readLine())!=null) {
                result.append(inputLine).append("\n");
            }
			
		} catch (MalformedURLException e) {
			logger.info("连接错误({}),{}","MalformedURLException",e.getMessage());
		} catch (IOException e) {
			int sleepSeconds = 10;
			logger.error("连接错误({})：{},睡眠{}秒钟","IOException",e.getMessage(),sleepSeconds);
			try {
				Thread.sleep(1000 * sleepSeconds);
			} catch (InterruptedException e1) {
				
			}
		}finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("",e);
                    return "error";
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        return result.toString();
	}
	
}
