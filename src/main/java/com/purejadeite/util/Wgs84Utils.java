package com.purejadeite.util;

/**
 * <pre>
 * WGS84(世界測地系1984)に関するユーティリティを提供します。
 * 参考URL: http://hp.vector.co.jp/authors/VA002244/yacht/geo.htm
 * </pre>
 *
 * @author mitsuhiroseino
 * @version 1.0.0
 */
public class Wgs84Utils {

	/**
	 * WGS84 赤道半径(m)
	 */
	private static final double LONG_RAD = 6378137.000;

	/**
	 * WGS84 極半径(m)
	 */
	private static final double SHORT_RAD = 6356752.314245;

	/**
	 * WGS84 第一離心率の二乗 (LONG_RAD^2 - SHORT_RAD^2) / LONG_RAD^2
	 */
	private static final double E2 = (Math.pow(LONG_RAD, 2) - Math.pow(
			SHORT_RAD, 2)) / (Math.pow(LONG_RAD, 2));

	/**
	 * 緯度1度毎の日本国内における大まかな距離(m)
	 */
	private static final long METER_PER_LAT_JP = 111000;

	/**
	 * 緯度1度毎の日本国内における大まかな距離(m)
	 */
	private static final long METER_PER_LNG_JP = 100000;

	/**
	 * ヒュベニの公式を用いて2点間のおおよその距離を取得します。
	 *
	 * @param lat1
	 *            緯度1
	 * @param lng1
	 *            経度1
	 * @param lat2
	 *            緯度2
	 * @param lng2
	 *            経度2
	 * @return 2点間の距離(m)
	 */
	public static long getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double y1 = Math.toRadians(lat1);
		double y2 = Math.toRadians(lat2);
		double x1 = Math.toRadians(lng1);
		double x2 = Math.toRadians(lng2);
		double dy = y1 - y2;
		double dx = x1 - x2;
		double my = (y1 + y2) / 2;
		double w = Math.sqrt(1 - E2 * Math.pow(my, 2));
		double n = LONG_RAD / w;
		double m = (LONG_RAD * (1 - E2)) / Math.pow(w, 3);
		double distance = Math.sqrt(Math.pow(dy * m, 2)
				+ Math.pow((dx * n * Math.cos(my)), 2));
		return (long) distance;
	}

	/**
	 * 日本国内で指定の緯度から、指定された距離(m)を移動したおおよその緯度を返します。
	 *
	 * @param lat
	 *            緯度
	 * @param meter
	 *            距離(m)。正数は北へ、負数は南へ移動
	 * @return 移動先の緯度
	 */
	public static double moveLat(double lat, long meter) {
		double offset = ((double) meter / (double) METER_PER_LAT_JP);
		double latitude = lat + offset;
		if (latitude < -90) {
			return (latitude + 180) * -1;
		} else if (90 < latitude) {
			return (latitude - 180) * -1;
		} else {
			return latitude;
		}
	}

	/**
	 * 日本国内で指定の経度から、指定された距離(m)を移動したおおよその経度を返します。
	 *
	 * @param lng
	 *            経度
	 * @param meter
	 *            距離(m)。正数は東へ、負数は西へ移動
	 * @return 移動先の経度
	 */
	public static double moveLng(double lng, long meter) {
		double offset = ((double) meter / (double) METER_PER_LNG_JP);
		double longitude = lng + offset;
		if (longitude < -180) {
			return longitude + 360;
		} else if (180 < longitude) {
			return longitude - 360;
		} else {
			return longitude;
		}
	}

	/**
	 * 指定の緯度経度から、指定の方向&距離へ移動した際のおおよその緯度を取得します。
	 *
	 * @param lat
	 *            緯度
	 * @param lng
	 *            経度
	 * @param meter
	 *            移動距離(m)
	 * @param direction
	 *            北を0°とし角度で示した移動方向
	 * @return 移動先の緯度
	 */
	public static double moveLat(double lat, double lng, double direction,
			long meter) {
		double i = calcI(lat, meter, direction);

		double W = Math.sqrt(1 - E2 * Math.pow(Math.sin(i), 2));
		double M = LONG_RAD * (1 - E2) / Math.pow(W, 3);
		double di = meter * Math.cos(direction * Math.PI / 180) / M;

		return lat + di * 180 / Math.PI;
	}

	/**
	 * 指定の緯度経度から、指定の方向&距離へ移動した際のおおよその経度を取得します。
	 *
	 * @param lat
	 *            緯度
	 * @param lng
	 *            経度
	 * @param meter
	 *            移動距離(m)
	 * @param direction
	 *            北を0°とし角度で示した移動方向
	 * @return 移動先の経度
	 */
	public static double moveLng(double lat, double lng, double direction,
			long meter) {
		double i = calcI(lat, meter, direction);

		double W = Math.sqrt(1 - E2 * Math.pow(Math.sin(i), 2));
		double N = LONG_RAD / W;
		double dk = meter * Math.sin(direction * Math.PI / 180)
				/ (N * Math.cos(i));

		return lng + dk * 180 / Math.PI;
	}

	private static double calcI(double lat, long meter, double direction) {
		double WT = Math.sqrt(1 - E2
				* Math.pow(Math.sin(lat * Math.PI / 180), 2)); // '仮のW（第１近似）
		double MT = LONG_RAD * (1 - E2) / Math.pow(WT, 3); // '仮のM（第１近似）
		double diT = meter * Math.cos(direction * Math.PI / 180) / MT; // '仮のdi（第１近似）
		double i = lat * Math.PI / 180 + diT / 2;
		return i;
	}

}
