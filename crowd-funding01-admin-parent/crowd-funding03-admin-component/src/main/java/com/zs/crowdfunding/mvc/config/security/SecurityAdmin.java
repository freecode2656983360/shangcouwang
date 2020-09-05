package com.zs.crowdfunding.mvc.config.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.zs.crowdfunding.entity.Admin;

public class SecurityAdmin extends User {

	private Admin admin;

	private static final long serialVersionUID = 1L;

	// 调用父类构造
	public SecurityAdmin(Admin admin, List<GrantedAuthority> GrantedAuthority) {
		super(admin.getLoginAcct(), admin.getUserPswd(), GrantedAuthority);
		this.admin = admin;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
