package org.lzj.rent_crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lzj.rent_crawler.content.HouseAnalysis;
import org.lzj.rent_crawler.content.SubwayInfoHolder;
import org.lzj.rent_crawler.model.House;
import org.lzj.rent_crawler.model.InterestPoint;
import org.lzj.rent_crawler.util.MailUtil;
import org.lzj.rent_crawler.util.RegexUtil;
import org.lzj.rent_crawler.util.UrlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import freemarker.template.TemplateException;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月2日
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！ 
@SpringApplicationConfiguration(classes = App.class) // 指定我们SpringBoot工程的Application启动类
public class MyTest {
	
	
	@Autowired
	private SubwayInfoHolder subwayInfoHolder;
	
	@Autowired
	private MailUtil mailUtil;
	
	private static Logger logger = LoggerFactory.getLogger(MyTest.class);
	
	@Autowired
	private HouseAnalysis houseAnalysis;
	
	@Test
	public void test(){
		
		String url = "http://sz.58.com/chuzu/sub/l236741/s237072/pn2/?pagetype=ditie&PGTID=0d3090a7-0000-4d3e-2d02-a2940ba5e903&ClickID=1";
		
		List<House> houseList = houseAnalysis.getHouseListByUrl(url);
		for(House house : houseList){
			System.out.println(JSONObject.toJSONString(house));
		}
	}
	
	@Test
	public void testInit(){
		while(true){
			
		}
	}
	
	@Test
	public void testFile() throws IOException{
		List<String> urls = new ArrayList<String>();
		File file = new File("liuzengjiong@qq.com_haveSend.txt");
		FileInputStream in = new FileInputStream("liuzengjiong@qq.com_haveSend.txt");
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
		System.out.println(urls.size());
		Set<String> set = new HashSet<String>();	
		for(String url : urls){
			System.out.println(url);
			set.add(url);
		}
		System.out.println(set.size());
	}
	
	@Test
	public void testMail(){
		InterestPoint testPoint = new InterestPoint();
		testPoint.setStationName("园岭站");
		testPoint.setPriceTo(2000);
		testPoint.setMail("liuzengjiong@qq.com");
		
		List<House> list = new ArrayList<>();
		House house = new House();
		house.setSubwayKey("园岭站");
		house.setSendTime("aa");
		house.setTitle("测试");
		list.add(house);
		list.add(house);
		for(int i=0;i<60;i++){
			list.add(house);
		}
		
		try {
			mailUtil.sendHouseList(testPoint, list);
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testNextPage(){
		String url = "http://sz.58.com/chuzu/sub/l4000044/pn2?pagetype=ditie&PGTID=0d3090a7-0000-4158-c6c3-450c0acc8828&ClickID=2";
		String nextPageUrl = UrlBuilder.getNextHousePage(url);
		System.out.println(nextPageUrl);
	}
	
	@Test
	public void testSearchStation(){
		
		String stationName = "7号线-全部";
		List<String> urlCache = new ArrayList<String>();
		
		int urlCacheSum = 0;
		int sum = 0;
		int zero_list_count = 0;
		String url = subwayInfoHolder.getStationUrl(stationName);
		
		while(true){
			String pageNo = "1";
			String regex = "pn(\\d*)";
			String pageStr = RegexUtil.getOneParam(url, regex);
			pageNo = StringUtils.isEmpty(pageStr)?pageNo:pageStr;
			
			List<House> houseList = houseAnalysis.getHouseListByUrl(url,stationName);
			logger.info("获取到第{}页的房子信息{}条",pageNo,houseList.size());
			if(houseList.size() == 0){
				zero_list_count++;
				if(zero_list_count >= 3){
					logger.error("{}次获取不到数据，退出此次任务",zero_list_count);
					break;
				}
				continue;
			}
			for(int i=0;i<houseList.size();i++){
				House house = houseList.get(i);
				if(urlCache.contains(house.getUrl())){
					logger.info("{} 房子已经查看过",sum+i);
					urlCacheSum ++ ;
					logger.info("目前有重复查到的房子:{}",urlCacheSum);
				}
				urlCache.add(house.getUrl());
				logger.info((sum + i) + JSONObject.toJSONString(house)) ;
			}
			sum += houseList.size();
			url = UrlBuilder.getNextHousePage(url);
		}
		
	}
}
