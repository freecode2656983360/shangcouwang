package com.zs.crowdfunding.service.api;

import java.util.HashMap;
import java.util.List;

import com.zs.crowdfunding.entity.Auth;

public interface AuthService {

	List<Auth> getAllAuth();

	List<Integer> getAssignAuthIdByRoId(Integer roleId);

	void saveRoleAuthRelathInship(HashMap<String, List<Integer>> map);

	List<String> getAssignedAuthNameByAdminId(Integer adminId);
	
}
