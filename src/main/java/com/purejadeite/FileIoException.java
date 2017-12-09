package com.purejadeite;

public class FileIoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileIoException() {
		super();
	}

	public FileIoException(String message) {
		super(message);
	}

	public FileIoException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileIoException(Throwable cause) {
		super(cause);
	}

}
