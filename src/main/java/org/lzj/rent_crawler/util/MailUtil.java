package org.lzj.rent_crawler.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.lzj.rent_crawler.model.House;
import org.lzj.rent_crawler.model.InterestPoint;
import org.lzj.rent_crawler.model.MailContent;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import freemarker.template.Template;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月3日
 */
@Configuration
@Component
public class MailUtil {
	 @Autowired  
	 JavaMailSender mailSender;

	 @Autowired
	 private freemarker.template.Configuration configuration;
	 
	 @Value("${spring.mail.nickname:刘增炯}")
	 private String nickname;
	 
	 @Value("${spring.mail.username:liuzengjiong@126.com}")
	 private String username;
	 
	 private static org.slf4j.Logger logger = LoggerFactory.getLogger(MailUtil.class);
	 
	 
	 public boolean sendHouseList(MailContent mailContent){
		 
		 InterestPoint point = mailContent.getPoint();
		 
		 List<House> houseList = mailContent.getHouseList();
		 
		 if(point == null || houseList == null || houseList.size() == 0){
			 return false;
		 }
		 
		 String subject = "租房邮件提醒";
		 
		 Map<String, Object> model = new HashMap<String, Object>();  
         model.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
         model.put("fromNickName", nickname);  
         model.put("mail", point.getMail());
         StringBuilder searchCondition = new StringBuilder();
         if(!StringUtils.isEmpty(point.getStationName())){
        	 model.put("stationName", point.getStationName());
        	 searchCondition.append("地铁：").append(point.getStationName()).append("  ");
         }
         if(!StringUtils.isEmpty(point.getRoomType())){
        	 searchCondition.append("类型：").append(point.getRoomType()).append("   ");
         }
         if(point.getPriceFrom()!=0 || point.getPriceTo()!=0){
        	 searchCondition.append("价格：").append(point.getPriceFrom()).append(" - ").append(point.getPriceTo()).append("    ");
         }
         String nearlyUpdateStr = point.isNearlyUpdate()?"是":"否";
         searchCondition.append("只看最近更新：").append(nearlyUpdateStr);
         model.put("searchCondition",searchCondition.toString());
         model.put("houseSize", houseList.size());
         model.put("houseList", houseList);
		 
		 try  
	        {  
			 
			 	Template t = configuration.getTemplate("houseList.ftl"); // freeMarker template  
			 	String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model); 
	     
	            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();  
	            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);  
	            message.setFrom(username);  
	            message.setTo(point.getMail());  
	            message.setSubject(subject);  
	            message.setText(content,true);  
	            mailSender.send(mimeMessage);  
	            
	            
	            return true;
	              
	        }  
	        catch(Exception ex){  
	        	logger.error("发送房子列表邮件失败{}"+ex.getMessage());
	            return false;  
	        }  
		 
	 }
	 
	 
	 public boolean sendNormalMail(String toMail,String title,String content){
		 
		   final MimeMessage mimeMessage = this.mailSender.createMimeMessage();  
           final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);  
          
           try {
    	    message.setFrom(username);  
            message.setTo(toMail);  
			message.setSubject(title);
			message.setText(content);
			mailSender.send(mimeMessage);  
			return true;
		} catch (MessagingException e) {
			logger.error("发送邮件失败{}",e.getMessage());
			return false;
		}  
	 }
	 
}
