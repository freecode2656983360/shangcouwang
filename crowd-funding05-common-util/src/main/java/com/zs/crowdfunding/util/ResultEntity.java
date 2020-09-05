package com.zs.crowdfunding.util;

public class ResultEntity<T> {
	private static final String SUCCESS = "success";
	
	private static final String FAILED = "failed";

	// 请求的结果
	private String result;
	// 处理失败的消息
	private String message;

	// 要返回的数据
	private T data;

	// 请求成功不带数据
	public static <type> ResultEntity<type> successWithoutDate() {

		return new ResultEntity<type>(SUCCESS, null, null);
	}

	// 请求成功带数据
	public static <type> ResultEntity<type> successWithDate(type date) {

		return new ResultEntity<type>(SUCCESS, null, date);
	}

	// 请求失败
	public static <type> ResultEntity<type> failed(String message) {

		return new ResultEntity<type>(FAILED, message, null);
	}

	public ResultEntity(String result, String message, T data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public ResultEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setDate(T data) {
		this.data = data;
	}

	public static String getSuccess() {
		return SUCCESS;
	}

	public static String getFailed() {
		return FAILED;
	}

}
