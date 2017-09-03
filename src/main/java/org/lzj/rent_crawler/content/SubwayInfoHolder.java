package org.lzj.rent_crawler.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lzj.rent_crawler.util.UrlBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
@Component
public class SubwayInfoHolder {
	
	private static Map<String,String> allSubway = new HashMap<>();
	
	private static Map<String,List<String>> lineSubway = new HashMap<>();
	
	public void addSubway(String lineName,String stationName,String stationUrl){
		
		if(!allSubway.containsKey(stationName)){
			allSubway.put(stationName, stationUrl);
		}
		List<String> list = lineSubway.get(lineName);
		if(list == null){
			list = new ArrayList<>();
			lineSubway.put(lineName, list);
		}
		list.add(stationName);
	}
	
	public List<String> getStationsOfLine(String lineName){
		if(StringUtils.isEmpty(lineName)){
			return null;
		}
		return lineSubway.get(lineName) == null ? new ArrayList<String>() : lineSubway.get(lineName);
	}
	
	public String getStationUrl(String stationName){
		if(StringUtils.isEmpty(stationName)){
			return null;
		}
		return allSubway.get(stationName);
	}
	
	public Set<String> getAllStationName(){
		
		return allSubway.keySet();
	}
	
	public List<String> getAllLineName(){
		
		Set<String> set = lineSubway.keySet();
		List<String> list = new ArrayList<>(set);
		Collections.sort(list);
		
		return list;
	}
	
	public String getNoLimitUrl(){
		
		return  UrlBuilder.build58Url("/chuzu/sub/");
	}
	
	
}
