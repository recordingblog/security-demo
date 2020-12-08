package com.zhushan.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import com.zhushan.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zhushan.security.core.authorize.AuthorizeConfigManager;
import com.zhushan.security.core.properties.SecurityConstants;
import com.zhushan.security.core.properties.SecurityProperties;
import com.zhushan.security.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
@EnableResourceServer 
/**
 * OAuth2资源服务器配置
 */
public class ZhushanResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Autowired  
	protected AuthenticationSuccessHandler AuthenticationSuccessHandler;
	
	@Autowired 
	protected AuthenticationFailureHandler AuthenticationFailureHandler;
	
	@Autowired 
	private SpringSocialConfigurer socialSecurityConfig;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired 
	private SecurityProperties securityProperties;
	
	@Autowired 
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)// 登录页配置
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)// 密码登录需要请求的url配置
			.successHandler(AuthenticationSuccessHandler)
			.failureHandler(AuthenticationFailureHandler);
		http.apply(validateCodeSecurityConfig) 	
				.and()
			.apply(smsCodeAuthenticationSecurityConfig) 
				.and()
			.apply(socialSecurityConfig)
				.and()
			.csrf().disable(); 
		authorizeConfigManager.config(http.authorizeRequests());
	}
	
}
