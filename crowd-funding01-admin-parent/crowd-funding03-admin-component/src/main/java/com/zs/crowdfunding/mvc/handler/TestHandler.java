package com.zs.crowdfunding.mvc.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zs.crowdfunding.entity.Admin;
import com.zs.crowdfunding.service.api.AdminService;

@Controller
public class TestHandler {

	@Autowired
	AdminService adminService;

	@RequestMapping("/test/ssm.html")
	public String Testfindall(ModelMap modelMap) {
		List<Admin> adminlist = adminService.findall();
		modelMap.addAttribute("adminlist", adminlist);
		return "target";

	}

}
