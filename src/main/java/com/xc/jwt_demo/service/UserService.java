package com.xc.jwt_demo.service;

import java.util.Map;


public interface UserService {

	Map<String, Object> login(String username, String password);
	
}
