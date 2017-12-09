package com.purejadeite.dir;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.FileIoException;
import com.purejadeite.db.DbException;

// ファイルの入出力を抽象化するクラス
public class Dir {

	private static final Logger LOGGER = LoggerFactory.getLogger(Dir.class);

	// ファイル保存用フォルダ
	protected File rootDir;

	public Dir (String rootDirPath) throws DbException {
		rootDir = new File(rootDirPath);
	}

	public Dir (File rootDir) throws DbException {
		this.rootDir = new File(rootDir.getPath());
	}

	public Dir (File parentDir, String rootDirName) throws DbException {
		rootDir = new File(parentDir, rootDirName);
	}

	// ファイルの保存Base64版 --------------------------------------------------------------
	public File saveFile(String base64, String... path) {
		return saveFile(base64, createFile(path));
	}

	public File saveFile(String base64, List<String> path) {
		return saveFile(base64, createFile(path));
	}

	public File saveFile(String base64, File dir, String fileName) {
		return saveFile(base64, new File(dir, fileName));
	}

	public File saveFile(String base64, File file) {
		return saveFile(Base64.decodeBase64(base64.getBytes()), file);
	}

	// ファイルの保存バイナリ版 --------------------------------------------------------------
	public File saveFile(byte[] binary, String... path) {
		return saveFile(binary, createFile(path));
	}

	public File saveFile(byte[] binary, List<String> path) {
		return saveFile(binary, createFile(path));
	}

	public File saveFile(byte[] binary, File dir, String fileName) {
		return saveFile(binary, new File(dir, fileName));
	}

	public File saveFile(byte[] binary, File file) {
		try {
			FileUtils.writeByteArrayToFile(file, binary);
		} catch (IOException e) {
			LOGGER.error(file.getAbsolutePath() + "を保存できませんでした");
			throw new FileIoException(e);
		}
		return file;
	}

	// ファイルの保存ファイル版 ---------------------------------------------------------------
	public File saveFile(File file, String... path) {
		return saveFile(file, Arrays.asList(path));
	}

	public File saveFile(File file, List<String> path) {
		path = new ArrayList<>(path);
		path.add(file.getName());
		return saveFile(file, createFile(path));
	}

	public File saveFile(File sourceFile, File file) {
		try {
			FileUtils.moveFile(sourceFile, file);
		} catch (IOException e) {
			LOGGER.error(file.getAbsolutePath() + "を保存できませんでした");
			throw new FileIoException(e);
		}
		return file;
	}

	// ファイルの取得(複数) ------------------------------------------------------------------
	public List<File> getFiles(String... path) {
		return getFiles(Arrays.asList(path));
	}

	public List<File> getFiles(List<String> path) {
		File dir = getDir(path);
		return Arrays.asList(dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		}));
	}

	// ファイルの取得(1件) ------------------------------------------------------------------
	public File getFile(String... path) {
		return getFile(Arrays.asList(path));
	}

	public File getFile(List<String> path) {
		File file = new File(toPathString(path));
		if (file.exists()) {
			return file;
		}
		return null;
	}

	public File getUnderFile(String... path) {
		return getUnderFile(Arrays.asList(path));
	}

	public File getUnderFile(List<String> path) {
		List<File> files = getUnderFiles(path);
		if (files.isEmpty()) {
			return null;
		}
		return files.get(0);
	}

	public List<File> getUnderFiles(List<String> path) {
		File dir = getFile(path);
		if (dir != null && dir.exists()) {
			return getUnderFiles(dir);
		}
		return new ArrayList<>();
	}

	private List<File> getUnderFiles(File dir) {
		List<File> files = new ArrayList<>();
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				files.addAll(getUnderFiles(file));
			} else {
				files.add(file);
			}
		}
		return files;
	}

	// ファイルの作成(1件) ------------------------------------------------------------------
	public File createFile(String... path) {
		return createFile(Arrays.asList(path));
	}

	public File createFile(List<String> path) {
		return new File(toPathString(path));
	}

	// ファイルの削除(複数) ------------------------------------------------------------------
	public List<File> removeFiles(String... path) {
		return removeFiles(Arrays.asList(path));
	}

	public List<File> removeFiles(List<String> path) {
		File dir = getDir(path);
		List<File> fiels = Arrays.asList(dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		}));
		for (File file : fiels) {
			file.delete();
		}
		return fiels;
	}

	// ファイルの削除(1件) -------------------------------------------------------------------
	public File removeFile(String... path) {
		return removeFile(Arrays.asList(path));
	}

	public File removeFile(List<String> path) {
		return removeFile(getFile(path));
	}

	public File removeFile(File file) {
		if (file.exists()) {
			if (!file.delete()) {
				LOGGER.error(file.getAbsolutePath() + "を削除できませんでした");
			}
		}
		return file;
	}

	// フォルダの削除(1件) -------------------------------------------------------------------
	public File removeDir(String... path) {
		return removeFile(path);
	}

	public File removeDir(List<String> path) {
		return removeFile(path);
	}

	public File removeDir(File dir) {
		return removeFile(dir);
	}

	// その他 -------------------------------------------------------------
	public File getRootDir() {
		return rootDir;
	}

	public String toPathString(List<String> path) {
		File file = rootDir;
		for (String name : path) {
			file = new File(file, name);
		}
		return file.getPath();
	}

	public File getDir(String... path) {
		return getDir(Arrays.asList(path));
	}

	public File getDir(List<String> path) {
		File dir = rootDir;
		for (String dirName : path) {
			dir = new File(dir, dirName);
		}
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				LOGGER.error(dir.getAbsolutePath() + "を作成できませんでした");
			}
		}
		return dir;
	}

	public List<String> toKeys(String path) {
		return toKeys(Arrays.asList(StringUtils.split(path, File.separator)));
	}

	public List<String> toKeys(List<String> path) {
		// root以下のpathを取得
		List<String> root = Arrays.asList(StringUtils.split(rootDir.getPath(), File.separator));
		List<Integer> removes = new ArrayList<>();
		for (int i = 0; root.size() < 0; i++) {
			if (path.size() <= i) {
				break;
			}
			if (root.get(i).equals(path.get(i))) {
				removes.add(Integer.valueOf(i));
			} else {
				break;
			}
		}
		Collections.reverse(removes);
		for (Integer index : removes) {
			path.remove(index);
		}
		if (!path.isEmpty()) {
			if (".".equals(path.get(0))) {
				path.remove(0);
			}
		}
		return path;
	}

}
