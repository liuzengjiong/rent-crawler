package org.lzj.rent_crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lzj.rent_crawler.Constant;
import org.lzj.rent_crawler.controller.dao.InterestPointDao;
import org.lzj.rent_crawler.model.InterestPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月4日
 */
@Component
public class InterestPointService {
	
	private static Logger logger = LoggerFactory.getLogger(InterestPointService.class);
	
	@Autowired
	private InterestPointDao pointDao;
	
	public List<InterestPoint> getInterestPoints(){
		
		try {
			return pointDao.readInterestPoint(Constant.interest_point_file);
		} catch (IOException e) {
			logger.error("获取兴趣点发生错误{}",e.getMessage());
			return new ArrayList<>();
		}
		
	}
	
	public List<InterestPoint> getInterestPointsByMail(String mail){
		
		try {
			List<InterestPoint> list = pointDao.readInterestPoint(Constant.interest_point_file);
			List<InterestPoint> result = new ArrayList<>();
			
			for(InterestPoint point : list){
				if(mail.equals(point.getMail())){
					result.add(point);
				}
			}
			return result;
		} catch (IOException e) {
			logger.error("获取兴趣点发生错误{}",e.getMessage());
			return new ArrayList<>();
		}
		
	}
	
	public boolean addPoint(InterestPoint point){
		try {
			pointDao.addPoint(Constant.interest_point_file, point);
			return true;
		} catch (IOException e) {
			logger.error("添加兴趣点发生错误{}",e.getMessage());
			return false;
		}
	}
	
	public boolean deletePoint(String id){
		
		try {
			return pointDao.deletePoint(Constant.interest_point_file, id);
		} catch (IOException e) {
			logger.error("删除兴趣点发生错误{}",e.getMessage());
			return false;
		}
	}
}
	