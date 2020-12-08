
package com.zhushan.security.browser;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;
import com.zhushan.security.browser.logout.ZhuShanLogoutSuccessHandler;
import com.zhushan.security.browser.session.ZhuShanExpiredSessionStrategy;
import com.zhushan.security.core.authentication.AbstractChannelSecurityConfig;
import com.zhushan.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zhushan.security.core.authorize.AuthorizeConfigManager;
import com.zhushan.security.core.properties.SecurityConstants;
import com.zhushan.security.core.properties.SecurityProperties;
import com.zhushan.security.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
	
	@Autowired 
	private SecurityProperties securityProperties;
	
	@Autowired 
	private DataSource dataSource;
	
	@Autowired 
	private UserDetailsService userDetailsService;
	
	@Autowired 
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired 
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired // 注入社交登录配置
	private SpringSocialConfigurer socialSecurityConfig;
	
	@Autowired // 退出登录处理器
	private ZhuShanLogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		applyPasswordAuthenticationConfig(http); // 调用配置项
		http.apply(validateCodeSecurityConfig) 	// 添加图片验证码配置项
				.and()
			.apply(smsCodeAuthenticationSecurityConfig) // 添加短信验证码配置项
				.and()
			.apply(socialSecurityConfig) // 添加社交登录相关配置项
				.and()
			.rememberMe()// 记住密码功能
				.tokenRepository(persistentTokenRepository()) // 使用默认实现
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())// 记住密码的失效时间
				.userDetailsService(userDetailsService) // 获取userDetailsService
				.and()
			.sessionManagement() // 集群Session管理
				.invalidSessionUrl(SecurityConstants.DEFAULT_SESSIONINVALID_URL)// session失效后要跳转的地址
				.maximumSessions(1)// 最大的session数量
				.maxSessionsPreventsLogin(false)// 阻止登录行为 达到最大登录数量的时候踢下线或者阻止登录
				.expiredSessionStrategy(new ZhuShanExpiredSessionStrategy()) // 并发登录策略
				.and()
				.and()
			.logout()// 退出配置
				.logoutUrl(securityProperties.getBrowser().getLoginOutUrl()) // 退出配置要请求的url
				/*.logoutSuccessUrl("/logout.html")*/ //退出成功后去的页面
				.logoutSuccessHandler(logoutSuccessHandler) // 退出成功处理器
				.deleteCookies("JSESSIONID")// 清除记住密码的cookie
				.and()
			.csrf().disable(); //关闭跨域
		
		authorizeConfigManager.config(http.authorizeRequests());
		
	}

	
	
	@Bean // 记住密码使用的默认实现，将token存储到cokie中，携带token访问用户信息
	public PersistentTokenRepository persistentTokenRepository() {
		// Security提供的默认实现
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		// 设置数据源
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true); //自动创建表
		return tokenRepository;
	}
}
