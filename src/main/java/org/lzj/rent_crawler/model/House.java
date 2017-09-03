package org.lzj.rent_crawler.model;

import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
public class House {
	
	private String url;
	
	private String title;
	
	private String roomType;

	private String area;
	
	private String garden; //小区
	
	private String distance;
	
	private String price;
	
	private String subwayKey; //根据地铁进行搜索的key
	
	private String sendTime;
	
//	private String updateInfo; // 更新信息
	
//	private String detailAddress; 
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType.replace("&nbsp;","");
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getGarden() {
		return garden;
	}

	public void setGarden(String garden) {
		this.garden = garden;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSubwayKey() {
		return subwayKey;
	}

	public void setSubwayKey(String subwayKey) {
		this.subwayKey = subwayKey;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}


}
