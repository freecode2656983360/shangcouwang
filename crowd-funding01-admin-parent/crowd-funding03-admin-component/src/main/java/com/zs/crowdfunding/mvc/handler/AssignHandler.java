package com.zs.crowdfunding.mvc.handler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zs.crowdfunding.entity.Auth;
import com.zs.crowdfunding.entity.Role;
import com.zs.crowdfunding.service.api.AdminService;
import com.zs.crowdfunding.service.api.AuthService;
import com.zs.crowdfunding.service.api.RoleService;
import com.zs.crowdfunding.util.ResultEntity;

@Controller
public class AssignHandler {

	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthService authService;

	@ResponseBody
	@RequestMapping("/assign/do/role/assign/auth.json")
	public ResultEntity<String> saveRoleAuthRelathInship(@RequestBody HashMap<String, List<Integer>> map) {
		authService.saveRoleAuthRelathInship(map);
		
		return ResultEntity.successWithoutDate();
	}

	@ResponseBody
	@RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
	public ResultEntity<List<Integer>> getAssignAuthIdByRoId(@RequestParam("roleId") Integer roleId) {
		List<Integer> authIdList = authService.getAssignAuthIdByRoId(roleId);
		return ResultEntity.successWithDate(authIdList);
	}

	@ResponseBody
	@RequestMapping("/assgin/get/all/auth.json")
	public ResultEntity<List<Auth>> getAauthAll() {

		List<Auth> listAuth = authService.getAllAuth();
		return ResultEntity.successWithDate(listAuth);

	}

	@RequestMapping("/assign/do/role/assign.html")
	public String saveRelationship(@RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword,
			@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
		adminService.saveRelationship(adminId, roleIdList);

		return "redirect:/admin/get/page.html?keyword=" + keyword + "&&pageNum=" + pageNum;
	}

	@RequestMapping("/assign/to/assign/role/page.html")
	public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
		// 1.查询已分配角色
		List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
		// 2.查询未分配角色
		List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
		// 3.存入模型（本质上其实是：request.setAttribute("attrName",attrValue);
		modelMap.addAttribute("assignedRoleList", assignedRoleList);
		modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
		return "assign-role";
	}
}
