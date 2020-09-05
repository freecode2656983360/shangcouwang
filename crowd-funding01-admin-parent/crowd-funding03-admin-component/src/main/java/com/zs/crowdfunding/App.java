package com.zs.crowdfunding;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class App {
	public static void main(String[] args) {
		BCryptPasswordEncoder cv = new BCryptPasswordEncoder();
		boolean encode = cv.matches("admin", "$2a$10$Nv8Vmyw46S.2vw3RWaPixuVax6eYv8AVQquY5R6lBzCuxShQD1xKi");
		System.out.println(encode);
	}
}