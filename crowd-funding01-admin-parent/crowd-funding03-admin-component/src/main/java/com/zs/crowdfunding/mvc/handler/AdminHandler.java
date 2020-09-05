package com.zs.crowdfunding.mvc.handler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zs.crowdfunding.constant.ConstantUtil;
import com.zs.crowdfunding.entity.Admin;
import com.zs.crowdfunding.service.api.AdminService;

@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService;

	// 提交修改
	@RequestMapping("admin/edit.html")
	public String editAdmin(Admin admin, @RequestParam("id") Integer id, @RequestParam("pageNum") Integer pageNum) {
		admin.setId(id);
		adminService.updateAdminById(admin);

		return "redirect:/admin/get/page.html?keyword=" + admin.getLoginAcct();
	}

	// 去修改页面
	@PreAuthorize("hasRole('火影') and hasAuthority('user:update')")
	@RequestMapping("/admin/to/edit.html")
	public String toeditAdmin(@RequestParam("adminId") Integer id, ModelMap modelMap) {
		Admin admin = adminService.getAdminById(id);
		modelMap.addAttribute("admin", admin);
		return "edit";

	}

	// 添加用户
	@PreAuthorize("hasRole('火影') and hasAuthority('user:add')")
	@RequestMapping("/admin/add.html")
	public String addAdmin(Admin admin) {
		adminService.saveAdmin(admin);
		return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;

	}

	// 删除用户
	@PreAuthorize("hasRole('火影') and hasAuthority('user:delete')")
	@RequestMapping("/admin/delete/{id}/{pageNum}/{keyword}.html")
	public String deleteadminbyid(@PathVariable(value = "id") Integer id,
			@PathVariable(value = "pageNum") Integer pageNum, @PathVariable(value = "keyword") String keyword) {

		adminService.removeAdmin(id);

		return "redirect:/admin/get/page.html?keyword=" + keyword + "&&pageNum=" + pageNum;
	}

	// 按照关键字分页查询
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyWord,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, ModelMap modelMap) {

		PageInfo<Admin> list = adminService.selectAdminByKeyWord(keyWord, pageNum, pageSize);
		modelMap.addAttribute(ConstantUtil.ATTR_NAME_PAGEINFO, list);
		return "admin-page";
	}

	// 提交登录
	@RequestMapping("/admin/do/login.html")
	public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd,
			HttpSession session) {
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
		session.setAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN, admin);
		return "redirect:/admin-main.html";
	}

	// 退出登录
	@RequestMapping("/security/do/sss.html")
	public String doLogout(HttpSession httpSession) {
		System.out.println("---------");
		return "redirect:/login.html";

	}
}
