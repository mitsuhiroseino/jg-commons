package com.purejadeite.util;

/**
 * 値検証の例外
 * @author mitsuhiroseino
 */
public class ValidationException extends RuntimeException {

	/**
	 * コンストラクタ
	 */
	public ValidationException() {
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param cause 原因
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param cause 原因
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param cause 原因
	 * @param enableSuppression 抑制を有効化するか、それとも無効化するか
	 * @param writableStackTrace スタックトレースを書き込み可能にするかどうか
	 */
	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
