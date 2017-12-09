package com.purejadeite.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ZipCodeCsv {

	private File csvFile = null;

	private List<ZipCodeData> list = null;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 郵便番号データ(http://www.post.japanpost.jp/zipcode/download.html)を読み込みます
	public List<ZipCodeData> read(String filePath) throws IOException {
		List<ZipCodeData> list = new ArrayList<>();
		csvFile = new File(filePath);
		List<String> lines = FileUtils.readLines(csvFile, "UTF-8");
		for (String line : lines) {
			StringTokenizer token = new StringTokenizer(line, ",");
			int index = 0;
			ZipCodeData data = new ZipCodeData();
            while (token.hasMoreTokens()) {
            	String value = token.nextToken();
            	if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
            		// "は削除
            		value = value.substring(1, value.length() - 1);
            	}
            	data.set(index, value);
            	index++;
            }
            list.add(data);
		}
		// ソート
		Collections.sort(list);
		this.list = merge(list);
		return this.list;
	}

	// 同じ郵便番号のデータをマージした郵便番号データを取得します。
	private List<ZipCodeData> merge(List<ZipCodeData> orglist) {
		List<ZipCodeData> list = new ArrayList<>();
		ZipCodeData dataI = null;
		ZipCodeData dataJ = null;
		int size = orglist.size();
		for (int i = 0; i < size - 1; i++) {
			dataI = orglist.get(i);
			if (!dataI.isDisabled()) {
				// 規定の文字列長を超えている場合
				for (int j = i + 1; j < size; j++) {
					dataJ = orglist.get(j);
					if (dataI.isJoinable(dataJ)) {
						// 郵便番号&県&市区町村が同じ
						String townI = dataI.getTown();
						String townKanaI = dataI.getTownKana();
						String townJ = dataJ.getTown();
						String townKanaJ = dataJ.getTownKana();
						if (dataI.isStart()) {
							// 町名
							dataI.setTown(townI + townJ);
							dataI.setJoined(true);
							// 町名カナ
							dataI.setTownKana(townKanaI + townKanaJ);
							dataI.setJoinedKana(true);
							// 結合済みのものは非活性に
							dataJ.setDisabled(true);
							if (dataJ.isEnd()) {
								break;
							}
						} else {
							if (dataI.isDivided() || dataI.isDividedKana()) {
								// 分割されていると思われるが"（"や"）"が無い
								System.out.println("warn:" + dataI.getZipCode() + "-" + dataI.getTown());
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < size - 2; i++) {
			dataI = orglist.get(i);
			if (!dataI.isDisabled()) {
				// 有効なデータのみ取得
				list.add(dataI);
			}
		}
		return list;
	}

	public Set<List<String>> toPrefectureList() {
		Set<List<String>> list = new LinkedHashSet<>();
		for (ZipCodeData data : this.list) {
			List<String> values = new ArrayList<>();
			values.add(data.getPrettyPrefecture());
			values.add(data.getPrettyPrefectureKana());
			list.add(values);
		}
		return list;
	}

	public Set<List<String>> toCityList() {
		Set<List<String>> list = new LinkedHashSet<>();
		for (ZipCodeData data : this.list) {
			List<String> values = new ArrayList<>();
			values.add(data.getPrettyPrefecture());
			values.add(data.getPrettyPrefectureKana());
			values.add(data.getPrettyCity());
			values.add(data.getPrettyCityKana());
			list.add(values);
		}
		return list;
	}

	// 郵便番号の上3桁毎に振り分けたデータを取得します。
	public Map<String, List<Map<String, String>>> toZipCodeMap(String... properties) {
		List<Map<String, String>> mapList = this.toMapList(properties);
		Map<String, List<Map<String, String>>> map = new LinkedHashMap<>();
		for (Map<String, String> data : mapList) {
			String zipCode = data.get("zipCode");
			String zipCode3 = zipCode.substring(0, 3);
			List<Map<String, String>> list = map.get(zipCode3);
			if (list == null) {
				list = new ArrayList<>();
				map.put(zipCode3, list);
			}
			list.add(data);
		}
		return map;
	}

	public List<Map<String, String>> toMapList(String... properties) {
		List<Map<String, String>> list = new ArrayList<>();
		for (ZipCodeData data : this.list) {
			list.add(data.getData(properties));
		}
		return list;
	}

//	public Map<String, Object> getNestedMap(Map<String, Object> map, String key, String sepalator) {
//		String[] keys = null;
//		if (sepalator == null) {
//			keys = new String[key.length()];
//			for (int i = 0; i < key.length(); i++) {
//				keys[i] = String.valueOf(key.charAt(i));
//			}
//		} else {
//			keys = StringUtils.split(key, sepalator);
//		}
//		Map<String, Object> current = map;
//		Map<String, Object> next = null;
//		int len = keys.length;
//		for (int i = 0; i < len; i++) {
//			next = (Map<String, Object>) current.get(keys[i]);
//			if (next == null) {
//				next = new LinkedHashMap<>();
//				current.put(keys[i], next);
//			}
//			current = next;
//		}
//		return current;
//	}

	// 郵便番号データをList形式で取得します
	public List<List<String>> toList() {
		List<List<String>> list = new ArrayList<>();
		for (ZipCodeData data : this.list) {
			list.add(data.getValues());
		}
		return list;
	}

	// 郵便番号データをJSON形式で取得します
	public String toJson() throws JsonProcessingException {
		List<List<String>> list = new ArrayList<>();
		for (ZipCodeData data : this.list) {
			list.add(data.getValues());
		}
		return MAPPER.writeValueAsString(list);

	}

	// 郵便番号データをJSON形式のファイルに出力します。
	public void write(String filePath) throws JsonProcessingException, IOException {
		FileUtils.write(new File(filePath), toJson(), "UTF-8");
	}

}
