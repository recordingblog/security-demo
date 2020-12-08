
package com.zhushan.security.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.zhushan.security.core.properties.SecurityConstants;

public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	protected AuthenticationSuccessHandler AuthenticationSuccessHandler;
	
	@Autowired 
	protected AuthenticationFailureHandler AuthenticationFailureHandler;
	
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		// 表单登录方式
		http.formLogin()
			// 登录页面配置
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			// 登录时需要认证的url配置
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(AuthenticationSuccessHandler)
			.failureHandler(AuthenticationFailureHandler);
	}
	
}
