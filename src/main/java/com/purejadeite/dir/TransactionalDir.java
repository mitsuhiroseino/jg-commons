package com.purejadeite.dir;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.AbstractTransactional;
import com.purejadeite.db.DbException;

/**
 * トランザクションをサポートしたファイル入出力クラス。
 *
 * @author mitsuhiroseino
 *
 */
public class TransactionalDir extends AbstractTransactional {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TransactionalDir.class);

	// ファイル保存用フォルダ
	private Dir dir;

	// 削除対象
	private Map<String, Object> removeFiles = null;

	// 作成・更新対象(ファイル)
	private Map<String, Object> saveFiles = null;

	public TransactionalDir(String parentDirPath, String dirName)
			throws DbException {
		this(new File(parentDirPath, dirName));
	}

	public TransactionalDir(File parentDir, String dirName) throws DbException {
		this(new File(parentDir, dirName));
	}

	public TransactionalDir(String rootDirPath) throws DbException {
		this(new File(rootDirPath));
	}

	public TransactionalDir(File rootDir) throws DbException {
		super();
		// データ保存用のフォルダを生成
		if (!rootDir.exists()) {
			if (rootDir.mkdirs()) {
				LOGGER.info(rootDir.getAbsolutePath() + "を作成しました");
			} else {
				LOGGER.info(rootDir.getAbsolutePath() + "は既に存在します。");
			}
		}
		dir = new Dir(rootDir);
		removeFiles = new HashMap<>();
		saveFiles = new HashMap<>();
	}

	// ファイルの取得(複数)
	// ------------------------------------------------------------------
	public List<File> getFiles(String... path) {
		return dir.getFiles(path);
	}

	public List<File> getFiles(List<String> path) {
		return dir.getFiles(path);
	}

	// ファイルの取得(1件)
	// ------------------------------------------------------------------
	public File getFile(String... path) {
		return dir.getFile(path);
	}

	public File getFile(List<String> path) {
		return dir.getFile(path);
	}

	public File getUnderFile(String... path) {
		return dir.getUnderFile(path);
	}

	public File getUnderFile(List<String> path) {
		return dir.getUnderFile(path);
	}

	// ファイルの削除(複数)
	// ------------------------------------------------------------------
	public List<File> removeFiles(String... path) {
		return removeFiles(Arrays.asList(path));
	}

	public List<File> removeFiles(List<String> path) {
		List<File> files = getFiles(path);
		for (File file : files) {
			put(removeFiles, dir.toKeys(file.getPath()), file);
		}
		return files;
	}

	// ファイルの削除(1件)
	// ------------------------------------------------------------------
	public String removeFile(String... path) {
		return removeFile(Arrays.asList(path));
	}

	public String removeFile(List<String> path) {
		File file = getFile(path);
		if (file == null) {
			return null;
		}
		List<String> keys = dir.toKeys(path);
		put(removeFiles, keys, file);
		return dir.toPathString(keys);
	}

	// フォルダの削除(1件)
	// ------------------------------------------------------------------
	public String removeDir(String... path) {
		return removeFile(path);
	}

	public String removeDir(List<String> path) {
		return removeFile(path);
	}

	// ファイルの作成・更新
	// ------------------------------------------------------------------
	public String saveFile(Object file, String... path) {
		return saveFile(file, Arrays.asList(path));
	}

	public String saveFile(Object file, List<String> path) {
		List<String> keys = dir.toKeys(path);
		put(saveFiles, keys, file);
		return dir.toPathString(keys);

	}

	// トランザクション関連
	// --------------------------------------------------------------------
	protected boolean commitImple() {
		commitRemove(removeFiles);
		commitSave(saveFiles);
		clear();
		return true;
	}

	private void commitRemove(Map<String, Object> map) {
		commitRemove(map, null);
	}

	private void commitRemove(Map<String, Object> map, List<String> targetPath) {
		Object target = get(map, targetPath);
		commitRemove(target, targetPath);
	}

	private void commitRemove(Object value, List<String> path) {
		if (value instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) value;
			for (String key : map.keySet()) {
				List<String> nextPath = new ArrayList<>();
				if (path != null) {
					nextPath.addAll(path);
				}
				nextPath.add(key);
				commitRemove(map.get(key), nextPath);
			}
		} else if (value instanceof File) {
			File file = (File) value;
			if (file.exists()) {
				file.delete();
			}
		}
	}

	private void commitSave(Map<String, Object> map) {
		commitSave(map, null);
	}

	private void commitSave(Map<String, Object> map, List<String> targetPath) {
		Object target = get(map, targetPath);
		commitSave(target, targetPath);
	}

	private void commitSave(Object value, List<String> path) {
		if (value instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) value;
			for (String key : map.keySet()) {
				List<String> nextPath = new ArrayList<>();
				if (path != null) {
					nextPath.addAll(path);
				}
				nextPath.add(key);
				commitSave(map.get(key), nextPath);
			}
		} else if (value instanceof File) {
			dir.saveFile((File) value, path);
		} else if (value instanceof String) {
			dir.saveFile((String) value, path);
		} else if (value instanceof byte[]) {
			dir.saveFile((byte[]) value, path);
		}
	}

	private boolean cancelTask(Object value, List<String> path) {
		Object target = null;
		if (1 < path.size()) {
			target = get(value, path.subList(0, path.size() - 1));
		} else if (path.size() == 1) {
			target = value;
		}
		if (target instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) target;
			String key = path.get(path.size() - 1);
			map.remove(key);
			return true;
		}
		return false;
	}

	// 部分的なコミット
	protected boolean commit(String... target) {
		List<String> path = Arrays.asList(target);
		// コミット
		commitRemove(removeFiles, path);
		commitSave(saveFiles, path);
		// コミットした部分を削除
		cancelTask(removeFiles, path);
		cancelTask(saveFiles, path);
		return true;
	}

	// 部分的なロールバック
	protected boolean rollback(String... target) {
		List<String> path = Arrays.asList(target);
		cancelTask(removeFiles, path);
		cancelTask(saveFiles, path);
		return true;
	}

	protected boolean rollbackImple() {
		clear();
		return true;
	}

	private void clear() {
		removeFiles.clear();
		saveFiles.clear();
	}

	private void put(Map<String, Object> map, List<String> path, Object value) {
		put(map, path, value, 0);
	}

	private void put(Map<String, Object> map, List<String> path, Object value,
			int index) {
		String name = path.get(index);
		if (path.size() - 1 == index) {
			map.put(name, value);
		} else {
			Map<String, Object> next = new HashMap<>();
			map.put(name, next);
			put(next, path, value, index + 1);
		}
	}

	private Object get(Object value, List<String> path) {
		if (value instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) value;
			String key = path.get(0);
			if (path.size() == 1) {
				return map.get(key);
			} else {
				Object next = map.get(key);
				return get(next, path.subList(1, path.size()));
			}
		}
		return null;
	}

}
