package com.purejadeite.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purejadeite.util.ZipCodeCsv;

public class ZipCodeDataTest {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public static void main(String[] args) {
		ZipCodeCsv csv = new ZipCodeCsv();
		Date start = new Date();
		try {
			System.out.println("start");
			csv.read("src/test/resources/KEN_ALL.CSV");
			Set<List<String>> cities = csv.toCityList();
			FileUtils.write(new File("src/test/resources/cities.json"), MAPPER.writeValueAsString(cities), "UTF-8");
			Set<List<String>> prefectures = csv.toPrefectureList();
			FileUtils.write(new File("src/test/resources/prefectures.json"), MAPPER.writeValueAsString(prefectures), "UTF-8");

//			Map<String, List<Map<String, String>>> zipCodeMap = csv.toZipCodeMap();
//			for (String zipCode : zipCodeMap.keySet()) {
//				FileUtils.write(new File("src/test/resources/" + zipCode + ".json"), MAPPER.writeValueAsString(zipCodeMap.get(zipCode)), "UTF-8");
//			}
//			csv.write("src/test/resources/ken_all.json");
//			for (ZipCodeData data : list) {
//				if (data.isDivided()) {
//					System.out.println("town:" + data.getTownLength()  + ":" + MAPPER.writeValueAsString(data.getValues()));
//				}
//				if (data.isDividedKana()) {
//					System.out.println("townKana:" + data.getTownKanaLength()  + ":" + MAPPER.writeValueAsString(data.getValues()));
//				}
//			}
			Date end = new Date();
			System.out.println("end:" + (end.getTime() - start.getTime()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
