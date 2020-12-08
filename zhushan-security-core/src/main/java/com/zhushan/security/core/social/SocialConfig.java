
package com.zhushan.security.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.zhushan.security.core.properties.SecurityProperties;

/**
 * 社交登录配置
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired // 注入数据源
	private DataSource dataSource;

	@Autowired // 加载配置项
	private SecurityProperties securityProperties;
	
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Override //用户连接策略
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		// 使用默认实现
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		// 设置表前缀
		repository.setTablePrefix("zhushan_");
		if(connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}

	@Bean
	public SpringSocialConfigurer zhushanSocialSecurityConfig() {
		//获取回调请求地址
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		ZhuShanSpringSocialConfigurer configurer = new ZhuShanSpringSocialConfigurer(filterProcessesUrl);
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		return configurer;
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}
}
