package org.lzj.rent_crawler.content;

import java.util.List;

import javax.annotation.PostConstruct;

import org.lzj.rent_crawler.Constant;
import org.lzj.rent_crawler.util.RegexUtil;
import org.lzj.rent_crawler.util.UrlBuilder;
import org.lzj.rent_crawler.util.UrlLoadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @TODO 这个类的init方法会在程序启动的时候直接执行
 * @author Jiong
 * @Date 2017年9月2日
 */
@Component
public class SubwayAnalysis{
	
	@Autowired
	private SubwayInfoHolder subwayInfoHolder;
	
	private  static Logger logger = LoggerFactory.getLogger(SubwayAnalysis.class);
	
	private  final String reg_subway_line_content_58 = "<dl class=\"secitem secitem_fist subway\">\\s*(<dd>[\\s\\S]*?)</dd>";
	
	private  final String reg_subway_line_list_58 = "(<a[\\s\\S]*?</a>)";
	
	private  final String reg_subway_line_name_58 = "<a[\\s\\S]*?>([\\s\\S]*?)</a>";
	
	private  final String reg_subway_line_url_58 = "<a href=\"(\\S*?)\"";
	
	
	private  final String reg_subway_station_content_58 = "<div id=\"sub_one\">([\\s\\S]*?)</div>";
	
	private  final String reg_subway_station_list_58 = reg_subway_line_list_58;
	
	private  final String reg_subway_station_name = reg_subway_line_name_58;
	
	private  final String reg_subway_station_url = reg_subway_line_url_58;
	

	private  List<String> getSubwayLineList(String content){
		
		String lineContent = RegexUtil.getOneParam(content,reg_subway_line_content_58);
		
		return RegexUtil.getOneGroupByPattern(lineContent, reg_subway_line_list_58);
	}
	
	private  List<String> getStationList(String content){
		
		String stationContent = RegexUtil.getOneParam(content, reg_subway_station_content_58);
		
		return RegexUtil.getOneGroupByPattern(stationContent, reg_subway_station_list_58);
	}
	
	@PostConstruct
	public  void intiSubwayToHolder(){
		
		logger.info("初始化地铁");
		
		String url = Constant.subway_url;
		String html = UrlLoadUtil.get(url);
		List<String> list = getSubwayLineList(html);
		for(String str : list){
			String lineName = RegexUtil.getOneParam(str, reg_subway_line_name_58);
			String lineUrl = UrlBuilder.build58Url(RegexUtil.getOneParam(str, reg_subway_line_url_58));
			
			logger.info("开始初始化"+lineName);
			String stationHtml = UrlLoadUtil.get(lineUrl);
			List<String> stationList = getStationList(stationHtml);
			for(String sstr : stationList){
				String stationName = RegexUtil.getOneParam(sstr, reg_subway_station_name);
				if(stationName.equals("整条线路")){
					stationName = lineName + "-全部"; 
				}
				String stationUrl = UrlBuilder.build58Url(RegexUtil.getOneParam(sstr, reg_subway_station_url));
				
				subwayInfoHolder.addSubway(lineName, stationName, stationUrl);
			}
			logger.info(subwayInfoHolder.getStationsOfLine(lineName).toString());
		}
	}

}
