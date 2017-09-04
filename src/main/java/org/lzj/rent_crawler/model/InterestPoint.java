package org.lzj.rent_crawler.model;

import java.util.List;

import org.springframework.util.StringUtils;

/**
 * @TODO 兴趣点
 * @author Jiong
 * @Date 2017年9月2日
 */
public class InterestPoint {
	
	private String id;
	
	private String mail;
	
	private String stationName;
	
	private String roomType;
	
	private int priceFrom;
	
	private int priceTo;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(int priceFrom) {
		this.priceFrom = priceFrom;
	}

	public int getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(int priceTo) {
		this.priceTo = priceTo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	};
	
	public boolean equalsByCondition(InterestPoint point){
		
		return 	compareString(this.mail,point.getMail())
				&& compareString(this.stationName,point.getStationName())
				&& compareString(this.roomType,point.getRoomType())
				&& this.priceFrom == point.getPriceFrom()
				&& this.priceTo == point.getPriceTo();
		
	}
	
	private boolean compareString(String s1,String s2){
		
		if(s1 == null && s2 == null){
			return true;
		}
		if(s1 == null){
			return false;
		}
		if(s1.equals(s2)){
			return true;
		}
		return false;
	}
	
}
