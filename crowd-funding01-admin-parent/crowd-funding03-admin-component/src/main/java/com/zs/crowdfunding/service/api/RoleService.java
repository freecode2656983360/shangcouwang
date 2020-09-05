package com.zs.crowdfunding.service.api;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zs.crowdfunding.entity.Role;

public interface RoleService {

	PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

	void savaRole(Role role);

	void updateRole(Role role);

	void removeRole(List<Integer> roleIdArray);

	List<Role> getAssignedRole(Integer adminId);

	List<Role> getUnAssignedRole(Integer adminId);
	
}
