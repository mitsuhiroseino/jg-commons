package com.purejadeite.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.xerces.impl.xpath.regex.RegularExpression;

/**
 * 文字列の一致種別
 *
 * @author mitsuhiroseino
 *
 */
public enum SimpleComparison {

	/**
	 * 無条件
	 */
	ANY {
		@Override
		public boolean validate(String source, String value) {
			return true;
		}

		@Override
		public String getSource(String source) {
			return null;
		}

		@Override
		public boolean assess(String source) {
			return "*".equals(source);
		}
	},

	/**
	 * 完全一致
	 */
	EQUALS {

		@Override
		public boolean validate(String source, String value) {
			return source.equals(value);
		}

		@Override
		public String getSource(String source) {
			return source;
		}

		@Override
		public boolean assess(String source) {
			return (!StringUtils.startsWith(source, "*") && !StringUtils.endsWith(source, "*"))
					&& (!StringUtils.startsWith(source, "/") && !StringUtils.endsWith(source, "/"));
		}
	},

	/**
	 * 前方一致
	 */
	START_WITH {

		@Override
		public boolean validate(String source, String value) {
			return StringUtils.startsWith(value, getSource(source));
		}

		@Override
		public String getSource(String source) {
			return StringUtils.substring(source, 0, source.length() - 1);
		}

		@Override
		public boolean assess(String source) {
			return 1 < StringUtils.length(source) && !StringUtils.startsWith(source, "*")
					&& StringUtils.endsWith(source, "*");
		}
	},

	/**
	 * 後方一致
	 */
	END_WITH {

		@Override
		public boolean validate(String source, String value) {
			return StringUtils.endsWith(value, getSource(source));
		}

		@Override
		public String getSource(String source) {
			return StringUtils.substring(source, 1);
		}

		@Override
		public boolean assess(String source) {
			return 1 < StringUtils.length(source) && StringUtils.startsWith(source, "*")
					&& !StringUtils.endsWith(source, "*");
		}
	},

	/**
	 * 部分一致
	 */
	LIKE {

		@Override
		public boolean validate(String source, String value) {
			return StringUtils.contains(value, getSource(source));
		}

		@Override
		public String getSource(String source) {
			return StringUtils.substring(source, 1, source.length() - 1);
		}

		@Override
		public boolean assess(String source) {
			return 1 < StringUtils.length(source) && StringUtils.startsWith(source, "*")
					&& StringUtils.endsWith(source, "*");
		}
	},

	/**
	 * 正規表現
	 */
	REGEX {

		@Override
		public boolean validate(String source, String value) {
			RegularExpression reqex = new RegularExpression(getSource(source));
			return reqex.matches(value);
		}

		@Override
		public String getSource(String source) {
			return StringUtils.substring(source, 1, source.length() - 1);
		}

		@Override
		public boolean assess(String source) {
			return 1 < StringUtils.length(source) && StringUtils.startsWith(source, "/")
					&& StringUtils.endsWith(source, "/");
		}
	};

	abstract public boolean validate(String source, String value);

	abstract public String getSource(String source);

	abstract public boolean assess(String source);

	public static SimpleComparison get(String source) {
		for (SimpleComparison type : SimpleComparison.values()) {
			if (type.assess(source)) {
				return type;
			}
		}
		return null;
	}

	public static boolean compare(String source, String value) {
		SimpleComparison type = get(source);
		if (type == null) {
			return false;
		}
		return type.validate(source, value);
	}

}