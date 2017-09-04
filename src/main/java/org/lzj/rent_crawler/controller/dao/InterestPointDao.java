package org.lzj.rent_crawler.controller.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.lzj.rent_crawler.model.InterestPoint;
import org.lzj.rent_crawler.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月4日
 */
@Component
public class InterestPointDao {
	
	@Autowired
	private CommonUtil commonUtil;
	
	public List<InterestPoint> readInterestPoint(String fileName) throws IOException{
		
		return commonUtil.readFileToList(fileName, InterestPoint.class);
	}
	
	public void addPoint(String fileName,InterestPoint point) throws IOException{
		commonUtil.appendToFile(fileName, JSONObject.toJSONString(point));
	}
	
	public boolean deletePoint(String fileName,String id) throws IOException{
		
		List<InterestPoint> list = readInterestPoint(fileName);
		boolean have = false;
		StringBuilder sb = new StringBuilder();
		
		for(InterestPoint point : list){
			if(id.equals(point.getId())){
				have = true;
				continue;
			}
			sb.append(JSONObject.toJSONString(point)).append("\n");
		}
		commonUtil.reWrite(fileName, sb.toString());
		
		return have;
	}
}
