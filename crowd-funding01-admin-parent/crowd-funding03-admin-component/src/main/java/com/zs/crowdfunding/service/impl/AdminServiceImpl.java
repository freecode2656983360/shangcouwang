package com.zs.crowdfunding.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zs.crowdfunding.constant.ConstantUtil;
import com.zs.crowdfunding.entity.Admin;
import com.zs.crowdfunding.entity.AdminExample;
import com.zs.crowdfunding.entity.AdminExample.Criteria;
import com.zs.crowdfunding.exception.LoginAcctAlreadyInUseException;
import com.zs.crowdfunding.exception.LoginAcctAlreadyInUseForUpdateException;
import com.zs.crowdfunding.exception.LoginAcctNullException;
import com.zs.crowdfunding.exception.LoginFailedException;
import com.zs.crowdfunding.mapper.AdminMapper;
import com.zs.crowdfunding.service.api.AdminService;
import com.zs.crowdfunding.util.CrowdUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminMapper adminMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<Admin> findall() {
		return adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

		AdminExample adminExample = new AdminExample();
		Criteria createCriteria = adminExample.createCriteria();
		createCriteria.andLoginAcctEqualTo(loginAcct);
		List<Admin> list = adminMapper.selectByExample(adminExample);
		if (list == null || list.size() == 0) {
			throw new LoginFailedException(ConstantUtil.MESSAGE_LOGIN_FAILED);
		}
		if (list.size() > 1) {
			throw new LoginFailedException(ConstantUtil.MESSAGE_SYSTEM_ERROR_NOT_UNIQUE);
		}
		Admin admin = list.get(0);
		if (admin == null) {
			throw new LoginFailedException(ConstantUtil.MESSAGE_LOGIN_FAILED);
		}
		String userPswdDB = admin.getUserPswd();
		String userPswdForm = CrowdUtil.md5(userPswd);
		if (!Objects.equals(userPswdDB, userPswdForm)) {
			throw new LoginFailedException(ConstantUtil.MESSAGE_LOGIN_FAILED);
		}
		return admin;
	}

	@Override
	public PageInfo<Admin> selectAdminByKeyWord(String keyWord, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Admin> list = adminMapper.selectAdminByKeyWord(keyWord);
		return new PageInfo<Admin>(list);
	}

	@Override
	public void removeAdmin(Integer id) {
		adminMapper.deleteByPrimaryKey(id);

	}

	@Override
	public void saveAdmin(Admin admin) {

		if ((admin.getLoginAcct() == null || admin.getUserName() == "")
				|| (admin.getUserPswd() == null || admin.getUserPswd() == "")) {
			throw new LoginAcctNullException("账号或密码不能为空");

		}
		String encode = bCryptPasswordEncoder.encode(admin.getUserPswd());
		admin.setUserPswd(encode);
		admin.setCreateTime(new SimpleDateFormat("yyyy:MM:dd hh:mm:ss").format(new Date()));
		try {
			adminMapper.insertSelective(admin);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseException("账号已存在");

			}
		}

	}

	@Override
	public Admin getAdminById(Integer id) {
		Admin admin = adminMapper.selectByPrimaryKey(id);
		return admin;
	}

	@Override
	public void updateAdminById(Admin admin) {
		try {
			adminMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseForUpdateException("账号已存在");

			}
		}

	}

	@Override
	public void saveRelationship(Integer adminId, List<Integer> roleIdList) {
		adminMapper.deleteRelationship(adminId);
		if (roleIdList != null && roleIdList.size() != 0) {
			adminMapper.insertNewRelationship(adminId, roleIdList);
		}

	}

	@Override
	public Admin getAdminByLoginAcct(String username) {
		AdminExample adminExample = new AdminExample();
		Criteria createCriteria = adminExample.createCriteria();
		createCriteria.andLoginAcctEqualTo(username);
		List<Admin> selectByExample = adminMapper.selectByExample(adminExample);
		return selectByExample.get(0);
	}

}
