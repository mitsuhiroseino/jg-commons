package com.purejadeite;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

abstract public class AbstractTest {

	protected <T> void eq(T actual, T expected) {
		assertThat(actual ,equalTo(expected));
	}

	protected <T> void eq(String reason, T actual, T expected) {
		assertThat(reason, actual ,equalTo(expected));
	}

}
