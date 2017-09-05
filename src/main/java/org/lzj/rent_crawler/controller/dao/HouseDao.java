package org.lzj.rent_crawler.controller.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.lzj.rent_crawler.model.House;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月5日
 */
@Component
public class HouseDao {
	
	public void appendToHaveSendFile(String fileName,List<House> houses) throws IOException{
		
		File file = new File(fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileInputStream in = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader buf = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String s;
		while((s=buf.readLine()) != null){
			sb.append(s).append("\n");
		}
		buf.close();
		reader.close();
		in.close();
		
		for(House house : houses){
			String jsonStr = JSONObject.toJSONString(house);
			sb.append(jsonStr).append("\n");
		}
		
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		BufferedWriter bufWriter = new BufferedWriter(writer);
		bufWriter.write(sb.toString());
		bufWriter.flush();
		bufWriter.close();
		writer.close();
		out.close();
		
	}
}
