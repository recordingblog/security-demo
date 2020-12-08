package com.zhushan.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import com.zhushan.security.core.authorize.AuthorizeConfigProvider;

@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider{

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers("/user/regist","/logout.html");
		config.antMatchers("/hello").hasRole("ADMIN");
	}

}
