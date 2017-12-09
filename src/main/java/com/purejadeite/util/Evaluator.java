package com.purejadeite.util;

/**
 * 2つの値の関係を評価するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public enum Evaluator {

	/**
	 * ==
	 */
	EQUAL_TO(Operator.EQUAL_TO) {
		@Override
		public <T extends Comparable<T>> boolean evaluate(T a, T b) {
			if (a == b) {
				return true;
			} else if (a == null || b == null) {
				return false;
			} else {
				return a.equals(b);
			}
		}
	},

	/**
	 * !=
	 */
	NOT_EQUAL_TO(Operator.NOT_EQUAL_TO) {
		@Override
		public <T extends Comparable<T>> boolean evaluate(T a, T b) {
			return !EQUAL_TO.evaluate(a, b);
		}
	},

	/**
	 * >
	 */
	GREATER_THAN(Operator.GREATER_THAN) {
		@Override
		public <T extends Comparable<T>> boolean evaluate(T a, T b) {
			if (a == b) {
				return false;
			} else if (a == null) {
				return false;
			} else if (b == null) {
				return true;
			} else {
				return a.compareTo(b) > 0;
			}
		}
	},

	/**
	 * >=
	 */
	GREATER_THAN_OR_EQUAL_TO(Operator.GREATER_THAN_OR_EQUAL_TO) {
		@Override
		public <T extends Comparable<T>> boolean evaluate(T a, T b) {
			return EQUAL_TO.evaluate(a, b) || GREATER_THAN.evaluate(a, b);
		}
	},

	/**
	 * <
	 */
	LESS_THAN(Operator.LESS_THAN) {
		@Override
		public <T extends Comparable<T>> boolean evaluate(T a, T b) {
			if (a == b) {
				return false;
			} else if (a == null) {
				return true;
			} else if (b == null) {
				return false;
			} else {
				return a.compareTo(b) < 0;
			}
		}
	},

	/**
	 * <=
	 */
	LESS_THAN_OR_EQUAL_TO(Operator.LESS_THAN_OR_EQUAL_TO) {
		@Override
		public <T extends Comparable<T>> boolean evaluate(T a, T b) {
			return EQUAL_TO.evaluate(a, b) || LESS_THAN.evaluate(a, b);
		}
	};

	/**
	 * 比較演算子文字列
	 */
	private String operator;

	/**
	 * コンストラクタ
	 *
	 * @param operator
	 *            比較演算子文字列
	 */
	private Evaluator(String operator) {
		this.operator = operator;
	}

	/**
	 * 比較演算子文字列を取得する
	 *
	 * @return 比較演算子
	 */
	public String getOperator() {
		return operator;
	}

	abstract public <T extends Comparable<T>> boolean evaluate(T a, T b);

	/**
	 * 値の大小を評価します
	 *
	 * @param a
	 *            値A
	 * @param b
	 *            値B
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Comparable<T>> boolean evaluate(final Object a, final Object b) {
		T ac = null;
		T bc = null;
		if (a == null || a instanceof Comparable) {
			ac = (T) a;
		} else {
			ac = (T) new ComparableWrapper<T>(a, this.getOperator());
		}
		if (b == null || b instanceof Comparable) {
			bc = (T) b;
		} else {
			bc = (T) new ComparableWrapper<T>(b, this.getOperator());
		}
		return evaluate(ac, bc);
	}

	public static Evaluator getEvaluator(String operator) {
		for (Evaluator ope : Evaluator.values()) {
			if (ope.getOperator().equals(operator)) {
				return ope;
			}
		}
		throw new IllegalArgumentException("「" + operator + "」は正しい比較演算子ではありません");
	}

	/**
	 * Comparableではないインスタンスの比較を行うためのラッパークラス
	 * @author mitsuhiroseino
	 *
	 * @param <T>
	 */
	private class ComparableWrapper<T> implements Comparable<T> {

		private Object value;
		private String operator;

		public ComparableWrapper(Object value, String operator) {
			this.value = value;
			this.operator = operator;
		}

		public Object getValue() {
			return value;
		}
		@Override
		public int compareTo(T v) {
			boolean result = equals(v);
			if (Operator.GREATER_THAN.equals(operator)) {
				return result ? -1 : -1;
			} else if (Operator.GREATER_THAN_OR_EQUAL_TO.equals(operator)) {
				return result ? 0 : -1;
			} else if (Operator.LESS_THAN.equals(operator)) {
				return result ? 1 : -1;
			} else if (Operator.LESS_THAN_OR_EQUAL_TO.equals(operator)) {
				return result ? 0 : -1;
			}
			return result ? 0 : 1;
		}
		@Override
		public boolean equals(Object v) {
			if (v instanceof ComparableWrapper) {
				ComparableWrapper<?> val = (ComparableWrapper<?>) v;
				Object valObj = val.getValue();
				if (value == valObj) {
					return true;
				} else if (value == null || valObj == null) {
					return false;
				} else if (value.equals(valObj)) {
					return true;
				}
				return false;
			} else {
				return false;
			}
		}

	}

}
