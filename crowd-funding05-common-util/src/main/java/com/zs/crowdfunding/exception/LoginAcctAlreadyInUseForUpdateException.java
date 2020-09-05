package com.zs.crowdfunding.exception;

public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException{

	/**
	 * 更新账号重复异常
	 */
	private static final long serialVersionUID = 1L;

	public LoginAcctAlreadyInUseForUpdateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
