package com.purejadeite.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.AbstractTest;

/**
 * Unit test for simple App.
 */
public class RoughlyMapUtilsTest extends AbstractTest {

	private static Logger LOGGER = LoggerFactory.getLogger(RoughlyMapUtilsTest.class);

//	private static Map<String, Object> TEST_DATA;

	private static String KEY_MAP = "MAP";
	private static Map<String, String> VALUE_MAP = new HashMap<>();

	private static String KEY_LIST = "LIST";
	private static List<String> VALUE_LIST = new ArrayList<>();

	private static String KEY_STRING = "STRING";
	private static String VALUE_STRING = "";

//	private static String KEY_BOOLEAN = "BOOLEAN";
//	private static Boolean VALUE_BOOLEAN = Boolean.TRUE;

	private static String KEY_BYTE = "BYTE";
	private static Byte VALUE_BYTE = Byte.MAX_VALUE;

	private static String KEY_SHORT = "SHORT";
	private static Short VALUE_SHORT = Short.MAX_VALUE;

	private static String KEY_INTEGER = "INTEGER";
	private static Integer VALUE_INTEGER = Integer.MAX_VALUE;

	private static String KEY_LONG = "LONG";
	private static Long VALUE_LONG = Long.MAX_VALUE;

	private static String KEY_FLOAT = "FLOAT";
	private static Float VALUE_FLOAT = Float.MAX_VALUE;

	private static String KEY_DOUBLE = "DOUBLE";
	private static Double VALUE_DOUBLE = Double.MAX_VALUE;

//	private static String KEY_BIG_DECIMAL = "BIG_DECIMAL";
//	private static BigDecimal VALUE_BIG_DECIMAL = BigDecimal.ZERO;
//
//	private static String KEY_BIG_INTEGER = "BIG_INTEGER";
//	private static BigInteger VALUE_BIG_INTEGER = BigInteger.ZERO;
//
//	private static String KEY_DATE = "DATE";
//	private static Date VALUE_DATE = new Date();
//
//	private static String KEY_SQL_DATE = "SQL_DATE";
//	private static java.sql.Date VALUE_SQL_DATE = new java.sql.Date(System.currentTimeMillis());
//
//	private static String KEY_TIMESTAMP = "TIMESTAMP";
//	private static Timestamp VALUE_TIMESTAMP = new Timestamp(System.currentTimeMillis());

	{
		VALUE_LIST.add("LIST_VALUE");
		VALUE_MAP.put("MAP_KEY", "MAP_VALUE");

		Map<String, Object> TEST_MAP = new HashMap<>();
		TEST_MAP.put(KEY_MAP, VALUE_MAP);
		TEST_MAP.put(KEY_LIST, VALUE_LIST);
		TEST_MAP.put(KEY_STRING, VALUE_STRING);
		TEST_MAP.put(KEY_BYTE, VALUE_BYTE);
		TEST_MAP.put(KEY_SHORT, VALUE_SHORT);
		TEST_MAP.put(KEY_INTEGER, VALUE_INTEGER);
		TEST_MAP.put(KEY_LONG, VALUE_LONG);
		TEST_MAP.put(KEY_FLOAT, VALUE_FLOAT);
		TEST_MAP.put(KEY_DOUBLE, VALUE_DOUBLE);
		TEST_MAP.put(KEY_STRING, VALUE_STRING);
		TEST_MAP.put(KEY_STRING, VALUE_STRING);
		TEST_MAP.put(KEY_STRING, VALUE_STRING);
		TEST_MAP.put(KEY_STRING, VALUE_STRING);
		TEST_MAP.put(KEY_STRING, VALUE_STRING);
	}

//	@Test
//	public void utils() {
//		Map<String, Set<String>> value = RoughlyMapUtils.getMap(TEST_DATA, KEY_MAP);
//		LOGGER.debug(value.toString());
//	}

//	@Test
//	public void utils() {
//		String[] keys = {"a", "b"};
//		Object obj = keys;
//		if (obj instanceof Object[]) {
//			LOGGER.info("true");
//		} else {
//			LOGGER.info("false");
//		}
//	}

	@Test
	public void utils() {
		List<String> list = new ArrayList<>();
		list.add("A");
		LOGGER.info(list.subList(0, list.size() - 1).toString());
	}
}
