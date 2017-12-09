package com.purejadeite.util;

/**
 * 2つの値の関係を評価するユーティリティです
 *
 * @author mitsuhiroseino
 *
 */
public class EvaluationUtils {

	/**
	 * ==
	 */
	public static String EQUAL_TO = "==";

	/**
	 * !=
	 */
	public static String NOT_EQUAL_TO = "!=";

	/**
	 * >
	 */
	public static String GREATER_THAN = ">";

	/**
	 * >=
	 */
	public static String GREATER_THAN_OR_EQUAL_TO = ">=";

	/**
	 * <
	 */
	public static String LESS_THAN = "<";

	/**
	 * <=
	 */
	public static String LESS_THAN_OR_EQUAL_TO = "<=";

	private EvaluationUtils() {
	}

	/**
	 * 値の大小を評価します
	 *
	 * @param a
	 *            値A
	 * @param operator
	 *            比較演算子
	 * @param b
	 *            値B
	 * @return
	 */
	public static <T extends Comparable<T>> boolean evaluate(Object a, String operator, Object b) {
		Evaluator evaluator = Evaluator.getEvaluator(operator);
		if (evaluator != null) {
			return evaluator.evaluate(a, b);
		} else {
			throw new IllegalArgumentException("「" + operator + "」は正しい比較演算子ではありません");
		}
	}

}
