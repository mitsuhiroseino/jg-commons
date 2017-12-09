package com.purejadeite.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.AbstractTest;
import com.purejadeite.util.collection.NestedMap;

/**
 * Unit test for simple App.
 */
public class NestedMapTest extends AbstractTest {

	private static Logger LOGGER = LoggerFactory.getLogger(NestedMapTest.class);

	@Test
	public void nest() throws IOException {
		NestedMap<String> map = new NestedMap<>(new HashMap<String, Object>(), true);
		List<String> keys1 = new ArrayList<>();
		keys1.add("a1");
		keys1.add("b1");
		map.put(keys1, "1");
		String[] keys2 = {"a2","b2", "c2"};
		map.put(keys2, "2");
		Set<String> keys3 = new HashSet<>();
		keys3.add("a3");
		keys3.add("b3");
		keys3.add("c3");
		keys3.add("d3");
		map.put(keys3, "3");
		String keys4 = null;
		map.put(keys4, "4");
		List<Integer> keys5 = new ArrayList<>();
		keys5.add(5);
		keys5.add(6);
		map.put(keys5, "5");
		List<String> keys6a = new ArrayList<>();
		keys6a.add("a6a");
		keys6a.add("b6a");
		List<String> keys6b = new ArrayList<>();
		keys6b.add("a6b");
		keys6b.add("b6b");
		List<List<String>> keys6 = new ArrayList<>();
		keys6.add(keys6a);
		keys6.add(keys6b);
		map.put(keys6, "6");
		LOGGER.info(map.toString());
		LOGGER.info(map.get(keys6).toString());

	}
}
