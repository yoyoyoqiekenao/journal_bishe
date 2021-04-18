package com.example.jorunal_bishe.exceptions;

/**
 * The abnormal custom exception system all the exceptions are transformed into
 * custom
 * 
 * @author
 * 
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException() {
		super();
	}

	public BaseException(String detailMessage) {
		super(detailMessage);
	}

	public BaseException(Throwable throwable) {
		super(throwable);
	}

	public BaseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
