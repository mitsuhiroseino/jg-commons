package com.purejadeite.db;

public class DbException extends Exception {

	private static final long serialVersionUID = -5350636677423601595L;

	public DbException() {
		super();
	}

	public DbException(String message) {
		super(message);
	}

	public DbException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbException(Throwable cause) {
		super(cause);
	}

}
