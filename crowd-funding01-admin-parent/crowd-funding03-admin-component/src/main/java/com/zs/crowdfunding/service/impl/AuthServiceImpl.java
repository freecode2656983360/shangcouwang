package com.zs.crowdfunding.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zs.crowdfunding.entity.Auth;
import com.zs.crowdfunding.mapper.AuthMapper;
import com.zs.crowdfunding.service.api.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthMapper authMapper;

	@Override
	public List<Auth> getAllAuth() {
		List<Auth> listAuth = authMapper.selectByExample(null);
		return listAuth;
	}

	@Override
	public List<Integer> getAssignAuthIdByRoId(Integer roleId) {
		List<Integer> authIdList = authMapper.selectAssignAuthIdByRoId(roleId);
		return authIdList;
	}

	@Override
	public void saveRoleAuthRelathInship(HashMap<String, List<Integer>> map) {
		authMapper.deleteOldRoleAuthRelathInship(map.get("roleId").get(0));
		if (map.get("authIdArray").size() > 0) {
			authMapper.insertRoleAuthRelathInship(map.get("roleId").get(0), map.get("authIdArray"));
		}
	}

	@Override
	public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
		List<String> authName = authMapper.selectAssignedAuthNameByAdminId(adminId);
		return authName;
	}

}
