package com.zs.crowdfunding.mvc.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zs.crowdfunding.entity.Admin;
import com.zs.crowdfunding.entity.Role;
import com.zs.crowdfunding.service.api.AdminService;
import com.zs.crowdfunding.service.api.AuthService;
import com.zs.crowdfunding.service.api.RoleService;

@Component
public class SecurityAdminService implements UserDetailsService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleServcie;

	@Autowired
	private AuthService authService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminService.getAdminByLoginAcct(username);

		List<Role> role = roleServcie.getAssignedRole(admin.getId());

		List<String> authName = authService.getAssignedAuthNameByAdminId(admin.getId());

		List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();

		for (Role r : role) {
			String roleName = "ROLE_" + r.getName();
			grantedAuthority.add(new SimpleGrantedAuthority(roleName));
		}
		for (String name : authName) {
			grantedAuthority.add(new SimpleGrantedAuthority(name));
		}

		SecurityAdmin securityAdmin = new SecurityAdmin(admin, grantedAuthority);
		return securityAdmin;
	}
}
