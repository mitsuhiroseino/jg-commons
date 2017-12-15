package com.purejadeite;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
}
