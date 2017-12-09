package com.purejadeite.db;

import java.io.File;

import com.purejadeite.dir.TransactionalDir;

/**
 * トランザクションをサポートしたファイル入出力クラス。
 *
 * @author mitsuhiroseino
 *
 */
public class DbDir extends TransactionalDir {

	public DbDir(String parentDirPath, String dirName)
			throws DbException {
		super(parentDirPath, dirName);
	}

	public DbDir(File parentDir, String dirName) throws DbException {
		super(parentDir, dirName);
	}

	public DbDir(String rootDirPath) throws DbException {
		super(rootDirPath);
	}

	public DbDir(File rootDir) throws DbException {
		super(rootDir);
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
			commit(tableName);
		}
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
			rollback(tableName);
		}
	}
}
