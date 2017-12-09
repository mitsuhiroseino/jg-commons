package com.purejadeite.util.collection;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * ネストしたmapの値にアクセスしやすくするためのラッパークラス
 * get,put,remove,containsKeyのkeyに配列またはCollectionを含む値を渡した場合に、ネストした値を取得します。
 * @author mitsuhiroseino
 */
public class StringKeyNestedMap extends NestedMap<String> {

	private static final String DEFAULT_KEY_SEPARATOR = ".";

	protected static final String INDEX_PREFIX = "[";

	protected static final String INDEX_SUFFIX = "]";

	private String keySeparator;

	protected String indexPrefix;

	protected String indexSuffix;

	public StringKeyNestedMap(Map<String, Object> map) {
		this(map, DEFAULT_KEY_SEPARATOR, false, MAP_CLASS, LIST_CLASS);
	}

	public StringKeyNestedMap(Map<String, Object> map, String keySeparator) {
		this(map, keySeparator, false, MAP_CLASS, LIST_CLASS);
	}

	public StringKeyNestedMap(Map<String, Object> map, boolean lazy) {
		this(map, DEFAULT_KEY_SEPARATOR, lazy, MAP_CLASS, LIST_CLASS);
	}

	@SuppressWarnings("rawtypes")
	public StringKeyNestedMap(Map<String, Object> map, boolean lazy, Class<? extends Map> mapClass) {
		this(map, DEFAULT_KEY_SEPARATOR, lazy, mapClass, LIST_CLASS);
	}

	@SuppressWarnings("rawtypes")
	public StringKeyNestedMap(Map<String, Object> map, boolean lazy, Class<? extends Map> mapClass,
			Class<? extends List> listClass) {
		this(map, DEFAULT_KEY_SEPARATOR, lazy, mapClass, listClass);
	}

	public StringKeyNestedMap(Map<String, Object> map, String keySeparator, boolean lazy) {
		this(map, keySeparator, lazy, MAP_CLASS, LIST_CLASS);
	}

	@SuppressWarnings("rawtypes")
	public StringKeyNestedMap(Map<String, Object> map, String keySeparator, boolean lazy, Class<? extends Map> mapClass) {
		this(map, keySeparator, lazy, mapClass, LIST_CLASS);
	}

	@SuppressWarnings("rawtypes")
	public StringKeyNestedMap(Map<String, Object> map, String keySeparator, boolean lazy, Class<? extends Map> mapClass,
			Class<? extends List> listClass) {
		super(map, lazy, mapClass, listClass);
		this.keySeparator = keySeparator;
		this.indexPrefix = INDEX_PREFIX;
		this.indexSuffix = INDEX_SUFFIX;
	}

	protected List<String> getKeys(Object key) {
		Object keys = null;
		if (key instanceof String) {
			keys = StringUtils.split((String)key, keySeparator);
		} else {
			keys = key;
		}
		return super.getKeys(keys);
	}

	protected int getIndex(String key) {
		if (key != null && key.startsWith(indexPrefix) && key.endsWith(indexSuffix)) {
			return super.getIndex(key.substring(indexPrefix.length(), key.length() - indexSuffix.length()));
		} else {
			return -1;
		}
	}

}

