
package com.zhushan.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zhushan.security.core.properties.SecurityProperties;


@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
	
	@Bean // 注入加密工具
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
