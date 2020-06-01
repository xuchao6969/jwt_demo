package com.xc.jwt_demo.config;


import java.util.ArrayList;
import java.util.List;

import com.xc.jwt_demo.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	private TokenInterceptor tokenInterceptor;

    public InterceptorConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(tokenInterceptor)
				 //拦截所有的请求
				 .addPathPatterns("/**")
				 //放开请求
				 .excludePathPatterns("/sys/test","/sys/login","/sys/doLogin","/js/**");
	}

}
