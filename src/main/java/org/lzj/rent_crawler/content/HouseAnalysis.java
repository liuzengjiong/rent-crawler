package org.lzj.rent_crawler.content;

import java.util.ArrayList;
import java.util.List;

import org.lzj.rent_crawler.model.House;
import org.lzj.rent_crawler.util.RegexUtil;
import org.lzj.rent_crawler.util.UrlLoadUtil;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

/**
 * @TODO 房子内容分析
 * @author Jiong
 * @Date 2017年9月2日
 */
@Component
public class HouseAnalysis {
	
	private static final String reg_house_list_58 = "(<li[\\s\\S]*?<div class=\"des\"[\\s\\S]*?/li>)";
	
	private static final String reg_house_url_58 = "<h2>[\\s\\S]*?<a href=\"([\\s\\S]*?)\"";
	
	private static final String reg_house_title_58 = "<h2>[\\s\\S]*?>([\\s\\S]*?)</a>";
	
	private static final String reg_house_roomType_58 = "<p class=\"room\">([\\s\\S]*?)</p>";
	
	private static final String reg_house_area_58 = "<p class=\"add\">[\\s\\S]*?>([\\s\\S]*?)</a>";
	
	private static final String reg_house_garden_58 = "<p class=\"add\">[\\s\\S]*?<a[\\s\\S]*?<a[\\s\\S]*?>([\\s\\S]*?)</a>";
	
	private static final String reg_house_distance_58 = "<p class=\"add\">[\\s\\S]*?</em>([\\s\\S]*?)</p>";
	
	private static final String reg_house_price = "<div class=\"money\">\\s*?<b>([\\s\\S]*?)</b>";
	
	private static final String reg_house_sendTime = "<div class=\"sendTime\">([\\s\\S]*?)</div>";
	
//	public  static final String reg_house_detail_updateInfo_58 = "<p class=\"house-update-info[\\S\\s]*?>\n([\\s\\S]*?)&";
	
//	public  static final String reg_house_detail_address = "<span\\s*class=\"dz\"\\s*>([\\s\\S]*?)</span>";
	
//	public  static final String reg_house_detail_title = "<div class=\"house-title\">\\s*<h1 [\\s\\S]*?>([\\s\\S]*?)</h1>";
	
	
	public List<House> getHouseListByUrl(String url){
		
		return getHouseListByUrl(url,null);
	}
	
	public List<House> getHouseListByUrl(String url,String stationName){
		
		String htmlContent = UrlLoadUtil.get(url);
		
		List<House> houseList = getHouseList(htmlContent,stationName);
		
		return houseList;
	}
	
	
	private List<String> getHouseStrList(String content){
		
		return RegexUtil.getOneGroupByPattern(content,reg_house_list_58);
	}
	
	
	private List<House> getHouseList(String content,String stationName){
		
		List<String> houseStrList = getHouseStrList(content);
		
		List<House> houseList = new ArrayList<>();
		
		for(int i=0;i<houseStrList.size();i++){
			
			String oneHouseStr = houseStrList.get(i);
			House house = buildHouse(oneHouseStr);
			house.setSubwayKey(stationName);
			houseList.add(house);
		}
		
		
		return houseList;
	}
	
	private House buildHouse(String oneHouseStr){
		
		House oneHouse = new House();
		String house_url =  RegexUtil.getOneParam(oneHouseStr,reg_house_url_58);
		String house_title = RegexUtil.getOneParam(oneHouseStr, reg_house_title_58);
		String house_area = RegexUtil.getOneParam(oneHouseStr, reg_house_area_58);
		String house_roomType = RegexUtil.getOneParam(oneHouseStr, reg_house_roomType_58);
		String house_garden = RegexUtil.getOneParam(oneHouseStr, reg_house_garden_58);
		String house_distance = RegexUtil.getOneParam(oneHouseStr, reg_house_distance_58);
		String house_price = RegexUtil.getOneParam(oneHouseStr, reg_house_price);
		String house_sendTime = RegexUtil.getOneParam(oneHouseStr, reg_house_sendTime);
		
		oneHouse.setUrl(house_url);
		oneHouse.setTitle(house_title);
		oneHouse.setArea(house_area);
		oneHouse.setRoomType(house_roomType);
		oneHouse.setGarden(house_garden);
		oneHouse.setDistance(house_distance);
		oneHouse.setPrice(house_price);
		oneHouse.setSendTime(house_sendTime);
		
		return oneHouse;
	}
	
}
