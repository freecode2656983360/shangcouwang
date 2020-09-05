package com.zs.crowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zs.crowdfunding.entity.Role;
import com.zs.crowdfunding.entity.RoleExample;
import com.zs.crowdfunding.entity.RoleExample.Criteria;
import com.zs.crowdfunding.mapper.RoleMapper;
import com.zs.crowdfunding.service.api.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list = roleMapper.selectRoleByKeyword(keyword);
		return new PageInfo<Role>(list);
	}

	@Override
	public void savaRole(Role role) {
		roleMapper.insert(role);
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public void removeRole(List<Integer> roleIdArray) {
		RoleExample roleExample = new RoleExample();
		Criteria createCriteria = roleExample.createCriteria();
		createCriteria.andIdIn(roleIdArray);
		roleMapper.deleteByExample(roleExample);
	}

	@Override
	public List<Role> getAssignedRole(Integer adminId) {
		// TODO Auto-generated method stub
		return roleMapper.selectAssignedRole(adminId);
	}

	@Override
	public List<Role> getUnAssignedRole(Integer adminId) {
		// TODO Auto-generated method stub
		return roleMapper.selectUnAssignedRole(adminId);
	}

}
