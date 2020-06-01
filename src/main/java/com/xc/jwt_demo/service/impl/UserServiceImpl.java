package com.xc.jwt_demo.service.impl;


import java.util.HashMap;
import java.util.Map;

import com.xc.jwt_demo.dao.UserDao;
import com.xc.jwt_demo.entity.User;
import com.xc.jwt_demo.service.UserService;
import com.xc.jwt_demo.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	//从配置文件application.yml读取token有效期时间 单位ms
	@Value("${expire_time}")
	private Long expiretime;

	//读取加密的secret
	@Value("${token_secret}")
	private String secret;

	@Autowired
	private UserDao dao;

	@Override
	public Map<String, Object> login(String username,String password) {
		Map<String, Object> map = new HashMap<>();
		User userObj  = dao.getUserByUserName(username);
		//判断用户是否存在
		if(null == userObj){
			map.put("code", 0);//用户不存在
			return map;
		}
		//判断密码是否正确
		if(! password.equals(userObj.getPassword())){
			map.put("code", -1);//密码错误
			return map;
		}
		map.put("code", 1);//用户存在
		String tokenObj = userObj.getToken();
		//用户token不存在就创建token
		if(null == tokenObj || "".equals(tokenObj)){
			String token = JwtTokenUtil.sign(userObj.getUsername(),userObj.getUserId(),expiretime,secret);
			logger.info("-----------创建token-------: "+ token);
			Map<String, Object> paraMap = new HashMap<>();
			paraMap.put("token", token);
			paraMap.put("userId", userObj.getUserId());
			dao.addToken2User(paraMap);
			map.put("token", token);
		}else{
			//取出用户token
			String token = userObj.getToken();
			logger.info("------存在的token-----: "+ token);
			//判断token是否过期    不过期返回，过期     新建token返回并插入数据库
			if(JwtTokenUtil.checkExpire(token)){
				logger.info("-------token存在且有效------: "+ token);
				map.put("token", token);
			}else{
				String tokenStr = JwtTokenUtil.sign(userObj.getUsername(),userObj.getUserId(),expiretime,secret);
				logger.info("------token重新生成-----: "+ tokenStr);
				Map<String, Object> paraMap = new HashMap<>();
				paraMap.put("token", tokenStr);
				paraMap.put("userId", userObj.getUserId());
				dao.addToken2User(paraMap);
				map.put("token", tokenStr);
			}
		}
		map.put("userId", userObj.getUserId());
		map.put("username", userObj.getUsername());
		return map;
	}
	


	
	
}
