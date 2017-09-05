package org.lzj.rent_crawler.scheduler;

import java.io.IOException;

import org.lzj.rent_crawler.Constant;
import org.lzj.rent_crawler.controller.dao.HouseDao;
import org.lzj.rent_crawler.model.MailContent;
import org.lzj.rent_crawler.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月5日
 */
@Component
public class MailScheduler {
	
	private static Logger logger = LoggerFactory.getLogger(MailScheduler.class);
	
	//一分钟发一次邮件
	private final long  gap = 1000 * 60;
	
	
	
	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private HouseDao houseDao;
	
	@Scheduled(fixedRate=gap)
	public void schedule(){
		
		MailContent mailContent = MailHolder.consumeMail();
		
		if(mailContent == null){
			return;
		}
		
		if(mailUtil.sendHouseList(mailContent)){
			try {
				houseDao.appendToHaveSendFile(mailContent.getPoint().getMail()+Constant.have_send_suffix, mailContent.getHouseList());
			} catch (IOException e) {
				logger.error("发送的房子列表写入文件失败{}",e.getMessage());
			}
			logger.info("发送邮件给{}成功,房子数量：{},还有{}封邮件待发",mailContent.getPoint().getMail(),mailContent.getHouseList().size(),MailHolder.getWaitMailNum());
		}
		
	}
	
}
