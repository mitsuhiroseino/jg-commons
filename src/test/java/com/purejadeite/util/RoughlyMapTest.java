package com.purejadeite.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.AbstractTest;
import com.purejadeite.TestHelper;
import com.purejadeite.util.collection.RoughlyMap;
import com.purejadeite.util.collection.StringKeyNestedMap;

/**
 * Unit test for simple App.
 */
public class RoughlyMapTest extends AbstractTest {

	private static Logger LOGGER = LoggerFactory.getLogger(RoughlyMapTest.class);

	private static final String DATA_DIR_PATH = "src/test/data";

	private static final File DEFINITIONS_DIR = new File(DATA_DIR_PATH, "definitions");

	private static RoughlyMap<String> TEST_MAP;

	private static String KEY_MAP = "MAP";
	private static Map<String, String> VALUE_MAP = new HashMap<>();

	private static String KEY_LIST = "LIST";
	private static List<String> VALUE_LIST = new ArrayList<>();

	private static String KEY_STRING = "STRING";
	private static String VALUE_STRING = "";

	private static String KEY_BOOLEAN = "BOOLEAN";
	private static Boolean VALUE_BOOLEAN = Boolean.TRUE;

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

	private static String KEY_BIG_DECIMAL = "BIG_DECIMAL";
	private static BigDecimal VALUE_BIG_DECIMAL = BigDecimal.ZERO;

	private static String KEY_BIG_INTEGER = "BIG_INTEGER";
	private static BigInteger VALUE_BIG_INTEGER = BigInteger.ZERO;

	private static String KEY_DATE = "DATE";
	private static Date VALUE_DATE = new Date();

	private static String KEY_SQL_DATE = "SQL_DATE";
	private static java.sql.Date VALUE_SQL_DATE = new java.sql.Date(System.currentTimeMillis());

	private static String KEY_TIMESTAMP = "TIMESTAMP";
	private static Timestamp VALUE_TIMESTAMP = new Timestamp(System.currentTimeMillis());

	{
		VALUE_LIST.add("LIST_VALUE");
		VALUE_MAP.put("MAP_KEY", "MAP_VALUE");

		Map<String, Object> ORG_MAP = new HashMap<>();
		TEST_MAP = new RoughlyMap<>(ORG_MAP);
		TEST_MAP.put(KEY_MAP, VALUE_MAP);
		TEST_MAP.put(KEY_LIST, VALUE_LIST);
		TEST_MAP.put(KEY_STRING, VALUE_STRING);
		TEST_MAP.put(KEY_BOOLEAN, VALUE_BOOLEAN);
		TEST_MAP.put(KEY_BYTE, VALUE_BYTE);
		TEST_MAP.put(KEY_SHORT, VALUE_SHORT);
		TEST_MAP.put(KEY_INTEGER, VALUE_INTEGER);
		TEST_MAP.put(KEY_LONG, VALUE_LONG);
		TEST_MAP.put(KEY_FLOAT, VALUE_FLOAT);
		TEST_MAP.put(KEY_DOUBLE, VALUE_DOUBLE);
		TEST_MAP.put(KEY_BIG_DECIMAL, VALUE_BIG_DECIMAL);
		TEST_MAP.put(KEY_BIG_INTEGER, VALUE_BIG_INTEGER);
		TEST_MAP.put(KEY_DATE, VALUE_DATE);
		TEST_MAP.put(KEY_SQL_DATE, VALUE_SQL_DATE);
		TEST_MAP.put(KEY_TIMESTAMP, VALUE_TIMESTAMP);
	}

//	@Test
//	public void utils() {
//		Map<String, Set<String>> value = TEST_MAP.getMap(KEY_MAP);
//		LOGGER.info(value.toString());
//		Integer integerValue = TEST_MAP.getInteger(KEY_INTEGER);
//		LOGGER.info(integerValue.toString());
//	}

	@Test
	public void nest() throws IOException {
		Map<String, Object> orgMap = TestHelper.toJsonMap(DEFINITIONS_DIR, "join.json");
		LOGGER.info(orgMap.toString());
		Map<String, Object> map = new StringKeyNestedMap(orgMap);
		Object value = map.get("sheets.cells.columns");
		LOGGER.info(value.toString());
	}

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
}
