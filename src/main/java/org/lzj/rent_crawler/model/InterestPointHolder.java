package org.lzj.rent_crawler.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
@Component
public class InterestPointHolder {
	
	
//	public List<InterestPoint> getAllPoints(){
//		
//	}
	
//	private Map<String,List<InterestPoint>> pointMap = new HashMap<>();
	
//	public boolean addPoint(InterestPoint point){
//		if(StringUtils.isEmpty(point.getMail())){
//			return false;
//		}
//		List<InterestPoint> points = pointMap.get(point.getMail());
//		if(points == null){
//			points = new ArrayList<InterestPoint>();
//			pointMap.put(point.getMail(), points);
//		}
//		if(existPoint(points, point)){
//			return false;
//		}
//		points.add(point);
//		return true;
//	}
//	
//	private boolean existPoint(List<InterestPoint> points,InterestPoint point){
//		for(InterestPoint existPoint : points){
//			if(existPoint.getStationName()!=null && existPoint.getStationName().equals(point.getStationName())){
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public boolean removePoint(String mail){
//		pointMap.remove(mail);
//		return true;
//	}
//	
//	public Collection<InterestPoint> getAllPoint(){
//		List<InterestPoint> allPoints = new ArrayList<InterestPoint>();
//		for(String mail : pointMap.keySet()){
//			allPoints.addAll(pointMap.get(mail));
//		}
//		return allPoints;
//	}
//	
//	public Map<String,List<InterestPoint>> getPointMap() {
//		return pointMap;
//	}
//
//	public void setPointMap(Map<String,List<InterestPoint>> pointMap) {
//		this.pointMap = pointMap;
//	}
}
