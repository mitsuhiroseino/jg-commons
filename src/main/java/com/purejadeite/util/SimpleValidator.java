package com.purejadeite.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 値のチェック用ユーティリティです
 *
 * @author mitsuhiroseino
 *
 */
public class SimpleValidator {

	/**
	 * コンフィグの必須チェック
	 *
	 * @param config
	 *            コンフィグ
	 * @param persistence
	 *            必須項目名称
	 */
	public static void containsKey(Map<?, ?> map, Object[] keys) {
		List<String> errors = new ArrayList<>();
		for (Object key : keys) {
			if (key instanceof Object[]) {
				// or条件
				Object[] keyArray = (Object[]) key;
				boolean result = false;
				for (Object k : keyArray) {
					if (map.containsKey(k)) {
						result = true;
						break;
					}
				}
				if (!result) {
					errors.add(keyArray[0].toString());
				}
			} else {
				if (!map.containsKey(key)) {
					errors.add(key.toString());
				}
			}
		}
		if (!errors.isEmpty()) {
			String errorKeys = StringUtils.join(errors.toArray(), ",");
			throw new ValidationException(errorKeys + "に値を設定してください。");
		}
	}

}
