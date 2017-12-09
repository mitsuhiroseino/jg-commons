package com.purejadeite.db;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purejadeite.AbstractTransactional;
import com.purejadeite.FileIoException;

/**
 * JSON形式のファイルへデータを保存する簡易データベースです。
 *
 * @author mitsuhiroseino
 *
 */
public class Db extends AbstractTransactional {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Db.class);
	/**
	 * JSON->Objectマッパー
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// データ保存用フォルダ
	private File rootDir;

	// ファイル保存用フォルダ
	private DbDir dir;

	// スキーマファイル
	private File schemasFile;

	// テーブルファイル
	private Map<String, File> tableFiles = new HashMap<>();

	// テーブル
	private Map<String, List<Map<String, Object>>> tables = new HashMap<>();

	// 最新のID
	private Map<String, Long> ids = new HashMap<>();

	// スキーマ
	private Map<String, Map<String, Object>> schemas = null;

	public Db(String dbDirPath) throws DbException {
		this(dbDirPath, null);
	}

	public Db(String dbDirPath, String schemaFileName) throws DbException {
		super();
		// データ保存用のフォルダを生成
		rootDir = new File(dbDirPath);
		if (!rootDir.exists()) {
			if (rootDir.mkdirs()) {
				LOGGER.info(rootDir.getAbsolutePath() + "を作成しました");
			} else {
				LOGGER.info(rootDir.getAbsolutePath() + "を作成できませんでした");
			}
		}

		// スキーマをロード
		if (schemaFileName == null) {
			schemas = new HashMap<>();
		} else {
			schemasFile = new File(rootDir, schemaFileName);
			if (schemas == null) {
				schemas = loadSchemas(schemasFile);
			}
		}
		// テーブルのファイルを確保
		tableFiles = getTableFiles(rootDir, schemasFile);
		dir = new DbDir(rootDir, "files");
	}

	// 初期化処理用メソッド ------------------------------------------

	// ファイルからスキーマを読み込む
	private Map<String, Map<String, Object>> loadSchemas(File file)
			throws DbException {
		String json;
		try {
			json = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			LOGGER.error(file.getAbsolutePath() + "の読み込みに失敗しました");
			throw new DbException(e);
		}
		if (json == null || json.length() == 0) {
			return new HashMap<>();
		}
		try {
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Object>> schemas = MAPPER.readValue(json,
					Map.class);
			LOGGER.info(file.getAbsolutePath() + "をロードしました。");
			return schemas;
		} catch (IOException e) {
			LOGGER.error(file.getAbsolutePath() + "の形式が不正です");
			throw new DbException(e);
		}
	}

	// テーブルファイルを取得する
	private Map<String, File> getTableFiles(File dir, final File schemaFile) {
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				// ファイルかつスキーマファイルではない事
				return file.isFile() && !file.equals(schemaFile);
			}
		});
		Map<String, File> tableFiles = new HashMap<>();
		for (File file : files) {
			// 拡張子を除くファイル名でマッピング
			String name = stripExtention(file.getName());
			tableFiles.put(name, file);
		}
		return tableFiles;
	}

	// ファイルからテーブルを読み込む
	private List<Map<String, Object>> loadTableFile(File file) {
		String json;
		try {
			json = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			LOGGER.error(file.getAbsolutePath() + "の読み込みに失敗しました");
			throw new FileIoException(e);
		}
		if (json == null || json.length() == 0) {
			return new ArrayList<>();
		}
		try {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> records = MAPPER.readValue(json,
					List.class);
			LOGGER.info(file.getAbsolutePath() + "をロードしました。");
			return records;
		} catch (IOException e) {
			LOGGER.error(file.getAbsolutePath() + "の形式が不正です");
			throw new FileIoException(e);
		}
	}

	// 拡張子を削除する
	private String stripExtention(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index < 0) {
			return fileName;
		}
		return fileName.substring(0, index);
	}

	// JSON形式でファイルへ保存する
	private void saveTableFile(File file, List<Map<String, Object>> table) {
		String json = toJSON(table);
		try {
			FileUtils.writeStringToFile(file, json, "UTF-8", false);
			LOGGER.info(file.getAbsolutePath() + "を保存しました");
		} catch (IOException e) {
			LOGGER.error(file.getAbsolutePath() + "を保存できませんでした");
			throw new FileIoException(e);
		}
	}

	// レコード操作用メソッド ------------------------------------------
	// ■検索

	/**
	 * 全てのレコードを取得します
	 *
	 * @param tableName
	 *            テーブル名
	 * @return レコードのリスト
	 */
	public List<Map<String, Object>> select(String tableName) {
		return select(tableName, new HashMap<String, Object>());
	}

	/**
	 * 条件に合うレコードを取得します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param fieldName
	 *            条件となるフィールドの名称
	 * @param value
	 *            条件となる値
	 * @return 条件に合うレコードのリスト
	 */
	public List<Map<String, Object>> select(String tableName, String fieldName,
			Object value) {
		Map<String, Object> params = new HashMap<>();
		params.put(fieldName, value);
		return select(tableName, params);
	}

	/**
	 * 条件に合うレコードを取得します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param params
	 *            条件
	 * @return 条件に合うレコードのリスト
	 */
	public List<Map<String, Object>> select(String tableName,
			Map<String, Object> params) {
		List<Map<String, Object>> table = getTable(tableName);
		List<Map<String, Object>> records = new ArrayList<>();
		if (table != null && !table.isEmpty()) {
			if (params == null || params.isEmpty()) {
				for (Map<String, Object> record : table) {
					records.add(new HashMap<>(record));
				}
			} else {
				for (Map<String, Object> record : table) {
					if (isMatching(record, params)) {
						records.add(new HashMap<>(record));
					}
				}
			}
		}
		return records;
	}

	/**
	 * 条件に合うレコードを1件取得します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param fieldName
	 *            条件となるフィールドの名称
	 * @param value
	 *            条件となる値
	 * @return 条件に合うレコード
	 */
	public Map<String, Object> selectOne(String tableName, String fieldName,
			Object value) {
		Map<String, Object> params = new HashMap<>();
		params.put(fieldName, value);
		return selectOne(tableName, params);
	}

	/**
	 * 条件に合うレコードを1件取得します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param params
	 *            条件
	 * @return 条件に合うレコード
	 */
	public Map<String, Object> selectOne(String tableName,
			Map<String, Object> params) {
		List<Map<String, Object>> table = getTable(tableName);
		if (table != null && !table.isEmpty()) {
			for (Map<String, Object> record : table) {
				if (isMatching(record, params)) {
					return new HashMap<>(record);
				}
			}
		}
		return null;
	}

	/**
	 * 条件に合うファイルを1件取得します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param id
	 *            ID
	 * @param field フィールド
	 *
	 * @return 条件に合うファイル
	 */
	public File selectFile(String tableName, String id, String field) {
		return dir.getUnderFile(tableName, id, field);
	}

	/**
	 * 条件に合うファイルを1件取得します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param id
	 *            ID
	 * @param field フィールド
	 *
	 * @param index インデックス
	 *
	 * @return 条件に合うファイル
	 */
	public File selectFile(String tableName, String id, String field, String index) {
		return dir.getUnderFile(tableName, id, field, index);
	}

	// ■新規作成

	/**
	 * 複数件のレコードを追加します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param records
	 *            レコード
	 * @return 追加したレコードのリスト
	 * @throws DbException
	 */
	public List<Map<String, Object>> add(String tableName,
			List<Map<String, Object>> records) throws DbException {
		return add(tableName, records, getKeyFields(tableName));
	}

	/**
	 *
	 * 複数件のレコードを追加します キー項目が重複している場合は例外が発生します。
	 *
	 * @param tableName
	 *            テーブル名
	 * @param records
	 *            レコード
	 * @param keyFields
	 *            キー項目名
	 * @return 追加したレコードのリスト
	 * @throws DbException
	 */
	public List<Map<String, Object>> add(String tableName,
			List<Map<String, Object>> records, List<String> keyFields)
			throws DbException {
		// テーブルへ追加
		List<Map<String, Object>> added = new ArrayList<>();
		for (Map<String, Object> record : records) {
			added.add(add(tableName, record, keyFields));
		}
		return added;
	}

	/**
	 * レコードを追加します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param record
	 *            レコード
	 * @return 追加したレコード
	 * @throws DbException
	 */
	public Map<String, Object> add(String tableName, Map<String, Object> record)
			throws DbException {
		return add(tableName, record, getKeyFields(tableName));
	}

	/**
	 * レコードを追加します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param record
	 *            レコード
	 * @param keyFields
	 *            キー項目名
	 * @return 追加したレコード
	 * @throws DbException
	 */
	public Map<String, Object> add(String tableName,
			Map<String, Object> record, List<String> keyFields)
			throws DbException {
		// テーブルへ追加(無い場合は作る)
		// 作成した場合、idのクリアが行われる為、最初にテーブルを取得しておく
		List<Map<String, Object>> table = getTable(tableName);

		// IDの付与
		Object tempId = record.get("id");
		if (tempId == null || !isFormalId(tableName, tempId.toString())) {
			record.put("id", createId(tableName));
		}
		// 事前にレコードが存在しない事を確認
		if (hasRecord(tableName, record, keyFields)) {
			// 対象のレコードが既にある場合は例外
			throw new DbException("追加対象のレコードはキーが重複しています:table=" + tableName
					+ ",record=" + toJSON(record));
		}

		// ファイルのみ先に保存
		Map<String, Object> dataRecord = saveFiles(tableName, record);
		table.add(dataRecord);
		return record;
	}

	// ファイルを保存しそれ以外のフィールドは戻り値として返す
	private Map<String, Object> saveFiles(String tableName, Map<String, Object> record) {
		Map<String, Object> dataRecord = new HashMap<>();
		String id = record.get("id").toString();
		for (String filedName : record.keySet()) {
			Object value = record.get(filedName);
			if (saveFile(tableName, id, filedName, value)) {
				// ファイルのフィールドだった場合は既存のファイルを削除
				dir.removeFile(tableName, id, filedName);

			} else {
				// ファイルのフィールドではなかった場合
				dataRecord.put(filedName, value);
			}
		}
		return dataRecord;
	}

	private boolean saveFile(String tableName, String id, String filedName, Object value) {
		return saveFile(tableName, id, filedName, value, null);
	}

	private boolean saveFile(String tableName, String id, String filedName, Object value, Integer index) {
		if (value instanceof Map) {
			@SuppressWarnings("rawtypes")
			Map file = (Map) value;
			String fileName = file.get("name").toString();
			Object fileData = file.get("file");
			if (index == null) {
				dir.saveFile(fileData, tableName, id, filedName, fileName);
			} else {
				dir.saveFile(fileData, tableName, id, filedName, index.toString(), fileName);
			}
			return true;
		} else if (value instanceof File) {
			// file
			File file = (File) value;
			if (index == null) {
				dir.saveFile(file, tableName, id, file.getName());
			} else {
				dir.saveFile(file, tableName, id, index.toString(), file.getName());
			}
			return true;
		} else if (value instanceof List) {
			// list
			boolean success = false;
			int i = 0;
			@SuppressWarnings("rawtypes")
			List values = (List) value;
			for (Object v : values) {
				if (saveFile(tableName, id, filedName, v, Integer.valueOf(i))) {
					success = true;
				}
				i++;
			}
			return success;
		}
		return false;
	}

	// ■更新

	/**
	 * 複数件のレコードを更新します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param recordsOrParams
	 *            レコードor更新情報
	 * @return 更新したレコードのリスト
	 * @throws DbException
	 */
	public List<Map<String, Object>> update(String tableName,
			List<Map<String, Object>> recordsOrParams) throws DbException {
		return update(tableName, recordsOrParams, getKeyFields(tableName));
	}

	/**
	 * 複数件のレコードを更新します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param recordsOrParams
	 *            レコードor更新情報
	 * @param keyFields
	 *            キー項目名
	 * @return 更新したレコードのリスト
	 * @throws DbException
	 */
	public List<Map<String, Object>> update(String tableName,
			List<Map<String, Object>> recordsOrParams, List<String> keyFields)
			throws DbException {

		List<Map<String, Object>> updated = new ArrayList<>();
		for (Map<String, Object> record : recordsOrParams) {
			updated.add(update(tableName, record, keyFields));
		}
		return updated;
	}

	/**
	 * レコードを更新します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param recordsOrParams
	 *            レコードor更新情報
	 * @return 更新したレコード
	 * @throws DbException
	 */
	public Map<String, Object> update(String tableName,
			Map<String, Object> recordsOrParams) throws DbException {
		return update(tableName, recordsOrParams, getKeyFields(tableName));
	}

	/**
	 * レコードを更新します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param recordsOrParams
	 *            レコードor更新情報
	 * @param keyFields
	 *            キー項目名
	 * @return 更新したレコード
	 * @throws DbException
	 */
	public Map<String, Object> update(String tableName,
			Map<String, Object> recordsOrParams, List<String> keyFields)
			throws DbException {
		// キーの一致するレコードを置き換え
		List<Map<String, Object>> table = getTable(tableName);
		Map<String, Object> keyParams = getParams(recordsOrParams, keyFields);
		if (table != null) {
			for (Map<String, Object> record : table) {
				if (isMatching(record, keyParams)) {
					Map<String, Object> dataRecord = saveFiles(tableName, recordsOrParams);
					record.putAll(dataRecord);
					return record;
				}
			}
		}
		// 対象のレコード無しの場合は例外
		throw new DbException("更新対象のレコードがありません:table=" + tableName + ",keys="
				+ toJSON(keyParams));
	}

	// ■削除

	/**
	 * 複数件のレコードを削除します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param records
	 *            レコード
	 * @return 削除したレコードのリスト
	 * @throws DbException
	 */
	public List<Map<String, Object>> delete(String tableName,
			List<Map<String, Object>> recordsOrParams) throws DbException {
		return delete(tableName, recordsOrParams, getKeyFields(tableName));
	}

	/**
	 * 複数件のレコードを削除します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param records
	 *            レコード
	 * @param keyFields
	 *            キー項目名
	 * @return 削除したレコードのリスト
	 * @throws DbException
	 */
	public List<Map<String, Object>> delete(String tableName,
			List<Map<String, Object>> recordsOrParams, List<String> keyFields)
			throws DbException {

		List<Map<String, Object>> deleted = new ArrayList<>();
		for (Map<String, Object> record : recordsOrParams) {
			deleted.add(deleteOne(tableName, record, keyFields));
		}
		return deleted;
	}

	/**
	 * 条件に一致するレコードを全て削除します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param fieldName
	 *            条件となるフィールドの名称
	 * @param value
	 *            条件となる値
	 * @return 削除したレコードのリスト
	 */
	public List<Map<String, Object>> delete(String tableName, String fieldName,
			Object value) {
		Map<String, Object> params = new HashMap<>();
		params.put(fieldName, value);
		return delete(tableName, params);
	}

	/**
	 * 条件に一致するレコードを全て削除します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param params
	 *            条件
	 * @return 削除したレコードのリスト
	 */
	public List<Map<String, Object>> delete(String tableName,
			Map<String, Object> params) {
		// キーの一致するレコードを削除
		List<Map<String, Object>> table = getTable(tableName);
		List<Map<String, Object>> removed = new ArrayList<>();
		if (table != null) {
			for (int i = table.size() - 1; -1 < i; i--) {
				Map<String, Object> record = table.get(i);
				if (isMatching(record, params)) {
					table.remove(i);
					removed.add(record);
				}
			}
		}
		return removed;
	}

	/**
	 * レコードを削除します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param fieldName
	 *            条件となるフィールドの名称
	 * @param value
	 *            条件となる値
	 * @return 削除したレコード
	 * @throws DbException
	 */
	public Map<String, Object> deleteOne(String tableName, String fieldName,
			Object value) throws DbException {
		Map<String, Object> params = new HashMap<>();
		params.put(fieldName, value);
		return deleteOne(tableName, params);
	}

	/**
	 * レコードを削除します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param record
	 *            レコード
	 * @return 削除したレコード
	 * @throws DbException
	 */
	public Map<String, Object> deleteOne(String tableName,
			Map<String, Object> recordOrParams) throws DbException {
		return deleteOne(tableName, recordOrParams, getKeyFields(tableName));
	}

	/**
	 * レコードを削除します
	 *
	 * @param tableName
	 *            テーブル名
	 * @param record
	 *            レコード
	 * @param keyFields
	 *            キー項目名
	 * @return 削除したレコード
	 * @throws DbException
	 */
	public Map<String, Object> deleteOne(String tableName,
			Map<String, Object> recordOrParams, List<String> keyFields)
			throws DbException {
		// キーの一致するレコードを置き換え
		List<Map<String, Object>> table = getTable(tableName);
		Map<String, Object> keyParams = getParams(recordOrParams, keyFields);
		if (table != null) {
			for (Map<String, Object> record : table) {
				if (isMatching(record, keyParams)) {
					// ファイルの削除
					dir.removeDir(tableName, record.get("id").toString());
					// データの削除
					table.remove(record);
					return record;
				}
			}
		}
		return null;
	}

	// カウント
	public List<Map<String, Object>> count(String tableName,
			String... keyFields) {
		return count(tableName, null, keyFields);
	}

	// カウント
	public List<Map<String, Object>> count(String tableName,
			Map<String, Object> params, String... keyFields) {
		List<Map<String, Object>> records = select(tableName, params);
		// 集計
		Map<List<Object>, Integer> results = new HashMap<>();
		for (Map<String, Object> record : records) {
			List<Object> keys = new ArrayList<>();
			for (String keyField : keyFields) {
				keys.add(record.get(keyField));
			}
			Integer count = results.get(keys);
			if (count == null) {
				results.put(keys, Integer.valueOf(1));
			} else {
				results.put(keys, Integer.valueOf(count.intValue() + 1));
			}
		}
		// リストに変換
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (List<Object> keys : results.keySet()) {
			Map<String, Object> result = new HashMap<>();
			for (int i = 0; i < keyFields.length; i++) {
				String keyField = keyFields[i];
				result.put(keyField, keys.get(i).toString());
			}
			result.put("count", results.get(keys));
			resultList.add(result);
		}
		// ソート
		Collections.sort(resultList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Integer count1 = (Integer) o1.get("count");
				Integer count2 = (Integer) o2.get("count");
				return count1.intValue() - count2.intValue();
			}
		});
		return resultList;
	}

	// 合計
	public Map<List<Object>, Integer> sum(String tableName,
			Map<String, Object> params, String valueField, String... keyFields) {
		List<Map<String, Object>> table = getTable(tableName);
		Map<List<Object>, Integer> result = new HashMap<>();
		for (Map<String, Object> record : table) {
			List<Object> keys = new ArrayList<>();
			for (String keyField : keyFields) {
				keys.add(record.get(keyField));
			}
			Integer sum = result.get(keys);
			Integer value = (Integer) record.get(valueField);
			if (sum == null) {
				result.put(keys, Integer.valueOf(value.intValue()));
			} else {
				result.put(keys,
						Integer.valueOf(sum.intValue() + value.intValue()));
			}
		}
		return result;
	}

	// トランザクション用メソッド -----------------------------------------------------
	public boolean beginTransaction() {
		boolean success = super.beginTransaction();
		if (success) {
			success = dir.beginTransaction();
			if (!success) {
				this.endTransaction();
			}
		}
		return success;
	}

	/**
	 * 全テーブルの内容をファイルへ書き出します
	 *
	 * @throws DbException
	 */
	protected boolean commitImple() {
		List<String> tableNames = new ArrayList<>();
		for (String tableName : tables.keySet()) {
			saveTable(tableName);
			tableNames.add(tableName);
		}

		return dir.commit();
	}

	/**
	 * テーブルの内容をファイルへ書き出します
	 *
	 * @param tableNames
	 *            テーブル名
	 * @throws DbException
	 */
	public void commitTable(String... tableNames) {
		for (String tableName : tableNames) {
			saveTable(tableName);
		}
		dir.commitTable(tableNames);
	}

	/**
	 * 全テーブルの内容をファイルから読み込みなおします
	 *
	 * @throws DbException
	 */
	protected boolean rollbackImple() {
		List<String> tableNames = new ArrayList<>();
		for (String tableName : tables.keySet()) {
			loadTable(tableName);
			tableNames.add(tableName);
		}
		return dir.rollback();
	}

	/**
	 * テーブルの内容をファイルから読み込みなおします
	 *
	 * @param tableNames
	 *            テーブル名
	 * @throws DbException
	 */
	public void rollbackTable(String... tableNames) {
		for (String tableName : tableNames) {
			loadTable(tableName);
		}
		dir.rollbackTable(tableNames);
	}

	// ユーティリティメソッド ----------------------------------------------

	// 条件に合うレコードか判定する
	private boolean isMatching(Map<String, Object> record,
			Map<String, Object> params) {
		for (String param : params.keySet()) {
			String paramValue = toStr(params.get(param));
			String value = toStr(record.get(param));
			if ((paramValue != null && !paramValue.equals(value))
					|| (value != null && !value.equals(paramValue))) {
				return false;
			}
		}
		return true;
	}

	private String toStr(Object value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

	// スキーマからキー情報を取得する
	@SuppressWarnings("unchecked")
	private List<String> getKeyFields(String tableName) {
		Map<String, Object> schema = getSchema(tableName);
		List<String> keyFields = new ArrayList<>();
		if (schema != null) {
			Object objKeys = schema.get("keys");
			if (objKeys instanceof List) {
				keyFields.addAll((List<String>) objKeys);
			}
		}
		if (keyFields.isEmpty()) {
			keyFields.add("id");
		}
		return keyFields;
	}

	// スキーマからID情報を取得する
	private String getIdFormat(String tableName) {
		Map<String, Object> schema = getSchema(tableName);
		if (schema != null) {
			Object id = schema.get("id");
			if (id != null) {
				return id.toString();
			}
		}
		return "0";
	}

	// レコードが存在するか判定する
	private boolean hasRecord(String tableName, Map<String, Object> record,
			List<String> keyFields) {
		List<Map<String, Object>> table = getTable(tableName);
		return hasRecord(table, record, keyFields);
	}

	// レコードが存在するか判定する
	private boolean hasRecord(List<Map<String, Object>> table,
			Map<String, Object> record, List<String> keyFields) {
		if (table != null) {
			Map<String, Object> params = getParams(record, keyFields);
			for (Map<String, Object> stored : table) {
				if (isMatching(stored, params)) {
					return true;
				}
			}
		}
		return false;
	}

	// レコードとキー名称から検索パラメーターを作成する
	private Map<String, Object> getParams(Map<String, Object> record,
			List<String> keyFields) {
		Map<String, Object> params = new HashMap<>();
		if (keyFields == null || keyFields.isEmpty()) {
			// keyFieldsの指定が無い場合は全項目がパラメーター
			params.putAll(record);
		} else {
			for (String key : keyFields) {
				params.put(key, record.get(key));
			}
		}
		return params;
	}

	// JSON形式に変換する
	private String toJSON(Object data) {
		try {
			return MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			throw new FileIoException("JSONの形式への変換が失敗しました" + data.toString(), e);
		}
	}

	private boolean isFormalId(String tableName, String id) {
		String format = getIdFormat(tableName);
		NumberFormat formatter = new DecimalFormat(format);
		long no = 0;
		try {
			no = formatter.parse(id).longValue();
		} catch (ParseException e) {
			// パースに失敗した場合はfalse
			return false;
		}
		// サーバーで発行したIDか...
		Long currentNo = getId(tableName);
		if (currentNo == null || currentNo.longValue() < no) {
			// サーバーで発行したIDは、まだその番号に達していない
			return false;
		}
		return true;
	}

	private String createId(String tableName) {
		// 現在のIDに振られた番号から次の番号を算出
		Long currentNo = getId(tableName);
		long no = 1;
		if (currentNo != null) {
			no = currentNo.longValue() + 1;
		}
		// 番号を基にIDを生成
		putId(tableName, Long.valueOf(no));
		String format = getIdFormat(tableName);
		NumberFormat formatter = new DecimalFormat(format);
		return formatter.format(no);

	}

	//
	private Long getCurrentIdNo(String tableName,
			List<Map<String, Object>> table) {
		// 一番大きなIDよりも大きなID
		String format = getIdFormat(tableName);
		NumberFormat formatter = new DecimalFormat(format);
		long no = 0;
		String currentId = getCurrentId(table);
		if (currentId != null) {
			// 既に発行されたIDがある場合
			try {
				no = formatter.parse(currentId).longValue();
			} catch (ParseException e) {
				// パースに失敗した場合はno=0としてあつかう
			}
		}
		return Long.valueOf(no);

	}

	private String getCurrentId(List<Map<String, Object>> records) {
		// 一番大きなIDを探す
		String id = null;
		String nextId = null;
		for (Map<String, Object> record : records) {
			nextId = record.get("id").toString();
			if (id == null || (nextId != null && 0 < nextId.compareTo(id))) {
				id = nextId;
			}
		}
		return id;
	}

	// テーブルを取得する
	private List<Map<String, Object>> getTable(String tableName) {
		List<Map<String, Object>> table = tables.get(getTableName(tableName));
		if (table == null) {
			synchronized (LOCK) {
				if (table == null) {
					table = loadTable(tableName);
				}
			}
		}
		return table;
	}

	private List<Map<String, Object>> loadTable(String tableName) {
		List<Map<String, Object>> table = null;
		File tableFile = getTableFile(tableName);
		if (tableFile != null && tableFile.exists()) {
			// ファイルから読み込み
			table = loadTableFile(tableFile);
		} else {
			// ファイルが無い場合は空のテーブルを作る
			table = new ArrayList<>();
		}
		putTable(tableName, table);
		putId(tableName, getCurrentIdNo(tableName, table));
		return table;
	}

	private void saveTable(String tableName) {
		saveTableFile(getTableFile(tableName), getTable(tableName));
	}

	private List<Map<String, Object>> putTable(String tableName,
			List<Map<String, Object>> table) {
		return tables.put(getTableName(tableName), table);
	}

	private Long getId(String tableName) {
		return ids.get(getTableName(tableName));
	}

	private Long putId(String tableName, Long id) {
		return ids.put(getTableName(tableName), id);
	}

	private File getTableFile(String tableName) {
		tableName = getTableName(tableName);
		File tableFile = tableFiles.get(tableName);
		if (tableFile == null) {
			tableFile = new File(rootDir, tableName + ".json");
			tableFiles.put(tableName, tableFile);
		}
		return tableFile;
	}

	private Map<String, Object> getSchema(String tableName) {
		return schemas.get(getTableName(tableName));
	}

	// テーブル名を取得する
	private String getTableName(String tableName) {
		if (schemas != null) {
			if (!tables.containsKey(tableName)
					&& !schemas.containsKey(tableName)) {
				// 正式な名称ではない場合
				for (String formalTableName : schemas.keySet()) {
					Map<String, Object> schema = schemas.get(formalTableName);
					// 別名がある場合
					Object alt = schema.get("alt");
					if (tableName.equals(alt)) {
						return formalTableName;
					}
				}
			}
		}
		// 正式な名称の場合 or 定義されていない名称の場合
		return tableName;
	}

}
