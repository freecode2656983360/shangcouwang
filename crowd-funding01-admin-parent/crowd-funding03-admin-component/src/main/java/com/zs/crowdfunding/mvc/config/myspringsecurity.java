package com.zs.crowdfunding.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 基于注解配置
public class myspringsecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login.html", "/admin/do/login.html", "/bootstrap/**", "/crowdjs/**", "/css/**",
						"/fonts/**", "/img/**", "/jquery/**", "/layer/**", "/script/**", "/ztree/**", "/index.jsp")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login.html")
				.loginProcessingUrl("/security/do/login.html").defaultSuccessUrl("/admin-main.html")
				.usernameParameter("loginAcct").passwordParameter("userPswd").and().csrf().disable().logout()
				.logoutUrl("/security/do/logout.html").logoutSuccessUrl("/admin-main.html");

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.inMemoryAuthentication().withUser("admin").password("admin").roles(
		 * "USER");
		 */
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

	}

}
