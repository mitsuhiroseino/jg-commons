package com.purejadeite.util;

/**
 * Guavaに倣いつつも泥臭い方法で変換をする、ケースフォーマット変換クラス
 * @author mitsuhiroseino
 *
 */
public enum CaseFormat {

	/**
	 * Lower Camel
	 */
	LOWER_CAMEL() {

		/**
		 * {@inheritDoc}
		 */
		protected String normalize(String word) {
			return camelToUpperHyphen(word);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			boolean upper = false;
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					upper = true;
				} else if (upper) {
					to.append(Character.toUpperCase(c));
					upper = false;
				}else{
					to.append(Character.toLowerCase(c));
				}
			}
			return to.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (i == 0 && !('a' <= c && c <= 'z')) {
					// 先頭は小文字以外不可
					return false;
				} else if (c == '-' || c == '_') {
					return false;
				}
			}
			return true;
		}
	},

	/**
	 * Upper Camel
	 */
	UPPER_CAMEL() {

		/**
		 * {@inheritDoc}
		 */
		protected String normalize(String word) {
			return camelToUpperHyphen(word);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			boolean upper = false;
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					upper = true;
				} else if (upper || i == 0) {
					to.append(Character.toUpperCase(c));
					upper = false;
				}else{
					to.append(Character.toLowerCase(c));
				}
			}
			return to.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (i == 0 && !('A' <= c && c <= 'Z')) {
					// 先頭は大文字以外不可
					return false;
				} else if (c == '-' || c == '_') {
					return false;
				}
			}
			return true;
		}
	},

	/**
	 * Lower Hyphen
	 */
	LOWER_HYPHEN() {

		/**
		 * {@inheritDoc}
		 */
		protected String normalize(String word) {
			return word.toUpperCase();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String convert(String from) {
			return from.toLowerCase();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('a' <= c && c <= 'z') || ('0' <= c && c <= '9') || c =='-')) {
					// 小文字と数字とハイフン以外不可
					return false;
				}
			}
			return true;
		}
	},

	/**
	 * Upper Hyphen
	 */
	UPPER_HYPHEN() {

		/**
		 * {@inheritDoc}
		 */
		protected String normalize(String word) {
			return word;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String convert(String from) {
			return from;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('A' <= c && c <= 'Z') || ('0' <= c && c <= '9') || c =='-')) {
					// 大文字と数字とハイフン以外不可
					return false;
				}
			}
			return true;
		}
	},

	/**
	 * Lower Underscore
	 */
	LOWER_UNDERSCORE() {

		/**
		 * {@inheritDoc}
		 */
		protected String normalize(String word) {
			return underscoreToUpperHyphen(word);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					to.append('_');
				}else{
					to.append(Character.toLowerCase(c));
				}
			}
			return to.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('a' <= c && c <= 'z') || ('0' <= c && c <= '9') || c =='_')) {
					// 小文字と数字とアンダースコア以外不可
					return false;
				}
			}
			return true;
		}
	},

	/**
	 * Upper Underscore
	 */
	UPPER_UNDERSCORE() {

		/**
		 * {@inheritDoc}
		 */
		protected String normalize(String word) {
			return underscoreToUpperHyphen(word);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					to.append('_');
				}else{
					to.append(c);
				}
			}
			return to.toString();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('A' <= c && c <= 'Z') || ('0' <= c && c <= '9') || c =='_')) {
					// 大文字と数字とアンダースコア以外不可
					return false;
				}
			}
			return true;
		}
	};

	private CaseFormat() {
	}

	/**
	 * アッパーハイフンに変換します
	 * @param word 文字列
	 * @return アッパーハイフン文字列
	 *
	 */
	abstract protected String normalize(String word);

	/**
	 * 目的の形式へ変換します
	 * @param from 変換前の文字列
	 * @return 目的の形式の文字列
	 */
	abstract public String convert(String from);

	/**
	 * 自身の形式であるか判定します
	 * @param word 文字列
	 * @return 自身の形式かどうか
	 */
	abstract public boolean match(String word);

	/**
	 * 文字列を指定の形式に変換します
	 * @param to 指定の形式
	 * @param word 文字列
	 * @return
	 */
	public String to(CaseFormat to, String word) {
		if (word == null || word.isEmpty()) {
			return "";
		}
		return to.convert(normalize(word));
	}

	/**
	 * キャメルケースをアッパーハイフンに変換します
	 * @param word キャメルケースの文字列
	 * @return アッパーハイフンの文字列
	 */
	private static String camelToUpperHyphen(String word) {
		StringBuilder normalized = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if ('A' <= c && c <= 'Z') {
				if (i != 0) {
					normalized.append('-');
				}
			}
			normalized.append(Character.toUpperCase(c));
		}
		return normalized.toString();
	}

	/**
	 * アンダースコアをアッパーハイフンに変換します
	 * @param word アンダースコアの文字列
	 * @return アッパーハイフンの文字列
	 */
	private static String underscoreToUpperHyphen(String word) {
		StringBuilder normalized = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (c == '_') {
				normalized.append('-');
			} else {
				normalized.append(Character.toUpperCase(c));
			}
		}
		return normalized.toString();
	}

}
