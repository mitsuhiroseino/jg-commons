package com.purejadeite;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Test;

import com.purejadeite.util.RoughlyConverter;

public class AnyTest {

//	@Test
	public void test1() {
		Number v = RoughlyConverter.intoByte(12.2);
		System.out.println(v);
		if (v != null) {
			System.out.println(v.getClass().getName());
			System.out.println(v.longValue() == (long) v.byteValue());

		}
	}

//	@Test
	public void test2() {
		List<Integer> list = new ArrayList<>();
		list.add(9);
		list.add(7);
		list.add(17);
		list.add(3);
		list.add(8);
		ListIterator<Integer> ite = list.listIterator(list.lastIndexOf(3));
		while (ite.hasPrevious()) {
			Integer i = ite.previous();
			System.out.println(i);
		}
	}

	@Test
	public void test3() {
		MultiValueMap<String, Integer> map = new MultiValueMap<>();
		map.put("a", 0);
		map.put("a", 1);
		map.put("b", 0);
		map.put("b", 0);
		map.put("c", 2);

		System.out.println(map.get("a"));
		System.out.println(map.get("b"));
		System.out.println(map.get("c"));

	}
}
