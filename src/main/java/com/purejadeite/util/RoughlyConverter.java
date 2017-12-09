package com.purejadeite.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Objectで渡された値を本来の型に変換するユーティリティ
 *
 * @author mitsuhiroseino
 *
 */
public class RoughlyConverter {

	/**
	 * <pre>
	 * Object型で渡された値の型がMapの場合、型をMapへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static <K, V> Map<K, V> intoMap(Object value) {
		return intoMap(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がMapの場合、型をMapへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> intoMap(Object value, Map<K, V> dflt) {
		if (value instanceof Map<?, ?>) {
			try {
				return (Map<K, V>) value;
			} catch (ClassCastException e) {
				return dflt;
			}
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がListの場合、型をListへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static <E> List<E> intoList(Object value) {
		return intoList(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がListの場合、型をListへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> intoList(Object value, List<E> dflt) {
		if (value instanceof List<?>) {
			try {
				return (List<E>) value;
			} catch (ClassCastException e) {
				return dflt;
			}
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がCollectionの場合、型をCollectionへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static <E> Collection<E> intoCollection(Object value) {
		return intoCollection(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がCollectionの場合、型をCollectionへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	@SuppressWarnings("unchecked")
	public static <E> Collection<E> intoCollection(Object value, Collection<E> dflt) {
		if (value instanceof Collection<?>) {
			try {
				return (Collection<E>) value;
			} catch (ClassCastException e) {
				return dflt;
			}
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がIterableの場合、型をIterableへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static <E> Iterable<E> intoIterable(Object value) {
		return intoIterable(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がIterableの場合、型をIterableへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> intoIterable(Object value, Iterable<E> dflt) {
		if (value instanceof Iterable<?>) {
			try {
				return (Iterable<E>) value;
			} catch (ClassCastException e) {
				return dflt;
			}
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がArrayの場合、型をArrayへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static <E> E[] intoArray(Object value) {
		return intoArray(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がArrayの場合、型をArrayへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] intoArray(Object value, E[] dflt) {
		if (value instanceof Object[]) {
			try {
				return (E[]) value;
			} catch (ClassCastException e) {
				return dflt;
			}
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がCharSequenceの場合、型をStringへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static String intoString(Object value) {
		return intoString(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がCharSequenceの場合、型をStringへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static String intoString(Object value, String dflt) {
		if (value instanceof CharSequence) {
			return value.toString();
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がBooleanの場合、型をBooleanへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Boolean intoBoolean(Object value) {
		return intoBoolean(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がBooleanの場合、型をBooleanへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Boolean intoBoolean(Object value, Boolean dflt) {
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		return dflt;
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が"true"の場合、Boolean.TRUEへ変換します。
	 * 上記以外の型の場合は、Boolean.FALSEへ変換します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Boolean intoBoolean(CharSequence value) {
		return Boolean.valueOf(value.toString());
	}

	/**
	 * <pre>
	 * Number型で渡された値が0の場合、Boolean.FALSEへ変換します。
	 * 上記以外の型の場合は、Boolean.TRUEへ変換します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Boolean intoBoolean(Number value) {
		boolean val = value.intValue() == 0 ? false : true;
		return Boolean.valueOf(val);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がbooleanの場合、型をbooleanへ変換します。
	 * 上記以外の型の場合はfalseを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static boolean intoBooleanValue(Object value) {
		return intoBooleanValue(value, false);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がbooleanの場合、型をbooleanへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static boolean intoBooleanValue(Object value, boolean dflt) {
		return intoBoolean(value, Boolean.valueOf(dflt)).booleanValue();
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がNumberの場合、型をNumberへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Number intoNumber(Object value) {
		return intoNumber(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がNumberの場合、型をNumberへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Number intoNumber(Object value, Number dflt) {
		if (value instanceof Number) {
			return (Number) value;
		}
		return dflt;
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が数値を表わしている場合、型をNumberへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Number intoNumber(CharSequence value) {
		return intoNumber(value, null);
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が数値を表わしている場合、型をNumberへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Number intoNumber(CharSequence value, Number dflt) {
		try {
			return NumberFormat.getInstance().parse(value.toString());
		} catch (ParseException e) {
			return dflt;
		}
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がByteの場合、
	 * またはByte型の範囲に収まる数値の場合に、型をByteへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Byte intoByte(Object value) {
		return intoByte(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がByteの場合、
	 * またはByte型の範囲に収まる数値の場合に、型をByteへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Byte intoByte(Object value, Byte dflt) {
		Number v = intoNumber(value);
		if (v == null) {
			return dflt;
		} else if (v instanceof Byte) {
			return (Byte) v;
		}
		byte val = v.byteValue();
		if (v.doubleValue() == (double) val) {
			return Byte.valueOf(val);
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がByteの場合、
	 * またはByte型の範囲に収まる数値の場合に、型をbyteへ変換します。
	 * 上記以外の型の場合は0を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static byte intoByteValue(Object value) {
		return intoByteValue(value, (byte) 0);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がByteの場合、
	 * またはByte型の範囲に収まる数値の場合に、型をbyteへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static byte intoByteValue(Object value, byte dflt) {
		return intoByte(value, Byte.valueOf(dflt)).byteValue();
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がIntegerの場合、
	 * またはInteger型の範囲に収まる数値の場合に、型をIntegerへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Integer intoInteger(Object value) {
		return intoInteger(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がIntegerの場合、
	 * またはInteger型の範囲に収まる数値の場合に、型をIntegerへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Integer intoInteger(Object value, Integer dflt) {
		Number v = intoNumber(value);
		if (v == null) {
			return dflt;
		} else if (v instanceof Integer) {
			return (Integer) v;
		}
		int val = v.intValue();
		if (v.doubleValue() == (double) val) {
			return Integer.valueOf(val);
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がIntegerの場合、
	 * またはInteger型の範囲に収まる数値の場合に、型をintへ変換します。
	 * 上記以外の型の場合は0を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static int intoIntValue(Object value) {
		return intoIntValue(value, 0);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がIntegerの場合、
	 * またはInteger型の範囲に収まる数値の場合に、型をintへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static int intoIntValue(Object value, int dflt) {
		return intoInteger(value, Integer.valueOf(dflt)).intValue();
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がShortの場合、
	 * またはShort型の範囲に収まる数値の場合に、型をShortへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Short intoShort(Object value) {
		return intoShort(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がShortの場合、
	 * またはShort型の範囲に収まる数値の場合に、型をShortへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Short intoShort(Object value, Short dflt) {
		Number v = intoNumber(value);
		if (v == null) {
			return dflt;
		} else if (v instanceof Short) {
			return (Short) v;
		}
		short val = v.shortValue();
		if (v.doubleValue() == (double) val) {
			return Short.valueOf(val);
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がShortの場合、
	 * またはShort型の範囲に収まる数値の場合に、型をshortへ変換します。
	 * 上記以外の型の場合は0を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static short intoShortValue(Object value) {
		return intoShortValue(value, (short) 0);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がShortの場合、
	 * またはShort型の範囲に収まる数値の場合に、型をshortへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static short intoShortValue(Object value, short dflt) {
		return intoShort(value, Short.valueOf(dflt)).shortValue();
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がLongの場合、
	 * またはLong型の範囲に収まる数値の場合に、型をLongへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Long intoLong(Object value) {
		return intoLong(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がLongの場合、
	 * またはLong型の範囲に収まる数値の場合に、型をLongへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Long intoLong(Object value, Long dflt) {
		Number v = intoNumber(value);
		if (v == null) {
			return dflt;
		} else if (v instanceof Long) {
			return (Long) v;
		}
		long val = v.longValue();
		if (v.doubleValue() == (double) val) {
			return Long.valueOf(val);
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がLongの場合、
	 * またはLong型の範囲に収まる数値の場合に、型をlongへ変換します。
	 * 上記以外の型の場合は0を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static long intoLongValue(Object value) {
		return intoLongValue(value, 0L);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がLongの場合、
	 * またはLong型の範囲に収まる数値の場合に、型をlongへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static long intoLongValue(Object value, long dflt) {
		return intoLong(value,Long.valueOf(dflt)).longValue();
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がFloatの場合、
	 * またはFloat型の範囲に収まる数値の場合に、型をFloatへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Float intoFloat(Object value) {
		return intoFloat(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がFloatの場合、
	 * またはFloat型の範囲に収まる数値の場合に、型をFloatへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Float intoFloat(Object value, Float dflt) {
		Number v = intoNumber(value);
		if (v == null) {
			return dflt;
		} else if (v instanceof Float) {
			return (Float) v;
		}
		float val = v.floatValue();
		if (v.doubleValue() == (double) val) {
			return Float.valueOf(val);
		}
		return dflt;
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がFloatの場合、
	 * またはFloat型の範囲に収まる数値の場合に、型をfloatへ変換します。
	 * 上記以外の型の場合は0を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static float intoFloatValue(Object value) {
		return intoFloatValue(value, 0F);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がFloatの場合、
	 * またはFloat型の範囲に収まる数値の場合に、型をfloatへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static float intoFloatValue(Object value, float dflt) {
		return intoFloat(value, Float.valueOf(dflt)).floatValue();
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がDoubleの場合、
	 * またはDouble型の範囲に収まる数値の場合に、型をDoubleへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Double intoDouble(Object value) {
		return intoDouble(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がDoubleの場合、
	 * またはDouble型の範囲に収まる数値の場合に、型をDoubleへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Double intoDouble(Object value, Double dflt) {
		Number v = intoNumber(value);
		if (v == null) {
			return dflt;
		} else if (v instanceof Double) {
			return (Double) v;
		}
		return Double.valueOf(v.doubleValue());
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がDoubleの場合、
	 * またはDouble型の範囲に収まる数値の場合に、型をdoubleへ変換します。
	 * 上記以外の型の場合は0を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static double intoDoubleValue(Object value) {
		return intoDoubleValue(value, 0D);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がDoubleの場合、
	 * またはDouble型の範囲に収まる数値の場合に、型をdoubleへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static double intoDoubleValue(Object value, double dflt) {
		return intoDouble(value, Double.valueOf(dflt)).doubleValue();
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がBigDecimalの場合、型をBigDecimalへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static BigDecimal intoBigDecimal(Object value) {
		return intoBigDecimal(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がBigDecimalの場合、型をBigDecimalへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static BigDecimal intoBigDecimal(Object value, BigDecimal dflt) {
		if (value == null) {
			return dflt;
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		try {
			return new BigDecimal(value.toString());
		} catch (NumberFormatException e) {
			return dflt;
		}
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がBigIntegerの場合、型をBigIntegerへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static BigInteger intoBigInteger(Object value) {
		return intoBigInteger(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がBigIntegerの場合、型をBigIntegerへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static BigInteger intoBigInteger(Object value, BigInteger dflt) {
		if (value == null) {
			return dflt;
		} else if (value instanceof BigInteger) {
			return (BigInteger) value;
		}
		try {
			return new BigInteger(value.toString());
		} catch (NumberFormatException e) {
			return dflt;
		}
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がjava.util.Dateの場合、型をjava.util.Dateへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Date intoDate(Object value) {
		return intoDate(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がjava.util.Dateの場合、型をjava.util.Dateへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Date intoDate(Object value, Date dflt) {
		if (value == null) {
			return dflt;
		} else if (value instanceof Date) {
			return (Date) value;
		}
		return dflt;
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が日付を表わしている場合、型をjava.util.Dateへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Date intoDate(CharSequence value) {
		return intoDate(value, null);
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が日付を表わしている場合、型をjava.util.Dateへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Date intoDate(CharSequence value, Date dflt) {
		try {
			return DateFormat.getInstance().parse(value.toString());
		} catch (ParseException e) {
			return dflt;
		}
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がjava.sql.Dateの場合、型をjava.sql.Dateへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static java.sql.Date intoSqlDate(Object value) {
		return intoSqlDate(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がjava.sql.Dateの場合、型をjava.sql.Dateへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static java.sql.Date intoSqlDate(Object value, java.sql.Date dflt) {
		if (value == null) {
			return dflt;
		} else if (value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		}
		return dflt;
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が日付を表わしている場合、型をjava.sql.Dateへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static java.sql.Date intoSqlDate(CharSequence value) {
		return intoSqlDate(value, null);
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が日付を表わしている場合、型をjava.sql.Dateへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static java.sql.Date intoSqlDate(CharSequence value, java.sql.Date dflt) {
		try {
			return java.sql.Date.valueOf(value.toString());
		} catch (IllegalArgumentException e) {
			return dflt;
		}
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がTimestampの場合、型をTimestampへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Timestamp intoTimestamp(Object value) {
		return intoTimestamp(value, null);
	}

	/**
	 * <pre>
	 * Object型で渡された値の型がTimestampの場合、型をTimestampへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Timestamp intoTimestamp(Object value, Timestamp dflt) {
		if (value == null) {
			return dflt;
		} else if (value instanceof Timestamp) {
			return (Timestamp) value;
		}
		return dflt;
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が日付を表わしている場合、型をTimestampへ変換します。
	 * 上記以外の型の場合はnullを返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @return 型変換後の値
	 */
	public static Timestamp intoTimestamp(CharSequence value) {
		return intoTimestamp(value, null);
	}

	/**
	 * <pre>
	 * CharSequence型で渡された値が日付を表わしている場合、型をTimestampへ変換します。
	 * 上記以外の型の場合はデフォルト値を返します。
	 * </pre>
	 *
	 * @param value
	 *            値
	 * @param dflt
	 *            デフォルト値
	 * @return 型変換後の値
	 */
	public static Timestamp intoTimestamp(CharSequence value, Timestamp dflt) {
		try {
			return Timestamp.valueOf(value.toString());
		} catch (IllegalArgumentException e) {
			return dflt;
		}
	}

}
