package com.zs.crowdfunding.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zs.crowdfunding.constant.ConstantUtil;
import com.zs.crowdfunding.entity.Admin;
import com.zs.crowdfunding.exception.AccessForbidenException;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN);

		if (admin == null) {
			// 4. 抛出异常，访问被拒绝异常
			throw new AccessForbidenException(ConstantUtil.MESSAGE_ACCESS_FORBIDEN);
		}
		
		return true;
	}

}
