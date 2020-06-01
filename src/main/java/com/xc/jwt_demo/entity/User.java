package com.xc.jwt_demo.entity;



import lombok.Data;
@Data
public class User {

	private String userId;
	private String username;
	private String password;
	private String email;
	private String mobile;
	private String status;
	private String createBy;
	private String createTime;
	private String lastUpdateBy;
	private String lastUpdateTime;
	private String department;
	private String realName;
	private String establishVest;
	private String delFlag;
	private String token;

}
