package com.purejadeite.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 郵便番号CSVの1行分のデータ
 *
 * @author mitsuhiroseino
 *
 */
public class ZipCodeData implements Comparable<ZipCodeData> {

	private static final int NO_DIVIDED_TOWN_LENGTH = 30;
	private static final int NO_DIVIDED_TOWN_KANA_LENGTH = 60;

	/**
	 * CSVのindexとプロパティの対応
	 */
	private static final String[] PROPERTIES = { "organizationCode",
			"zipCodeOld", "zipCode", "prefectureKana", "cityKana", "townKana",
			"prefecture", "city", "town", "doubleCd", "detailCd", "chomeCd",
			"commonCd", "updateCd", "updateReason" };

	/**
	 * 出力対象プロパティ：全て
	 */
	public static final String[] ALL_DATA_PROPERTIES = PROPERTIES;

	/**
	 * 出力対象プロパティ：郵便番号(7桁)、都道府県名（カナ）、市区町村名（カナ）、町域名（カナ）、都道府県名、市区町村名、町域名
	 */
	public static final String[] DEFAULT_DATA_PROPERTIES = { "zipCode",
			"prefectureKana", "cityKana", "townKana", "prefecture", "city",
			"town" };

	/**
	 * 出力対象プロパティ：郵便番号(7桁)、町域名（カナ）、都道府県名、市区町村名、町域名
	 */
	public static final String[] FROM_ZIPCODE_DATA_PROPERTIES = { "zipCode",
			"townKana", "prefecture", "city", "town" };

	/**
	 * 出力対象プロパティ：郵便番号(7桁)、都道府県名（カナ）、市区町村名（カナ）、町域名（カナ）、都道府県名、市区町村名、町域名
	 */
	public static final String[] FROM_ADDRESS_DATA_PROPERTIES = { "zipCode",
			"prefectureKana", "cityKana", "townKana", "prefecture", "city",
			"town" };

	// 出力をひらがなで行う
	private boolean hiragana = false;

	// 1. 全国地方公共団体コード（JIS X0401、X0402）………　半角数字
	private String organizationCode = null;
	// 2. （旧）郵便番号（5桁）………………………………………　半角数字
	private String zipCodeOld = null;
	// 3. 郵便番号（7桁）………………………………………　半角数字
	private String zipCode = null;
	// 4. 都道府県名　…………　半角カタカナ（コード順に掲載）　（注1）
	private String prefectureKana = null;
	// 5. 市区町村名　…………　半角カタカナ（コード順に掲載）　（注1）
	private String cityKana = null;
	// 6. 町域名　………………　半角カタカナ（五十音順に掲載）　（注1）
	private String townKana = null;
	// 7. 都道府県名　…………　漢字（コード順に掲載）　（注1,2）
	private String prefecture = null;
	// 8. 市区町村名　…………　漢字（コード順に掲載）　（注1,2）
	private String city = null;
	// 9. 町域名　………………　漢字（五十音順に掲載）　（注1,2）
	private String town = null;
	// 10. 一町域が二以上の郵便番号で表される場合の表示　（注3）　（「1」は該当、「0」は該当せず）
	private String doubleCd = null;
	// 11. 小字毎に番地が起番されている町域の表示　（注4）　（「1」は該当、「0」は該当せず）
	private String detailCd = null;
	// 12. 丁目を有する町域の場合の表示　（「1」は該当、「0」は該当せず）
	private String chomeCd = null;
	// 13. 一つの郵便番号で二以上の町域を表す場合の表示　（注5）　（「1」は該当、「0」は該当せず）
	private String commonCd = null;
	// 14. 更新の表示（注6）（「0」は変更なし、「1」は変更あり、「2」廃止（廃止データのみ使用））
	private String updateCd = null;
	// 15.
	// 変更理由　（「0」は変更なし、「1」市政・区政・町政・分区・政令指定都市施行、「2」住居表示の実施、「3」区画整理、「4」郵便区調整等、「5」訂正、「6」廃止（廃止データのみ使用））
	private String updateReason = null;

	// 町名カナを結合済み
	private boolean joinedKana = false;

	// 町名を結合済み
	private boolean joined = false;

	// 重複あり
	private boolean doubled = false;

	// 分割の始まり行(townKana,townの長さが規定値以上で、"（"or"("を含む)
	private boolean start = false;

	// 分割の終わり行("）"or")"を含む)
	private boolean end = false;

	// 市区町村名（カナ）が2レコード以上に分割されている可能性がある
	private boolean dividedKana = false;

	// 市区町村名が2レコード以上に分割されている可能性がある
	private boolean divided = false;

	// 市区町村名（カナ）の文字列長
	private int townKanaLength = 0;

	// 市区町村名の文字列長
	private int townLength = 0;

	// 有効／無効（結合したら無効に）
	private boolean disabled = false;

	public ZipCodeData() {
		super();
	}

	public ZipCodeData(boolean hiragana) {
		super();
		this.hiragana = hiragana;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public boolean isJoinedKana() {
		return joinedKana;
	}

	public void setJoinedKana(boolean joinedKana) {
		this.joinedKana = joinedKana;
	}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}

	public int getTownKanaLength() {
		return townKanaLength;
	}

	public int getTownLength() {
		return townLength;
	}


	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getZipCodeOld() {
		return zipCodeOld;
	}

	public void setZipCodeOld(String zipCodeOld) {
		this.zipCodeOld = zipCodeOld;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPrefectureKana() {
		return prefectureKana;
	}

	public void setPrefectureKana(String prefectureKana) {
		this.prefectureKana = prefectureKana;
	}

	public String getCityKana() {
		return cityKana;
	}

	public void setCityKana(String cityKana) {
		this.cityKana = cityKana;
	}

	public String getTownKana() {
		return townKana;
	}

	public void setTownKana(String townKana) {
		this.townKana = townKana;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getDoubleCd() {
		return doubleCd;
	}

	public void setDoubleCd(String doubleCd) {
		this.doubleCd = doubleCd;
	}

	public String getDetailCd() {
		return detailCd;
	}

	public void setDetailCd(String detailCd) {
		this.detailCd = detailCd;
	}

	public String getChomeCd() {
		return chomeCd;
	}

	public void setChomeCd(String chomeCd) {
		this.chomeCd = chomeCd;
	}

	public String getCommonCd() {
		return commonCd;
	}

	public void setCommonCd(String commonCd) {
		this.commonCd = commonCd;
	}

	public String getUpdateCd() {
		return updateCd;
	}

	public void setUpdateCd(String updateCd) {
		this.updateCd = updateCd;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public boolean isDivided() {
		return divided;
	}

	public void setDivided(boolean value) {
		divided = value;
	}

	public boolean isDividedKana() {
		return dividedKana;
	}

	public void setDividedKana(boolean value) {
		dividedKana = value;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean value) {
		disabled = value;
	}

	public boolean isDoubled() {
		return doubled;
	}

	public void setDoubled(boolean doubled) {
		this.doubled = doubled;
	}

	public String getPrettyPrefectureKana() {
		return toPretty(prefectureKana);
	}

	public String getPrettyCityKana() {
		return toPretty(cityKana);
	}

	public String getPrettyTownKana() {
		return toPretty(townKana);
	}

	public String getPrettyPrefecture() {
		return StringConvertUtils.toHalf(prefecture);
	}

	public String getPrettyCity() {
		return StringConvertUtils.toHalf(city);
	}

	public String getPrettyTown() {
		return StringConvertUtils.toHalf(town);
	}

	public String toPretty(String origin) {
		String text = StringConvertUtils.toFullKatakana(origin);
		if (hiragana) {
			text = StringConvertUtils.toHiragana(text);
		}
		return text;
	}

	// プロパティを設定する
	public void set(int index, String value) {
		if (index < PROPERTIES.length) {
			set(PROPERTIES[index], value);
		}
	}

	// プロパティを設定する
	public void set(String property, String value) {
		if ("organizationCode".equals(property)) {
			setOrganizationCode(value);
		} else if ("zipCodeOld".equals(property)) {
			// 空白は削除
			setZipCodeOld(value.trim());
			// setZipCodeOld(value);
		} else if ("zipCode".equals(property)) {
			setZipCode(value);
		} else if ("prefectureKana".equals(property)) {
			setPrefectureKana(value);
		} else if ("cityKana".equals(property)) {
			setCityKana(value);
		} else if ("townKana".equals(property)) {
			setTownKana(value);
			if (value != null) {
				townKanaLength = value.length();
				if (NO_DIVIDED_TOWN_KANA_LENGTH < townKanaLength) {
					// NO_DIVIDED_TOWN_KANA_LENGTHを超える場合は分割されている可能性あり
					dividedKana = true;
				}
				if (-1 < value.indexOf("(") && value.indexOf(")") == -1) {
					// "("を含み")"を含まないものは結合の開始
					start = true;
				} else if (value.indexOf("(") == -1 && -1 < value.indexOf(")")) {
					// "("を含まず")"を含むものは結合の終了
					end = true;
				}
			}
		} else if ("prefecture".equals(property)) {
			setPrefecture(value);
		} else if ("city".equals(property)) {
			setCity(value);
		} else if ("town".equals(property)) {
			setTown(value);
			if (value != null) {
				townLength = value.length();
				if (NO_DIVIDED_TOWN_LENGTH < townLength) {
					// NO_DIVIDED_TOWN_LENGTHを超える場合は分割されている可能性あり
					divided = true;
				}
				if (-1 < value.indexOf("（") && value.indexOf("）") == -1) {
					// "("を含み")"を含まないものは結合の開始
					start = true;
				} else if (value.indexOf("（") == -1 && -1 < value.indexOf("）")) {
					// "("を含まず")"を含むものは結合の終了
					end = true;
				}
			}
		} else if ("doubleCd".equals(property)) {
			setDoubleCd(value);
		} else if ("detailCd".equals(property)) {
			setDetailCd(value);
		} else if ("chomeCd".equals(property)) {
			setChomeCd(value);
		} else if ("commonCd".equals(property)) {
			setCommonCd(value);
		} else if ("updateCd".equals(property)) {
			setUpdateCd(value);
			if (value.equals("2")) {
				// 廃止の場合は非活性
				disabled = true;
			}
		} else if ("updateReason".equals(property)) {
			setUpdateReason(value);
		}
	}

	public String get(String property) {
		if ("organizationCode".equals(property)) {
			return getOrganizationCode();
		} else if ("zipCodeOld".equals(property)) {
			return getZipCodeOld();
		} else if ("zipCode".equals(property)) {
			return getZipCode();
		} else if ("prefectureKana".equals(property)) {
			return getPrettyPrefectureKana();
		} else if ("cityKana".equals(property)) {
			return getPrettyCityKana();
		} else if ("townKana".equals(property)) {
			return getPrettyTownKana();
		} else if ("prefecture".equals(property)) {
			return getPrettyPrefecture();
		} else if ("city".equals(property)) {
			return getPrettyCity();
		} else if ("town".equals(property)) {
			return getPrettyTown();
		} else if ("doubleCd".equals(property)) {
			return getDoubleCd();
		} else if ("detailCd".equals(property)) {
			return getDetailCd();
		} else if ("chomeCd".equals(property)) {
			return getChomeCd();
		} else if ("commonCd".equals(property)) {
			return getCommonCd();
		} else if ("updateCd".equals(property)) {
			return getUpdateCd();
		} else if ("updateReason".equals(property)) {
			return getUpdateReason();
		}
		return null;
	}

	/**
	 * 値の配列を取得する
	 * @param properties
	 * @return
	 */
	public List<String> getValues(String... properties) {
		if (properties.length == 0) {
			properties = DEFAULT_DATA_PROPERTIES;
		}
		List<String> values = new ArrayList<>();
		for (String property : properties) {
			values.add(get(property));
		}
		return values;
	}

	/**
	 * プロパティ名と値を対で取得する
	 * @param properties
	 * @return
	 */
	public Map<String, String> getData(String... properties) {
		if (properties.length == 0) {
			properties = DEFAULT_DATA_PROPERTIES;
		}
		Map<String, String> data = new LinkedHashMap<>();
		for (String property : properties) {
			data.put(property, get(property));
		}
		return data;
	}

	@Override
	public String toString() {
		String string = "ZipCodeData [";
		for (int i = 0; i < PROPERTIES.length; i++) {
			if (i != 0) {
				string += ",";
			}
			String property = PROPERTIES[i];
			string += property + "=" + get(property) + ",";
		}
		string += "]";
		return string;
	}

	public boolean equals(ZipCodeData data) {
		// townKanaとtownは分割されている可能性がある為、比較には含めない
		return this.organizationCode.equals(data.organizationCode)
				&& this.zipCodeOld.equals(data.zipCodeOld)
				&& this.zipCode.equals(data.zipCode)
				&& this.prefectureKana.equals(data.prefectureKana)
				&& this.cityKana.equals(data.cityKana)
				&& this.prefecture.equals(data.prefecture)
				&& this.city.equals(data.city)
				&& this.doubleCd.equals(data.doubleCd)
				&& this.detailCd.equals(data.detailCd)
				&& this.chomeCd.equals(data.chomeCd)
				&& this.commonCd.equals(data.commonCd)
				&& this.updateCd.equals(data.updateCd)
				&& this.updateReason.equals(data.updateReason);
	}

	@Override
	public int compareTo(ZipCodeData o) {
		// townKanaとtownは分割されている可能性がある為、比較には含めない
		int result = 0;
		result = organizationCode.compareTo(o.organizationCode);
		if (result != 0) {
			return result;
		}
		result = zipCode.compareTo(o.zipCode);
		if (result != 0) {
			return result;
		}
		result = prefectureKana.compareTo(o.prefectureKana);
		if (result != 0) {
			return result;
		}
		result = cityKana.compareTo(o.cityKana);
		if (result != 0) {
			return result;
		}
		result = prefecture.compareTo(o.prefecture);
		if (result != 0) {
			return result;
		}
		result = city.compareTo(o.city);
		if (result != 0) {
			return result;
		}
		return 0;
	}

	/**
	 * 結合可能か
	 * @param data
	 * @return
	 */
	public boolean isJoinable(ZipCodeData data) {
		return this.isDisabled() && data.isDisabled()
				&& this.zipCode.equals(data.zipCode)
				&& this.prefectureKana.equals(data.prefectureKana)
				&& this.cityKana.equals(data.cityKana)
				&& this.prefecture.equals(data.prefecture)
				&& this.city.equals(data.city);
	}

	public boolean join(ZipCodeData dataJ) {
		if (this.isStart()) {
			// 町名
			this.setTown(this.getTown() + dataJ.getTown());
			this.setJoined(true);
			// 町名カナ
			this.setTownKana(this.getTownKana() + dataJ.getTownKana());
			this.setJoinedKana(true);
			// 結合済みのものは非活性に
			dataJ.setDisabled(true);
			// これ以降は結合するものが無い場合はfalseを返す
			return !dataJ.isEnd();
		} else {
			if (this.isDivided() || this.isDividedKana()) {
				// 分割されていると思われるが"（"や"）"が無い
				System.out.println("warn:" + this.getZipCode() + "-" + this.getTown());
			}
			return true;
		}
	}

}
