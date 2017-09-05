package org.lzj.rent_crawler.model;

import java.util.List;

/**
 * @TODO 邮件内容
 * @author Jiong
 * @Date 2017年9月5日
 */
public class MailContent {
	
	private InterestPoint point;
	private List<House> houseList;
	
	public MailContent(InterestPoint point,List<House> houseList){
		this.setPoint(point);
		this.setHouseList(houseList);
	}

	public InterestPoint getPoint() {
		return point;
	}

	public void setPoint(InterestPoint point) {
		this.point = point;
	}

	public List<House> getHouseList() {
		return houseList;
	}

	public void setHouseList(List<House> houseList) {
		this.houseList = houseList;
	}
	
}
