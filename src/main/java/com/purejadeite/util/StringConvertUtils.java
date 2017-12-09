package com.purejadeite.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class StringConvertUtils {

	// 変換タイプ
	public static enum Type {
		NUMBER,
		ALPHABET,
		SING,
		SPACE,
		KATAKANA
	}
	private static final Type[] NO_KATAKANA_TYPES = {Type.ALPHABET, Type.NUMBER, Type.SING, Type.SPACE};
	private static final Type[] KATAKANA_TYPES = {Type.KATAKANA};

	// 全角半角変換用map
	private static final Map<Type, Map<String, String>> TO_FULL = new HashMap<>();
	private static final Map<String, String> TO_FULL_KATAKANA = new HashMap<>();
	private static final Map<String, String> TO_FULL_NUMBER = new HashMap<>();
	private static final Map<String, String> TO_FULL_ALPHABET = new HashMap<>();
	private static final Map<String, String> TO_FULL_SING = new HashMap<>();
	private static final Map<String, String> TO_FULL_SPACE = new HashMap<>();
	private static final Map<Type, Map<String, String>> TO_HALF = new HashMap<>();
	private static final Map<String, String> TO_HALF_KATAKANA = new HashMap<>();
	private static final Map<String, String> TO_HALF_NUMBER = new HashMap<>();
	private static final Map<String, String> TO_HALF_ALPHABET = new HashMap<>();
	private static final Map<String, String> TO_HALF_SING = new HashMap<>();
	private static final Map<String, String> TO_HALF_SPACE = new HashMap<>();
	// ひらがな、カタカナ変換用map
	private static final Map<String, String> TO_KATAKANA = new HashMap<>();
	private static final Map<String, String> TO_HIRAGANA = new HashMap<>();

	static {
		// 半角→全角
		TO_FULL.put(Type.ALPHABET, TO_FULL_ALPHABET);
		TO_FULL.put(Type.NUMBER, TO_FULL_NUMBER);
		TO_FULL.put(Type.SING, TO_FULL_SING);
		TO_FULL.put(Type.SPACE, TO_FULL_SPACE);
		TO_FULL.put(Type.KATAKANA, TO_FULL_KATAKANA);
		// 全角→半角
		TO_HALF.put(Type.ALPHABET, TO_HALF_ALPHABET);
		TO_HALF.put(Type.NUMBER, TO_HALF_NUMBER);
		TO_HALF.put(Type.SING, TO_HALF_SING);
		TO_HALF.put(Type.SPACE, TO_HALF_SPACE);
		TO_HALF.put(Type.KATAKANA, TO_HALF_KATAKANA);
		// アルファベット
		TO_FULL_ALPHABET.put("A", "Ａ");
		TO_FULL_ALPHABET.put("a", "ａ");
		TO_FULL_ALPHABET.put("B", "Ｂ");
		TO_FULL_ALPHABET.put("b", "ｂ");
		TO_FULL_ALPHABET.put("C", "Ｃ");
		TO_FULL_ALPHABET.put("c", "ｃ");
		TO_FULL_ALPHABET.put("D", "Ｄ");
		TO_FULL_ALPHABET.put("d", "ｄ");
		TO_FULL_ALPHABET.put("E", "Ｅ");
		TO_FULL_ALPHABET.put("e", "ｅ");
		TO_FULL_ALPHABET.put("F", "Ｆ");
		TO_FULL_ALPHABET.put("f", "ｆ");
		TO_FULL_ALPHABET.put("G", "Ｇ");
		TO_FULL_ALPHABET.put("g", "ｇ");
		TO_FULL_ALPHABET.put("H", "Ｈ");
		TO_FULL_ALPHABET.put("h", "ｈ");
		TO_FULL_ALPHABET.put("I", "Ｉ");
		TO_FULL_ALPHABET.put("i", "ｉ");
		TO_FULL_ALPHABET.put("J", "Ｊ");
		TO_FULL_ALPHABET.put("j", "ｊ");
		TO_FULL_ALPHABET.put("K", "Ｋ");
		TO_FULL_ALPHABET.put("k", "ｋ");
		TO_FULL_ALPHABET.put("L", "Ｌ");
		TO_FULL_ALPHABET.put("l", "ｌ");
		TO_FULL_ALPHABET.put("M", "Ｍ");
		TO_FULL_ALPHABET.put("m", "ｍ");
		TO_FULL_ALPHABET.put("N", "Ｎ");
		TO_FULL_ALPHABET.put("n", "ｎ");
		TO_FULL_ALPHABET.put("O", "Ｏ");
		TO_FULL_ALPHABET.put("o", "ｏ");
		TO_FULL_ALPHABET.put("P", "Ｐ");
		TO_FULL_ALPHABET.put("p", "ｐ");
		TO_FULL_ALPHABET.put("Q", "Ｑ");
		TO_FULL_ALPHABET.put("q", "ｑ");
		TO_FULL_ALPHABET.put("R", "Ｒ");
		TO_FULL_ALPHABET.put("r", "ｒ");
		TO_FULL_ALPHABET.put("S", "Ｓ");
		TO_FULL_ALPHABET.put("s", "ｓ");
		TO_FULL_ALPHABET.put("T", "Ｔ");
		TO_FULL_ALPHABET.put("t", "ｔ");
		TO_FULL_ALPHABET.put("U", "Ｕ");
		TO_FULL_ALPHABET.put("u", "ｕ");
		TO_FULL_ALPHABET.put("V", "Ｖ");
		TO_FULL_ALPHABET.put("v", "ｖ");
		TO_FULL_ALPHABET.put("W", "Ｗ");
		TO_FULL_ALPHABET.put("w", "ｗ");
		TO_FULL_ALPHABET.put("X", "Ｘ");
		TO_FULL_ALPHABET.put("x", "ｘ");
		TO_FULL_ALPHABET.put("Y", "Ｙ");
		TO_FULL_ALPHABET.put("y", "ｙ");
		TO_FULL_ALPHABET.put("Z", "Ｚ");
		TO_FULL_ALPHABET.put("z", "ｚ");
		for (Map.Entry<String, String> entry : TO_FULL_ALPHABET.entrySet()) {
			TO_HALF_ALPHABET.put(entry.getValue(), entry.getKey());
		}

		// 数字
		TO_FULL_NUMBER.put("0", "0");
		TO_FULL_NUMBER.put("1", "1");
		TO_FULL_NUMBER.put("2", "2");
		TO_FULL_NUMBER.put("3", "3");
		TO_FULL_NUMBER.put("4", "4");
		TO_FULL_NUMBER.put("5", "5");
		TO_FULL_NUMBER.put("6", "6");
		TO_FULL_NUMBER.put("7", "7");
		TO_FULL_NUMBER.put("8", "8");
		TO_FULL_NUMBER.put("9", "9");
		for (Map.Entry<String, String> entry : TO_FULL_NUMBER.entrySet()) {
			TO_HALF_NUMBER.put(entry.getValue(), entry.getKey());
		}

		// 記号
		TO_FULL_SING.put("'", "＇");
		TO_FULL_SING.put("-", "－");
		TO_FULL_SING.put("!", "！");
		TO_FULL_SING.put("\"", "＂");
		TO_FULL_SING.put("#", "＃");
		TO_FULL_SING.put("$", "＄");
		TO_FULL_SING.put("%", "％");
		TO_FULL_SING.put("&", "＆");
		TO_FULL_SING.put("(", "（");
		TO_FULL_SING.put(")", "）");
		TO_FULL_SING.put("*", "＊");
		TO_FULL_SING.put("", "，");
		TO_FULL_SING.put("､", "、");
		TO_FULL_SING.put(".", "．");
		TO_FULL_SING.put("｡", "。");
		TO_FULL_SING.put("/", "／");
		TO_FULL_SING.put(":", "：");
		TO_FULL_SING.put(";", "；");
		TO_FULL_SING.put("?", "？");
		TO_FULL_SING.put("@", "＠");
		TO_FULL_SING.put("[", "［");
		TO_FULL_SING.put("\\", "＼");
		TO_FULL_SING.put("]", "］");
		TO_FULL_SING.put("^", "＾");
		TO_FULL_SING.put("_", "＿");
		TO_FULL_SING.put("`", "｀");
		TO_FULL_SING.put("{", "｛");
		TO_FULL_SING.put("|", "｜");
		TO_FULL_SING.put("}", "｝");
		TO_FULL_SING.put("~", "～");
		TO_FULL_SING.put("¦", "￤");
		TO_FULL_SING.put("¯", "￣");
		TO_FULL_SING.put("¢", "￠");
		TO_FULL_SING.put("£", "￡");
		TO_FULL_SING.put("¥", "￥");
		TO_FULL_SING.put("｢", "「");
		TO_FULL_SING.put("｣", "」");
		TO_FULL_SING.put("₩", "￦");
		TO_FULL_SING.put("+", "＋");
		TO_FULL_SING.put("<", "＜");
		TO_FULL_SING.put("=", "＝");
		TO_FULL_SING.put(">", "＞");
		TO_FULL_SING.put("￨", "│");
		for (Map.Entry<String, String> entry : TO_FULL_SING.entrySet()) {
			TO_HALF_SING.put(entry.getValue(), entry.getKey());
		}

		// スペース
		TO_FULL_SPACE.put(" ", "　");
		for (Map.Entry<String, String> entry : TO_FULL_SPACE.entrySet()) {
			TO_HALF_SPACE.put(entry.getValue(), entry.getKey());
		}

		// カタカナ
		TO_FULL_KATAKANA.put("･", "・");
		TO_FULL_KATAKANA.put("ｧ", "ァ");
		TO_FULL_KATAKANA.put("ｱ", "ア");
		TO_FULL_KATAKANA.put("ｨ", "ィ");
		TO_FULL_KATAKANA.put("ｲ", "イ");
		TO_FULL_KATAKANA.put("ｩ", "ゥ");
		TO_FULL_KATAKANA.put("ｳ", "ウ");
		TO_FULL_KATAKANA.put("ｳﾞ", "ヴ");
		TO_FULL_KATAKANA.put("ｪ", "ェ");
		TO_FULL_KATAKANA.put("ｴ", "エ");
		TO_FULL_KATAKANA.put("ｫ", "ォ");
		TO_FULL_KATAKANA.put("ｵ", "オ");
		TO_FULL_KATAKANA.put("ｶ", "カ");
		TO_FULL_KATAKANA.put("ｶﾞ", "ガ");
		TO_FULL_KATAKANA.put("ｷ", "キ");
		TO_FULL_KATAKANA.put("ｷﾞ", "ギ");
		TO_FULL_KATAKANA.put("ｸ", "ク");
		TO_FULL_KATAKANA.put("ｸﾞ", "グ");
		TO_FULL_KATAKANA.put("ｹ", "ケ");
		TO_FULL_KATAKANA.put("ｹﾞ", "ゲ");
		TO_FULL_KATAKANA.put("ｺ", "コ");
		TO_FULL_KATAKANA.put("ｺﾞ", "ゴ");
		TO_FULL_KATAKANA.put("ｻ", "サ");
		TO_FULL_KATAKANA.put("ｻﾞ", "ザ");
		TO_FULL_KATAKANA.put("ｼ", "シ");
		TO_FULL_KATAKANA.put("ｼﾞ", "ジ");
		TO_FULL_KATAKANA.put("ｽ", "ス");
		TO_FULL_KATAKANA.put("ｽﾞ", "ズ");
		TO_FULL_KATAKANA.put("ｾ", "セ");
		TO_FULL_KATAKANA.put("ｾﾞ", "ゼ");
		TO_FULL_KATAKANA.put("ｿ", "ソ");
		TO_FULL_KATAKANA.put("ｿﾞ", "ゾ");
		TO_FULL_KATAKANA.put("ﾀ", "タ");
		TO_FULL_KATAKANA.put("ﾀﾞ", "ダ");
		TO_FULL_KATAKANA.put("ﾁ", "チ");
		TO_FULL_KATAKANA.put("ﾁﾞ", "ヂ");
		TO_FULL_KATAKANA.put("ｯ", "ッ");
		TO_FULL_KATAKANA.put("ﾂ", "ツ");
		TO_FULL_KATAKANA.put("ﾂﾞ", "ヅ");
		TO_FULL_KATAKANA.put("ﾃ", "テ");
		TO_FULL_KATAKANA.put("ﾃﾞ", "デ");
		TO_FULL_KATAKANA.put("ﾄ", "ト");
		TO_FULL_KATAKANA.put("ﾄﾞ", "ド");
		TO_FULL_KATAKANA.put("ﾅ", "ナ");
		TO_FULL_KATAKANA.put("ﾆ", "ニ");
		TO_FULL_KATAKANA.put("ﾇ", "ヌ");
		TO_FULL_KATAKANA.put("ﾈ", "ネ");
		TO_FULL_KATAKANA.put("ﾉ", "ノ");
		TO_FULL_KATAKANA.put("ﾊ", "ハ");
		TO_FULL_KATAKANA.put("ﾊﾞ", "バ");
		TO_FULL_KATAKANA.put("ﾊﾟ", "パ");
		TO_FULL_KATAKANA.put("ﾋ", "ヒ");
		TO_FULL_KATAKANA.put("ﾋﾞ", "ビ");
		TO_FULL_KATAKANA.put("ﾋﾟ", "ピ");
		TO_FULL_KATAKANA.put("ﾌ", "フ");
		TO_FULL_KATAKANA.put("ﾌﾞ", "ブ");
		TO_FULL_KATAKANA.put("ﾌﾟ", "プ");
		TO_FULL_KATAKANA.put("ﾍ", "ヘ");
		TO_FULL_KATAKANA.put("ﾍﾞ", "ベ");
		TO_FULL_KATAKANA.put("ﾍﾟ", "ペ");
		TO_FULL_KATAKANA.put("ﾎ", "ホ");
		TO_FULL_KATAKANA.put("ﾎﾞ", "ボ");
		TO_FULL_KATAKANA.put("ﾎﾟ", "ポ");
		TO_FULL_KATAKANA.put("ﾏ", "マ");
		TO_FULL_KATAKANA.put("ﾐ", "ミ");
		TO_FULL_KATAKANA.put("ﾑ", "ム");
		TO_FULL_KATAKANA.put("ﾒ", "メ");
		TO_FULL_KATAKANA.put("ﾓ", "モ");
		TO_FULL_KATAKANA.put("ｬ", "ャ");
		TO_FULL_KATAKANA.put("ﾔ", "ヤ");
		TO_FULL_KATAKANA.put("ｭ", "ュ");
		TO_FULL_KATAKANA.put("ﾕ", "ユ");
		TO_FULL_KATAKANA.put("ｮ", "ョ");
		TO_FULL_KATAKANA.put("ﾖ", "ヨ");
		TO_FULL_KATAKANA.put("ﾗ", "ラ");
		TO_FULL_KATAKANA.put("ﾘ", "リ");
		TO_FULL_KATAKANA.put("ﾙ", "ル");
		TO_FULL_KATAKANA.put("ﾚ", "レ");
		TO_FULL_KATAKANA.put("ﾛ", "ロ");
		TO_FULL_KATAKANA.put("ﾜ", "ワ");
		TO_FULL_KATAKANA.put("ｦ", "ヲ");
		TO_FULL_KATAKANA.put("ﾝ", "ン");
		TO_FULL_KATAKANA.put("ｰ", "ー");
		for (Map.Entry<String, String> entry : TO_FULL_KATAKANA.entrySet()) {
			TO_HALF_KATAKANA.put(entry.getValue(), entry.getKey());
		}

		// ひらがな＆カタカナ
		TO_KATAKANA.put("ぁ", "ァ");
		TO_KATAKANA.put("あ", "ア");
		TO_KATAKANA.put("ぃ", "ィ");
		TO_KATAKANA.put("い", "イ");
		TO_KATAKANA.put("ぅ", "ゥ");
		TO_KATAKANA.put("う", "ウ");
		TO_KATAKANA.put("ゔ", "ヴ");
		TO_KATAKANA.put("ぇ", "ェ");
		TO_KATAKANA.put("え", "エ");
		TO_KATAKANA.put("ぉ", "ォ");
		TO_KATAKANA.put("お", "オ");
		TO_KATAKANA.put("か", "カ");
		TO_KATAKANA.put("が", "ガ");
		TO_KATAKANA.put("き", "キ");
		TO_KATAKANA.put("ぎ", "ギ");
		TO_KATAKANA.put("く", "ク");
		TO_KATAKANA.put("ぐ", "グ");
		TO_KATAKANA.put("け", "ケ");
		TO_KATAKANA.put("げ", "ゲ");
		TO_KATAKANA.put("こ", "コ");
		TO_KATAKANA.put("ご", "ゴ");
		TO_KATAKANA.put("さ", "サ");
		TO_KATAKANA.put("ざ", "ザ");
		TO_KATAKANA.put("し", "シ");
		TO_KATAKANA.put("じ", "ジ");
		TO_KATAKANA.put("す", "ス");
		TO_KATAKANA.put("ず", "ズ");
		TO_KATAKANA.put("せ", "セ");
		TO_KATAKANA.put("ぜ", "ゼ");
		TO_KATAKANA.put("そ", "ソ");
		TO_KATAKANA.put("ぞ", "ゾ");
		TO_KATAKANA.put("た", "タ");
		TO_KATAKANA.put("だ", "ダ");
		TO_KATAKANA.put("ち", "チ");
		TO_KATAKANA.put("ぢ", "ヂ");
		TO_KATAKANA.put("っ", "ッ");
		TO_KATAKANA.put("つ", "ツ");
		TO_KATAKANA.put("づ", "ヅ");
		TO_KATAKANA.put("て", "テ");
		TO_KATAKANA.put("で", "デ");
		TO_KATAKANA.put("と", "ト");
		TO_KATAKANA.put("ど", "ド");
		TO_KATAKANA.put("な", "ナ");
		TO_KATAKANA.put("に", "ニ");
		TO_KATAKANA.put("ぬ", "ヌ");
		TO_KATAKANA.put("ね", "ネ");
		TO_KATAKANA.put("の", "ノ");
		TO_KATAKANA.put("は", "ハ");
		TO_KATAKANA.put("ば", "バ");
		TO_KATAKANA.put("ぱ", "パ");
		TO_KATAKANA.put("ひ", "ヒ");
		TO_KATAKANA.put("び", "ビ");
		TO_KATAKANA.put("ぴ", "ピ");
		TO_KATAKANA.put("ふ", "フ");
		TO_KATAKANA.put("ぷ", "ブ");
		TO_KATAKANA.put("ぶ", "プ");
		TO_KATAKANA.put("へ", "ヘ");
		TO_KATAKANA.put("べ", "ベ");
		TO_KATAKANA.put("ぺ", "ペ");
		TO_KATAKANA.put("ほ", "ホ");
		TO_KATAKANA.put("ぼ", "ボ");
		TO_KATAKANA.put("ぽ", "ポ");
		TO_KATAKANA.put("ま", "マ");
		TO_KATAKANA.put("み", "ミ");
		TO_KATAKANA.put("む", "ム");
		TO_KATAKANA.put("め", "メ");
		TO_KATAKANA.put("も", "モ");
		TO_KATAKANA.put("ゃ", "ャ");
		TO_KATAKANA.put("や", "ヤ");
		TO_KATAKANA.put("ュ", "ュ");
		TO_KATAKANA.put("ゆ", "ユ");
		TO_KATAKANA.put("ょ", "ョ");
		TO_KATAKANA.put("よ", "ヨ");
		TO_KATAKANA.put("ら", "ラ");
		TO_KATAKANA.put("り", "リ");
		TO_KATAKANA.put("る", "ル");
		TO_KATAKANA.put("れ", "レ");
		TO_KATAKANA.put("ろ", "ロ");
		TO_KATAKANA.put("わ", "ワ");
		TO_KATAKANA.put("ゐ", "ヰ");
		TO_KATAKANA.put("ゑ", "ヱ");
		TO_KATAKANA.put("を", "ヲ");
		TO_KATAKANA.put("ん", "ン");
		TO_KATAKANA.put("ゝ", "ヽ");
		TO_KATAKANA.put("ゞ", "ヾ");
		for (Map.Entry<String, String> entry : TO_KATAKANA.entrySet()) {
			TO_HIRAGANA.put(entry.getValue(), entry.getKey());
		}

	}

	public static String toHiragana(String str) {
		return to(TO_HIRAGANA, str);
	}

	public static String toKatakana(String str) {
		return to(TO_KATAKANA, str);
	}

	public static String toFull(String str, Type... types) {
		if (types.length == 0) {
			types = NO_KATAKANA_TYPES;
		}
		return to(getMap(TO_FULL, types), str);
	}

	public static String toFullKatakana(String str) {
		return to(getMap(TO_FULL, KATAKANA_TYPES), str);
	}

	public static String toHalf(String str, Type... types) {
		if (types.length == 0) {
			types = NO_KATAKANA_TYPES;
		}
		return to(getMap(TO_HALF, types), str);
	}

	public static String toHalfKatakana(String str) {
		return to(getMap(TO_HALF, KATAKANA_TYPES), str);
	}

	public static String to(Map<String, String> map, String str) {
		List<String> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.length() - o1.length();
			}
		});
		String result = str;
		for (String key : keys) {
			result = StringUtils.replace(result, key, map.get(key));
		}
		return result;
	}

	private static Map<String, String> getMap(Map<Type, Map<String, String>> maps, Type... types) {
		Map<String, String> map = new HashMap<>();
		if (types.length == 0) {
			for (Map<String, String> value : maps.values()) {
				map.putAll(value);
			}
		} else {
			for (Type type : types) {
				map.putAll(maps.get(type));
			}
		}
		return map;
	}
}
