package org.lzj.rent_crawler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.lzj.rent_crawler.content.SubwayInfoHolder;
import org.lzj.rent_crawler.model.InterestPoint;
import org.lzj.rent_crawler.service.InterestPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @TODO 订阅
 * @author Jiong
 * @Date 2017年9月4日
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private InterestPointService service;
	
	@Autowired
	private SubwayInfoHolder subwayInfo;
	
	@RequestMapping("/mailPage")
	public String mailPage(HttpServletRequest request,HashMap<String,Object> map){
		String contextpath = 
				request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
		String url = contextpath + "/order/selectPage";
		map.put("url", url);
		return "/mail";
	}
	
	@RequestMapping("/selectPage")
	public String selectPage(HttpServletRequest request,HashMap<String,Object> map,String mail){
		
		if(StringUtils.isEmpty(mail)){
			map.put("errorMsg", "请填写邮箱");
			return "/error";
		}
		
		String contextpath = 
				request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();

		
		List<InterestPoint> points = service.getInterestPointsByMail(mail);
		
		map.put("mail", mail);
		map.put("points", points);
		map.put("stationListStr",subwayInfo.getAllStationName().toString());
		map.put("addUrl",contextpath + "/order/add");
		map.put("deleteUrl", contextpath + "/order/delete");
		
		return "/selectPage";
	}
	
	@RequestMapping("/add")
	public String add(String mail,String roomType,Integer priceFrom,Integer priceTo,String stationName,Boolean nearlyUpdate,
			HttpServletRequest request,HashMap<String,Object> map){
		
		if(StringUtils.isEmpty(mail)){
			map.put("errorMsg", "邮箱为空，请按照流程订阅");
			return "/error";
		}

		if(StringUtils.isEmpty(stationName) || !subwayInfo.getAllStationName().contains(stationName)){
			map.put("errorMsg", "该地铁不存在");
			return "/error";
		}
		
		
		InterestPoint point = new InterestPoint();
		point.setId(UUID.randomUUID().toString());
		point.setMail(mail);
		point.setRoomType(roomType);
		point.setStationName(stationName);
		if(priceFrom != null){
			point.setPriceFrom(priceFrom);
		}
		if(priceTo != null){
			point.setPriceTo(priceTo);
		}
		if(nearlyUpdate != null){
			point.setNearlyUpdate(nearlyUpdate);
		}
		List<InterestPoint> list = service.getInterestPoints();
		for(InterestPoint existPoint : list){
			if(existPoint.equalsByCondition(point)){
				map.put("errorMsg", "你已经订阅过一模一样的租房信息");
				return "/error";
			}
		}
		
		if(service.addPoint(point)){
			String url = "redirect:/order/selectPage?mail="+mail;
			return url;
		}else{
			map.put("errorMsg", "添加订阅信息失败！");
			return "/error";
		}
		
	}
	
	
	@RequestMapping("/delete/{mail}/{id}")
	public String delete(@PathVariable("id") String id,@PathVariable("mail")String mail,
			HttpServletRequest request,HashMap<String,Object> map){
		
		if(StringUtils.isEmpty(id)){
			map.put("errorMsg", "删除的订阅点为空，请按照流程操作");
			return "/error";
		}
		
		if(service.deletePoint(id)){
			String url = "redirect:/order/selectPage?mail="+mail;
			return url;
		}else{
			map.put("errorMsg", "该订阅信息不存在");
			return "/error";
		}
		
	}
	
	
}
