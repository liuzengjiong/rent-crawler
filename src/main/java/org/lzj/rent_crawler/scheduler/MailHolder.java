package org.lzj.rent_crawler.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.lzj.rent_crawler.model.MailContent;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月5日
 */
public class MailHolder {
	
	private static List<MailContent> waitSend = new ArrayList<>();
	
	public static synchronized void produceMail(MailContent mailContent){
		
		waitSend.add(mailContent);
	}
	
	public static synchronized MailContent consumeMail(){
		
		if(waitSend.size() > 0){
			return waitSend.remove(0);
		}
		return null;
	}
	
	public static int getWaitMailNum(){
		
		return waitSend.size();
	}
}
