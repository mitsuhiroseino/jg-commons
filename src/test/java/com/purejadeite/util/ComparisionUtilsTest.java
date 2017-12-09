package com.purejadeite.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.purejadeite.AbstractTest;

/**
 * Unit test for simple App.
 */
public class ComparisionUtilsTest extends AbstractTest {

	private Object a = new Object();
	private Object b = new Object();


	@Test
	public void equalTo() throws IOException {
		assertTrue(EvaluationUtils.evaluate(1, "==", 1));
		assertFalse(EvaluationUtils.evaluate(1, "==", 2));
		assertFalse(EvaluationUtils.evaluate(2, "==", 1));
		assertTrue(EvaluationUtils.evaluate(null, "==", null));
		assertFalse(EvaluationUtils.evaluate(1, "==", null));
		assertFalse(EvaluationUtils.evaluate(null, "==", 1));
		assertFalse(EvaluationUtils.evaluate(a, "==", b));
		assertTrue(EvaluationUtils.evaluate(a, "==", a));
	}

	@Test
	public void notEqualTo() throws IOException {
		assertFalse(EvaluationUtils.evaluate(1, "!=", 1));
		assertTrue(EvaluationUtils.evaluate(1, "!=", 2));
		assertTrue(EvaluationUtils.evaluate(2, "!=", 1));
		assertFalse(EvaluationUtils.evaluate(null, "!=", null));
		assertTrue(EvaluationUtils.evaluate(1, "!=", null));
		assertTrue(EvaluationUtils.evaluate(null, "!=", 1));
		assertTrue(EvaluationUtils.evaluate(a, "!=", b));
		assertFalse(EvaluationUtils.evaluate(a, "!=", a));
	}

	@Test
	public void greaterThan() throws IOException {
		assertFalse(EvaluationUtils.evaluate(1, ">", 1));
		assertFalse(EvaluationUtils.evaluate(1, ">", 2));
		assertTrue(EvaluationUtils.evaluate(2, ">", 1));
		assertFalse(EvaluationUtils.evaluate(null, ">", null));
		assertTrue(EvaluationUtils.evaluate(1, ">", null));
		assertFalse(EvaluationUtils.evaluate(null, ">", 1));
		assertFalse(EvaluationUtils.evaluate(a, ">", b));
		assertFalse(EvaluationUtils.evaluate(a, ">", a));
	}

	@Test
	public void greaterThanOrEqualTo() throws IOException {
		assertTrue(EvaluationUtils.evaluate(1, ">=", 1));
		assertFalse(EvaluationUtils.evaluate(1, ">=", 2));
		assertTrue(EvaluationUtils.evaluate(2, ">=", 1));
		assertTrue(EvaluationUtils.evaluate(null, ">=", null));
		assertTrue(EvaluationUtils.evaluate(1, ">=", null));
		assertFalse(EvaluationUtils.evaluate(null, ">=", 1));
		assertFalse(EvaluationUtils.evaluate(a, ">=", b));
		assertTrue(EvaluationUtils.evaluate(a, ">=", a));
	}

	@Test
	public void lessThan() throws IOException {
		assertFalse(EvaluationUtils.evaluate(1, "<", 1));
		assertTrue(EvaluationUtils.evaluate(1, "<", 2));
		assertFalse(EvaluationUtils.evaluate(2, "<", 1));
		assertFalse(EvaluationUtils.evaluate(null, "<", null));
		assertFalse(EvaluationUtils.evaluate(1, "<", null));
		assertTrue(EvaluationUtils.evaluate(null, "<", 1));
		assertTrue(EvaluationUtils.evaluate(a, "<", b));
		assertFalse(EvaluationUtils.evaluate(a, "<", a));
	}

	@Test
	public void lessThanOrEqualTo() throws IOException {
		assertTrue(EvaluationUtils.evaluate(1, "<=", 1));
		assertTrue(EvaluationUtils.evaluate(1, "<=", 2));
		assertFalse(EvaluationUtils.evaluate(2, "<=", 1));
		assertTrue(EvaluationUtils.evaluate(null, "<=", null));
		assertFalse(EvaluationUtils.evaluate(1, "<=", null));
		assertTrue(EvaluationUtils.evaluate(null, "<=", 1));
		assertTrue(EvaluationUtils.evaluate(a, "<=", b));
		assertTrue(EvaluationUtils.evaluate(a, "<=", a));
	}
}
