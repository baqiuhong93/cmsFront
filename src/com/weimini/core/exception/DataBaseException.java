package com.weimini.core.exception;

/**
 * dao层抛出的异常类
 * @作者 qiuhongba
 * @创建日期 2011-8-24
 * @版本 V 1.0
 *
 */
public class DataBaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataBaseException() {
		super();
	}

	public DataBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataBaseException(String message) {
		super(message);
	}

	public DataBaseException(Throwable cause) {
		super(cause);
	}
	
}
