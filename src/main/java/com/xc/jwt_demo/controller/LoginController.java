package com.xc.jwt_demo.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xc.jwt_demo.entity.User;
import com.xc.jwt_demo.service.UserService;
import com.xc.jwt_demo.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/sys")
public class LoginController {

    @Resource
    private UserService userService;

	//测试页面
    @RequestMapping("/test")
    public String testThymeleaf(ModelMap model) {
        User user = new User();
        user.setUsername("二叔");
        user.setMobile("911");
        model.addAttribute("user", user);
        return "/viewTest";
    }

	//登录页面
	@RequestMapping("/login")
	public String login(ModelMap model) {
		return "/login";
	}

    @RequestMapping(value = "/doLogin")
    @ResponseBody
    public Integer login(User user) {
        Map<String, Object> resultMap = userService.login(user.getUsername(),user.getPassword());
        Integer code = (Integer) resultMap.get("code");
        return code;
    }

    //使用postman或者restclient进行token测试
    @RequestMapping("/tokenTest")
    public void TokenTest(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String token = request.getHeader("token");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("认证成功 Token是:"+token);
    }
}
