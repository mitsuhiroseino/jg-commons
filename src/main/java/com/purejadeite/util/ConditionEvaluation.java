package com.purejadeite.util;

/**
 * 比較条件
 *
 * @author mitsuhiroseino
 *
 */
public class ConditionEvaluation {

	private Object value;

	private Evaluator evaluator;

	public ConditionEvaluation(String operator, Object value) {
		evaluator = Evaluator.getEvaluator(operator);
		this.value = value;
	}

	public boolean evaluate(Object comparisonValue) {
		return evaluator.evaluate(comparisonValue, value);
	}
}
