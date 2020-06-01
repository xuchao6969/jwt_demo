package com.xc.jwt_demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.xc.jwt_demo.util.JwtTokenUtil;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    //从配置文件application.yml进行加载
    @Value("${token_secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
            throws Exception {
        String str = request.getMethod();
        if (str.equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        if (null != token) {
            //校验token是否过期
            boolean result = JwtTokenUtil.verify(token, secret);
            if (result) {
                logger.info("通过拦截器");
                return true;
            }
        }
        logger.info("认证失败");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("Token不存在或者已失效");
        return false;
    }
}
