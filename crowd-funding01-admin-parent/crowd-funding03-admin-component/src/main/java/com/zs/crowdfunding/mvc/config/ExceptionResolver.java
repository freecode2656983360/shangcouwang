package com.zs.crowdfunding.mvc.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.zs.crowdfunding.exception.AccessForbidenException;
import com.zs.crowdfunding.exception.LoginAcctAlreadyInUseException;
import com.zs.crowdfunding.exception.LoginAcctAlreadyInUseForUpdateException;
import com.zs.crowdfunding.exception.LoginAcctNullException;
import com.zs.crowdfunding.exception.LoginFailedException;
import com.zs.crowdfunding.util.CrowdUtil;
import com.zs.crowdfunding.util.ResultEntity;

/**
 * @Description 异常处理类
 */

@ControllerAdvice
public class ExceptionResolver {
	Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

	/**
	 * 添加账号为null异常
	 * 
	 * @throws Exception
	 */
	@ExceptionHandler(value = LoginAcctNullException.class)
	public ModelAndView resolverLoginAcctNullException(LoginAcctNullException e, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = "add";
		return common(viewName, e, request, response);
	}

	/**
	 * 更新账号重复异常
	 * 
	 * @throws Exception
	 */

	@ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
	public ModelAndView resolverLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException e,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = "system-error";
		return common(viewName, e, request, response);
	}

	/**
	 * 
	 * 添加账号重复异常
	 * 
	 * @throws Exception
	 */

	@ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
	public ModelAndView resolverLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException e,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = "add";
		return common(viewName, e, request, response);
	}

	/**
	 * 登录异常
	 * 
	 * @throws Exception
	 */
	@ExceptionHandler(value = LoginFailedException.class)
	public ModelAndView resolverLoginFailedException(LoginFailedException e, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = "admin-login";
		return common(viewName, e, request, response);
	}

	/**
	 * 
	 * 拒绝访问异常
	 * 
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(AccessForbidenException.class)
	public ModelAndView resolverAccessForbiddenException(AccessForbidenException e, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("----------------------------------");
		String viewName = "admin-login";
		ModelAndView modelAndView = common(viewName, e, request, response);
		return modelAndView;
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ModelAndView Exception(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("----------------------------------");
		String viewName = "system-error";
		ModelAndView modelAndView = common(viewName, e, request, response);
		return modelAndView;
	}

	/**
	 * 提取可复用部分
	 *
	 * @return
	 */
	public ModelAndView common(String viewName, Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		boolean requestType = CrowdUtil.isAjaxRequest(request);
		if (requestType) {
			ResultEntity<Object> failed = ResultEntity.failed(e.getMessage());
			String json = new Gson().toJson(failed);

			response.getWriter().write(json);
			return null;
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("exception", e);
			modelAndView.setViewName(viewName);
			return modelAndView;
		}
	}

}