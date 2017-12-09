package com.purejadeite.util.collection;

import static com.purejadeite.util.RoughlyConverter.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * 値がObjectのMap用ユーティリティ
 *
 * @author mitsuhiroseino
 *
 */
public class RoughlyMapUtils {

	/**
	 * <pre>
	 * Mapから値をMapで取得します。
	 * 値がMapでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Map形式の値
	 */
	public static <K, V> Map<K, V> getMap(Map<K, ? extends Object> map, K key) {
		return intoMap(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をListで取得します。
	 * 値がListでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return List形式の値
	 */
	public static <K, E> List<E> getList(Map<K, ? extends Object> map, K key) {
		return intoList(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をStringで取得します。
	 * 値がStringでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return String形式の値
	 */
	public static <K> String getString(Map<K, ? extends Object> map, K key) {
		return intoString(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をBooleanで取得します。
	 * 値がBooleanの場合はその値を、
	 * Stringの場合はBoolean.valueOfで取得できる値を、
	 * Numberの場合は0をfalse、それ以外をtrueとした値を、
	 * それ以外はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Boolean形式の値
	 */
	public static <K> Boolean getBoolean(Map<K, ? extends Object> map, K key) {
		return intoBoolean(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をbooleanで取得します。
	 * getBooleanで取得できた値がnullの場合はfalseを、
	 * それ以外の場合は取得できた値をbooleanに変換した値を返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return boolean形式の値
	 */
	public static <K> boolean getBooleanValue(Map<K, ? extends Object> map, K key) {
		return intoBooleanValue(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をNumberで取得します。
	 * Numberの場合はその値を、
	 * CharSequenceの場合はNumberFormatでパースした値を、
	 * パースに失敗した場合および上記以外はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Number形式の値
	 */
	public static <K> Number getNumber(Map<K, ? extends Object> map, K key) {
		return intoNumber(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をByteで取得します。
	 * 値がByteでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Byte形式の値
	 */
	public static <K> Byte getByte(Map<K, ? extends Object> map, K key) {
		return intoByte(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をbyteで取得します。
	 * getByteで取得できた値がnullの場合は0を、
	 * それ以外の場合は取得できた値をbyteに変換した値を返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Byte形式の値
	 */
	public static <K> byte getByteValue(Map<K, ? extends Object> map, K key) {
		return intoByteValue(map.get(key));
	}

	public static <K> Integer getInteger(Map<K, ? extends Object> map, K key) {
		return intoInteger(map.get(key));
	}

	public static <K> int getIntValue(Map<K, ? extends Object> map, K key) {
		return intoIntValue(map.get(key));
	}

	public static <K> Short getShort(Map<K, ? extends Object> map, K key) {
		return intoShort(map.get(key));
	}

	public static <K> short getShortValue(Map<K, ? extends Object> map, K key) {
		return intoShortValue(map.get(key));
	}

	public static <K> Long getLong(Map<K, ? extends Object> map, K key) {
		return intoLong(map.get(key));
	}

	public static <K> long getLongValue(Map<K, ? extends Object> map, K key) {
		return intoLongValue(map.get(key));
	}

	public static <K> Float getFloat(Map<K, ? extends Object> map, K key) {
		return intoFloat(map.get(key));
	}

	public static <K> float getFloatValue(Map<K, ? extends Object> map, K key) {
		return intoFloatValue(map.get(key));
	}

	public static <K> Double getDouble(Map<K, ? extends Object> map, K key) {
		return intoDouble(map.get(key));
	}

	public static <K> double getDoubleValue(Map<K, ? extends Object> map, K key) {
		return intoDoubleValue(map.get(key));
	}

	public static <K> BigDecimal getBigDecimal(Map<K, ? extends Object> map, K key) {
		return intoBigDecimal(map.get(key));
	}

	public static <K> BigInteger getBigInteger(Map<K, ? extends Object> map, K key) {
		return intoBigInteger(map.get(key));
	}

	public static <K> Date getDate(Map<K, ? extends Object> map, K key) {
		return intoDate(map.get(key));
	}

	public static <K> java.sql.Date getSqlDate(Map<K, ? extends Object> map, K key) {
		return intoSqlDate(map.get(key));
	}

	public static <K> Timestamp getTimestamp(Map<K, ? extends Object> map, K key) {
		return intoTimestamp(map.get(key));
	}

	public static <K, V> Map<K, V> getMap(Map<K, ? extends Object> map, K key, Map<K, V> dflt) {
		return intoMap(map.get(key), dflt);
	}

	public static <K, E> List<E> getList(Map<K, ? extends Object> map, K key, List<E> dflt) {
		return intoList(map.get(key), dflt);
	}

	public static <K> String getString(Map<K, ? extends Object> map, K key, String dflt) {
		return intoString(map.get(key), dflt);
	}

	public static <K> Boolean getBoolean(Map<K, ? extends Object> map, K key, Boolean dflt) {
		return intoBoolean(map.get(key), dflt);
	}

	public static <K> boolean getBooleanValue(Map<K, ? extends Object> map, K key, boolean dflt) {
		return intoBooleanValue(map.get(key), dflt);
	}

	public static <K> Number getNumber(Map<K, ? extends Object> map, K key, Number dflt) {
		return intoNumber(map.get(key), dflt);
	}

	public static <K> Byte getByte(Map<K, ? extends Object> map, K key, Byte dflt) {
		return intoByte(map.get(key), dflt);
	}

	public static <K> byte getByteValue(Map<K, ? extends Object> map, K key, byte dflt) {
		return intoByteValue(map.get(key), dflt);
	}

	public static <K> Integer getInteger(Map<K, ? extends Object> map, K key, Integer dflt) {
		return intoInteger(map.get(key), dflt);
	}

	public static <K> int getIntValue(Map<K, ? extends Object> map, K key, int dflt) {
		return intoIntValue(map.get(key), dflt);
	}

	public static <K> Short getShort(Map<K, ? extends Object> map, K key, Short dflt) {
		return intoShort(map.get(key), dflt);
	}

	public static <K> short getShortValue(Map<K, ? extends Object> map, K key, Short dflt) {
		return intoShortValue(map.get(key), dflt);
	}

	public static <K> Long getLong(Map<K, ? extends Object> map, K key, Long dflt) {
		return intoLong(map.get(key), dflt);
	}

	public static <K> long getLongValue(Map<K, ? extends Object> map, K key, long dflt) {
		return intoLongValue(map.get(key), dflt);
	}

	public static <K> Float getFloat(Map<K, ? extends Object> map, K key, Float dflt) {
		return intoFloat(map.get(key), dflt);
	}

	public static <K> float getFloatValue(Map<K, ? extends Object> map, K key, float dflt) {
		return intoFloatValue(map.get(key), dflt);
	}

	public static <K> Double getDouble(Map<K, ? extends Object> map, K key, Double dflt) {
		return intoDouble(map.get(key), dflt);
	}

	public static <K> double getDoubleValue(Map<K, ? extends Object> map, K key, double dflt) {
		return intoDoubleValue(map.get(key), dflt);
	}

	public static <K> BigDecimal getBigDecimal(Map<K, ? extends Object> map, K key, BigDecimal dflt) {
		return intoBigDecimal(map.get(key), dflt);
	}

	public static <K> BigInteger getBigInteger(Map<K, ? extends Object> map, K key, BigInteger dflt) {
		return intoBigInteger(map.get(key), dflt);
	}

	public static <K> Date getDate(Map<K, ? extends Object> map, K key, Date dflt) {
		return intoDate(map.get(key), dflt);
	}

	public static <K> java.sql.Date getSqlDate(Map<K, ? extends Object> map, K key, java.sql.Date dflt) {
		return intoSqlDate(map.get(key), dflt);
	}

	public static <K> Timestamp getTimestamp(Map<K, ? extends Object> map, K key, Timestamp dflt) {
		return intoTimestamp(map.get(key), dflt);
	}

	public static <K, V> V get(Map<K, V> map, @SuppressWarnings("unchecked") K... keys) {
		for (K key : keys) {
			V value = map.get(key);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * 配列形式のキーのいずれかで取得できたnull以外の値が、
	 * Listの場合はそのまま返し、List以外の値であればListに入れて返します
	 *
	 * @param map 取得元
	 * @param keys キー
	 * @return List
	 */
	public static <K, E> List<E> getAsList(Map<K, ? extends Object> map, K[] keys) {
		List<E> values = null;
		for (K key : keys) {
			values = getAsList(map, key);
			if (values != null) {
				return values;
			}
		}
		return null;
	}

	/**
	 * 取得できたnull以外の値が、Listの場合はそのまま返し、List以外の値であればListに入れて返します
	 *
	 * @param map 取得元
	 * @param key キー
	 * @return List
	 */
	public static <K, E> List<E> getAsList(Map<K, ? extends Object> map, K key) {
		List<E> values = getList(map, key);
		if (values == null) {
			try {
				@SuppressWarnings("unchecked")
				E value = (E) map.get(key);
				if (value != null) {
					values = new ArrayList<>();
					values.add(value);
				}
			} catch(ClassCastException e){
				return null;
			}
		}
		return values;
	}

}
