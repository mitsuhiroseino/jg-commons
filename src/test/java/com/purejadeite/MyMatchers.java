package com.purejadeite;

import java.util.Iterator;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MyMatchers {

	public static <T extends Map<?, ?>> Matcher<T> hasSameItemsTo(T map) {
		return new MapEquals<T>(map);
	}

	private static class MapEquals<T extends Map<?, ?>> extends TypeSafeDiagnosingMatcher<T> {
		private T expected;

		public MapEquals(T expected) {
			this.expected = expected;
		}

		@Override
		public void describeTo(Description description) {
	        description
            .appendText("a map containing ");
		}

		@Override
		protected boolean matchesSafely(T actual, Description mismatchDescription) {
			return hasSameItemsTo(expected, actual, mismatchDescription, "root");
		}

		private boolean hasSameItemsTo(Object expected, Object actual, Description mismatchDescription, String keys) {
			if (expected == actual) {
				return true;
			} else if (expected == null) {
				return false;
			} else if (actual == null) {
				return false;
			} else if (expected.equals(actual)) {
				return true;
			}
			if (expected instanceof Map && actual instanceof Map) {
				Map<?, ?> exp = (Map<?, ?>) expected;
				Map<?, ?> act = (Map<?, ?>) actual;
				for (Object key : exp.keySet()) {
					Object itemE = exp.get(key);
					Object itemA = act.get(key);
					if (!hasSameItemsTo(itemE, itemA, mismatchDescription, keys + "." + key)) {
						return false;
					}
				}
				if (exp.size() != act.size()) {
					for (Object key : act.keySet()) {
						Object itemE = exp.get(key);
						Object itemA = act.get(key);
						if (!hasSameItemsTo(itemE, itemA, mismatchDescription, keys + "." + key)) {
							return false;
						}
					}
				}
				return true;
			} else if (expected instanceof Iterable && actual instanceof Iterable) {
				Iterable<?> exp = (Iterable<?>) expected;
				Iterable<?> act = (Iterable<?>) actual;
				Iterator<?> iteE = exp.iterator();
				Iterator<?> iteA = act.iterator();
				int index = -1;
				while (iteE.hasNext() && iteA.hasNext()) {
					index++;
					Object itemE = iteE.next();
					Object itemA = iteA.next();
					if (!hasSameItemsTo(itemE, itemA, mismatchDescription, keys + "." + index)) {
						return false;
					}
				}
				if (iteE.hasNext()) {
					return false;
				} else if (iteA.hasNext()) {
					return false;
				}
				return true;
			}
			return false;
		}

	}

}
