package com.purejadeite;


/**
 * トランザクションをサポートしたクラス。
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractTransactional {

	protected static final Object LOCK = new Object();

	private Thread transactionThread;

	// トランザクション関連
	// --------------------------------------------------------------------
	public boolean beginTransaction() {
		synchronized (LOCK) {
			if (transactionThread == null) {
				transactionThread = Thread.currentThread();
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean commit() {
		boolean success = commitImple();
		endTransaction();
		return success;
	}

	protected abstract boolean commitImple();

	public boolean rollback() {
		boolean success = rollbackImple();
		endTransaction();
		return success;
	}

	protected abstract boolean rollbackImple();

	public void endTransaction() {
		transactionThread = null;
	}

}
