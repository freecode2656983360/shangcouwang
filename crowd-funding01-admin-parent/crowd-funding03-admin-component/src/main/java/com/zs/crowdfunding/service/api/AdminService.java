package com.zs.crowdfunding.service.api;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zs.crowdfunding.entity.Admin;

public interface AdminService {
	List<Admin> findall();

	Admin getAdminByLoginAcct(String loginAcct, String userPswd);

	PageInfo<Admin> selectAdminByKeyWord(String keyWord, Integer pageNum, Integer pageSize);

	void removeAdmin(Integer id);

	void saveAdmin(Admin admin);

	Admin getAdminById(Integer id);

	void updateAdminById(Admin admin);

	void saveRelationship(Integer adminId, List<Integer> roleIdList);

	Admin getAdminByLoginAcct(String username);

}
