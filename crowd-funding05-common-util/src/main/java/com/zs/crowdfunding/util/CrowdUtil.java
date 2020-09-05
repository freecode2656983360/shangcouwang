package com.zs.crowdfunding.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import com.zs.crowdfunding.constant.ConstantUtil;
import com.zs.crowdfunding.exception.LoginFailedException;

public class CrowdUtil {
	// md5加密
	public static String md5(String source) {

		if (source == null || source.length() == 0) {
			throw new LoginFailedException(ConstantUtil.MESSAGE_STRING_INVALIDATE);
		}

		String algorithm = "md5";
		try {
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			byte[] input = source.getBytes();
			byte[] output = instance.digest(input);
			int signum = 1;
			BigInteger bigInteger = new BigInteger(signum, output);
			int radix = 16;
			String encode = bigInteger.toString(radix).toUpperCase();
			return encode;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断请求是否是ajax请求
	 * 
	 * @param request
	 * @return true:是ajax请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
		String xRequestHeader = request.getHeader("X-Requested-With");
		return ((accept != null && accept.contains("application/json"))
				|| (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest")));
	}
}
