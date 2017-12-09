package com.purejadeite.util.collection;

import static com.purejadeite.util.RoughlyConverter.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * 値がObjectのMap用ラッパー
 *
 * @author mitsuhiroseino
 *
 */
public class RoughlyMap<K> implements Map<K, Object> {

	protected Map<K, Object> map;

	public RoughlyMap(Map<K, Object> map) {
		this.map = map;
	}

	public Map<K, Object> getOriginalMap() {
		return map;
	}

	/**
	 * <pre>
	 * Mapから値をMapで取得します。
	 * 値がMapでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Map形式の値
	 */
	@SuppressWarnings("hiding")
	public <K> Map<K, Object> getMap(K key) {
		return intoMap(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をListで取得します。
	 * 値がListでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return List形式の値
	 */
	public <E> List<E> getList(K key) {
		return intoList(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をStringで取得します。
	 * 値がStringでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return String形式の値
	 */
	public String getString(K key) {
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
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Boolean形式の値
	 */
	public Boolean getBoolean(K key) {
		return intoBoolean(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をbooleanで取得します。
	 * getBooleanで取得できた値がnullの場合はfalseを、
	 * それ以外の場合は取得できた値をbooleanに変換した値を返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return boolean形式の値
	 */
	public boolean getBooleanValue(K key) {
		return intoBooleanValue(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をNumberで取得します。
	 * Numberの場合はその値を、
	 * CharSequenceの場合はNumberFormatでパースした値を、
	 * パースに失敗した場合および上記以外はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Number形式の値
	 */
	public Number getNumber(K key) {
		return intoNumber(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をByteで取得します。
	 * 値がByteでない場合はnullを返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Byte形式の値
	 */
	public Byte getByte(K key) {
		return intoByte(map.get(key));
	}

	/**
	 * <pre>
	 * Mapから値をbyteで取得します。
	 * getByteで取得できた値がnullの場合は0を、
	 * それ以外の場合は取得できた値をbyteに変換した値を返します。
	 * </pre>
	 *
	 * @param map
	 *            対象のMap
	 * @param key
	 *            キー
	 * @return Byte形式の値
	 */
	public byte getByteValue(K key) {
		return intoByteValue(map.get(key));
	}

	public Integer getInteger(K key) {
		return intoInteger(map.get(key));
	}

	public int getIntValue(K key) {
		return intoIntValue(map.get(key));
	}

	public Short getShort(K key) {
		return intoShort(map.get(key));
	}

	public short getShortValue(K key) {
		return intoShortValue(map.get(key));
	}

	public Long getLong(K key) {
		return intoLong(map.get(key));
	}

	public long getLongValue(K key) {
		return intoLongValue(map.get(key));
	}

	public Float getFloat(K key) {
		return intoFloat(map.get(key));
	}

	public float getFloatValue(K key) {
		return intoFloatValue(map.get(key));
	}

	public Double getDouble(K key) {
		return intoDouble(map.get(key));
	}

	public double getDoubleValue(K key) {
		return intoDoubleValue(map.get(key));
	}

	public BigDecimal getBigDecimal(K key) {
		return intoBigDecimal(map.get(key));
	}

	public BigInteger getBigInteger(K key) {
		return intoBigInteger(map.get(key));
	}

	public Date getDate(K key) {
		return intoDate(map.get(key));
	}

	public java.sql.Date getSqlDate(K key) {
		return intoSqlDate(map.get(key));
	}

	public Timestamp getTimestamp(K key) {
		return intoTimestamp(map.get(key));
	}

	public <V extends Object> Map<K, V> getMap(K key, Map<K, V> dflt) {
		return intoMap(map.get(key), dflt);
	}

	public <V extends Object> List<V> getList(K key, List<V> dflt) {
		return intoList(map.get(key), dflt);
	}

	public String getString(K key, String dflt) {
		return intoString(map.get(key), dflt);
	}

	public Boolean getBoolean(K key, Boolean dflt) {
		return intoBoolean(map.get(key), dflt);
	}

	public boolean getBooleanValue(K key, boolean dflt) {
		return intoBooleanValue(map.get(key), dflt);
	}

	public Number getNumber(K key, Number dflt) {
		return intoNumber(map.get(key), dflt);
	}

	public Byte getByte(K key, Byte dflt) {
		return intoByte(map.get(key), dflt);
	}

	public byte getByteValue(K key, byte dflt) {
		return intoByteValue(map.get(key), dflt);
	}

	public Integer getInteger(K key, Integer dflt) {
		return intoInteger(map.get(key), dflt);
	}

	public int getIntValue(K key, int dflt) {
		return intoIntValue(map.get(key), dflt);
	}

	public Short getShort(K key, Short dflt) {
		return intoShort(map.get(key), dflt);
	}

	public short getShortValue(K key, Short dflt) {
		return intoShortValue(map.get(key), dflt);
	}

	public Long getLong(K key, Long dflt) {
		return intoLong(map.get(key), dflt);
	}

	public long getLongValue(K key, long dflt) {
		return intoLongValue(map.get(key), dflt);
	}

	public Float getFloat(K key, Float dflt) {
		return intoFloat(map.get(key), dflt);
	}

	public float getFloatValue(K key, float dflt) {
		return intoFloatValue(map.get(key), dflt);
	}

	public Double getDouble(K key, Double dflt) {
		return intoDouble(map.get(key), dflt);
	}

	public double getDoubleValue(K key, double dflt) {
		return intoDoubleValue(map.get(key), dflt);
	}

	public BigDecimal getBigDecimal(K key, BigDecimal dflt) {
		return intoBigDecimal(map.get(key), dflt);
	}

	public BigInteger getBigInteger(K key, BigInteger dflt) {
		return intoBigInteger(map.get(key), dflt);
	}

	public Date getDate(K key, Date dflt) {
		return intoDate(map.get(key), dflt);
	}

	public java.sql.Date getSqlDate(K key, java.sql.Date dflt) {
		return intoSqlDate(map.get(key), dflt);
	}

	public Timestamp getTimestamp(K key, Timestamp dflt) {
		return intoTimestamp(map.get(key), dflt);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return map.get(key);
	}

	@Override
	public Object put(K key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends Object> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Object> values() {
		return map.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, Object>> entrySet() {
		return map.entrySet();
	}

}
