package org.lzj.rent_crawler.model;

import java.util.List;

/**
 * @TODO 兴趣点
 * @author Jiong
 * @Date 2017年9月2日
 */
public class InterestPoint {
	
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
	};
	
}
