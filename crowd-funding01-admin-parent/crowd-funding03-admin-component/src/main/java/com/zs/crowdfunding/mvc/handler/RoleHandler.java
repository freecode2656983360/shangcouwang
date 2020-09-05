package com.zs.crowdfunding.mvc.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zs.crowdfunding.entity.Role;
import com.zs.crowdfunding.service.api.RoleService;
import com.zs.crowdfunding.util.ResultEntity;

@Controller
public class RoleHandler {

	@Autowired
	private RoleService roleService;

	@PreAuthorize("hasRole('火影') and hasAuthority('role:get')")
	@RequestMapping("/role/get/page/info.json")
	@ResponseBody
	public ResultEntity<PageInfo<Role>> getPageInfo(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

		return ResultEntity.successWithDate(pageInfo);

	}

	@PreAuthorize("hasRole('火影') and hasAuthority('role:add')")
	@RequestMapping("/role/add.json")
	@ResponseBody
	public ResultEntity<String> addRole(Role role) {
		roleService.savaRole(role);
		ResultEntity<String> successWithoutDate = ResultEntity.successWithoutDate();
		return successWithoutDate;

	}
	@PreAuthorize("hasRole('火影') and hasAuthority('role:update')")
	@RequestMapping("/role/update.json")
	@ResponseBody
	public ResultEntity<String> updateRole(Role role) {
		roleService.updateRole(role);
		ResultEntity<String> successWithoutDate = ResultEntity.successWithoutDate();
		return successWithoutDate;

	}
	@PreAuthorize("hasRole('火影') and hasAuthority('role:delete')")
	@RequestMapping("role/delete.json")
	@ResponseBody
	public ResultEntity<Integer> removeRole(@RequestBody List<Integer> roleIdArray) {
		System.out.println(roleIdArray);
		roleService.removeRole(roleIdArray);
		return ResultEntity.successWithoutDate();
	}
}
