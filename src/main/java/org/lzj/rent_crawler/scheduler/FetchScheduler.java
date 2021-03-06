package org.lzj.rent_crawler.scheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lzj.rent_crawler.Constant;
import org.lzj.rent_crawler.content.HouseAnalysis;
import org.lzj.rent_crawler.content.SubwayAnalysis;
import org.lzj.rent_crawler.content.SubwayInfoHolder;
import org.lzj.rent_crawler.model.House;
import org.lzj.rent_crawler.model.InterestPoint;
import org.lzj.rent_crawler.model.MailContent;
import org.lzj.rent_crawler.service.InterestPointService;
import org.lzj.rent_crawler.util.MailUtil;
import org.lzj.rent_crawler.util.UrlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import freemarker.template.TemplateException;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
@Component
public class FetchScheduler {
	
	private static Logger logger = LoggerFactory.getLogger(FetchScheduler.class);
	
	@Autowired
	private SubwayInfoHolder subwayInfoHolder;
	
//	@Autowired
//	private InterestPointHolder pointHolder;
	
	@Autowired
	private InterestPointService pointService;
	
	@Autowired
	private HouseAnalysis houseAnalysis;
	
	@Autowired
	private SubwayAnalysis subwayAnalysis;
	
	@Autowired
	private MailUtil mailUtil;
	
	private boolean haveSendWarning;
	
	private String warningReceiver = "liuzengjiong@126.com";
	
//	private static final int max_num_once_send = 20;
	
//	private final long gapTime = 1000 * 60 * 60 *6;
	
	//6：00  ； 12：00 ；18：00
//	@Scheduled(cron = "0 0 0/6 * * ?")
	@Scheduled(fixedRate=1000 * 120 * 10)
	public void schedule(){
		
		int count = 0;
		while(subwayInfoHolder.getAllStationName().size() == 0 && count++ < 5){
			long seconds = 20;
			logger.info("地铁数量为0，尝试休眠{}秒再获取",seconds);
			try {
				Thread.sleep(1000 * seconds);
			} catch (InterruptedException e) {
			}
			subwayAnalysis.intiSubwayToHolder();
		}
		
		Collection<InterestPoint> points = pointService.getInterestPoints();
		Map<String,List<InterestPoint>> subwayStationPoints = new HashMap<>();
		
		Iterator<InterestPoint> ite = points.iterator();
		while(ite.hasNext()){
			InterestPoint point = ite.next();
			String stationName = point.getStationName();

			List<InterestPoint> list = subwayStationPoints.get(stationName);
			if(list == null){
				list = new ArrayList<InterestPoint>();
				subwayStationPoints.put(stationName, list);
			}
			list.add(point);
		}
		
		ExecutorService exes = Executors.newCachedThreadPool();
		logger.info("查询的地铁站点size{}",subwayStationPoints.size());
		Constant.threadLoadNum = subwayStationPoints.size();
		haveSendWarning = false;
		for(String stationName : subwayStationPoints.keySet()){
			List<InterestPoint> pointsOfStation = subwayStationPoints.get(stationName);
			exes.execute(new FetchThread(stationName, pointsOfStation));
		}
		exes.shutdown();
	}
	
	class FetchThread implements Runnable{
		
		private String stationName;
		
		private List<InterestPoint> points;
		
		private List<House> houseWaitSend = new ArrayList<>();
		
		public FetchThread(String stationName,List<InterestPoint> points){
			this.stationName = stationName;
			this.points = points;
		}
		
		@Override
		public void run() {
			
			long currentMill = System.currentTimeMillis();
			
			int sumSend = 0;
			
			logger.info("启动线程，地铁：{}，points：{}，size：{}",stationName,JSONObject.toJSONString(points),points.size());
			
			String stationUrl = subwayInfoHolder.getStationUrl(stationName);
			if(StringUtils.isEmpty(stationUrl)){
				stationUrl = subwayInfoHolder.getNoLimitUrl();
			}
			
			houseWaitSend = calculateToWaitSend(stationUrl);
			
			for(InterestPoint point : points){
				List<House> toSend = selectToSend(houseWaitSend, point);
				sumSend += toSend.size();
				if(toSend.size() > 0){
					MailHolder.produceMail(new MailContent(point, toSend));
				}else{
					logger.info("查询到{}的房子数量为0",point.getStationName());
				}
			}
			
			//10秒中就爬好，判定为被要求验证码
			if(sumSend == 0 && System.currentTimeMillis() - currentMill  < 1000 * 10 && !haveSendWarning){
				if(mailUtil.sendNormalMail(warningReceiver, "租房邮件警告", "系统可能被要求验证码")){
					haveSendWarning = true;
				}
			}
		}
		
		public List<House> calculateToWaitSend(String url){
			
			List<House> houseWaitSend = new ArrayList<>();
			
			int zero_house_count = 0;
			while(true){
				List<House> houses = houseAnalysis.getHouseListByUrl(url, stationName);
				if(houses.size() == 0){
					zero_house_count++;
					if(zero_house_count >= 3){
						break;
					}
					continue;
				}
				for(House house : houses){
					if(houseWaitSend.contains(house)){
						continue;
					}
					houseWaitSend.add(house);
				}
				url = UrlBuilder.getNextHousePage(url);
			}
			return houseWaitSend;
		}
		
		public List<House> selectToSend(List<House> houseWaitSend,InterestPoint point){
			
			List<House> toSend = new ArrayList<>();
			Set<String> haveSendUrl = new HashSet<>();
			try {
				haveSendUrl = readHaveSendUrl(point.getMail()+Constant.have_send_suffix);
			} catch (IOException e) {
				logger.error("读取配置文件失败，{}",e.getMessage());
			}
			for(House house : houseWaitSend){
				if(!StringUtils.isEmpty(point.getStationName()) && !point.getStationName().equals(house.getSubwayKey())){
					continue;
				}
				if(!StringUtils.isEmpty(point.getRoomType()) && !house.getRoomType().contains(point.getRoomType())){
					continue;
				}
				int housePrice = house.getPrice() == null? 0 : Integer.valueOf(house.getPrice());
				if((point.getPriceTo()!=0 || point.getPriceFrom()!= 0) &&
						(point.getPriceTo()<housePrice || point.getPriceFrom()> housePrice )){
					continue;
				}
				if(point.isNearlyUpdate() && (house.getSendTime() == null || !house.getSendTime().contains("前"))){
					
					continue;
				}
				if(!haveSendUrl.add(house.getUrl())){
					continue;
				}
				toSend.add(house);
			}
			
			return toSend;
			
		}
		
		private Set<String> readHaveSendUrl(String fileName) throws IOException{
			
			Set<String> urls = new HashSet<String>();
			File file = new File(fileName);
			if(!file.exists()){
				return urls;
			}
			FileInputStream in = new FileInputStream(fileName);
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(reader);
			String s;
			while((s=buf.readLine()) != null){
				if(StringUtils.isEmpty(s)){
					continue;
				}
				urls.add(JSONObject.parseObject(s, House.class).getUrl());
			}
			buf.close();
			return urls;
		}
		
		
	}
	
}
