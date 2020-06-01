package com.xc.jwt_demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenUtil {
	
	
	/**
	 * 生成签名，过期时间可以从配置文件里读取 具体看方法调用处
	 * @param **username**
	 * @param **password**
	 * @return
	 */
	public static String sign(String username, String userId,Long expiretime,String secret) {
	    try {
	        // 设置过期时间
	        Date date = new Date(System.currentTimeMillis() + expiretime);
	        // 私钥和加密算法
	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        // 设置头部信息
	        Map<String, Object> header = new HashMap<>(2);
	        header.put("Type", "Jwt");
	        header.put("alg", "HS256");
	        // 返回token字符串
	        return JWT.create()
	                .withHeader(header)
	                .withClaim("loginName", username)
	                .withClaim("userId", userId)
	                .withExpiresAt(date)
	                .sign(algorithm);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * 检验token是否正确
	 * @param **token**
	 * @return
	 */
	public static boolean verify(String token,String secret){
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        JWTVerifier verifier = JWT.require(algorithm).build();
	        @SuppressWarnings("unused")
			DecodedJWT jwt = verifier.verify(token);
	        return true;
	    } catch (Exception e){
	        return false;
	    }
	}
	
	/**
	 * 从token中获取username信息
	 * @param **token**
	 * @return
	 */
	public static String getUserName(String token){
	    try {
	        DecodedJWT jwt = JWT.decode(token);
	        return jwt.getClaim("loginName").asString();
	    } catch (JWTDecodeException e){
	        e.printStackTrace();
	        return null;
	    }
	}

	/**
	 * token是否过期 
	 * @param token
	 */
	public static Boolean checkExpire(String token) {
		try {
	        DecodedJWT jwt = JWT.decode(token);
	        //获取token过期时间
	        Date expiretime = jwt.getExpiresAt();
	        String etStr = String.valueOf(expiretime.getTime());
	        //获取系统当前时间
	        String nowTime = String.valueOf(System.currentTimeMillis());
	        //如果系统当前时间超过token过期时间返回false
	        if(nowTime.compareTo(etStr)>0){
	        	return false;
	        }
	        return true;
	    } catch (JWTDecodeException e){
	        e.printStackTrace();
	    }
		return true;
	}
		
}

