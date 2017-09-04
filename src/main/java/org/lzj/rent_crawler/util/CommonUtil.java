package org.lzj.rent_crawler.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.lzj.rent_crawler.model.House;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @TODO 
 * @author Jiong
 * @Date 2017年9月4日
 */
@Component
public class CommonUtil {
	
	public <T> List<T> readFileToList(String fileName,Class<T> clazz) throws IOException{
		
		List<T> list = new ArrayList<>();
		File file = new File(fileName);
		if(!file.exists()){
			return list;
		}
		FileInputStream in = new FileInputStream(fileName);
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader buf = new BufferedReader(reader);
		String s;
		while((s=buf.readLine()) != null){
			if(StringUtils.isEmpty(s)){
				continue;
			}
			list.add(JSONObject.parseObject(s, clazz));
		}
		buf.close();
		return list;
	}
	
	public void appendToFile(String fileName,String tailStr) throws IOException{
		
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
		
		sb.append(tailStr);
		
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		BufferedWriter bufWriter = new BufferedWriter(writer);
		bufWriter.write(sb.toString());
		bufWriter.flush();
		bufWriter.close();
		writer.close();
		out.close();
		
	}
	
	public void reWrite(String fileName,String newStr) throws IOException{
		
		File file = new File(fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		BufferedWriter bufWriter = new BufferedWriter(writer);
		bufWriter.write(newStr);
		bufWriter.flush();
		bufWriter.close();
		writer.close();
		out.close();
		
	}

}


