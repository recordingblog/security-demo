package com.zhushan.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import com.zhushan.security.core.properties.SecurityConstants;
import com.zhushan.security.core.properties.SecurityProperties;

@Component
public class ZhuShanAuthorizeConfigProvider implements AuthorizeConfigProvider {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers( // 路径放行
				SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
				SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
				securityProperties.getBrowser().getLoginPage(),
				SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
				securityProperties.getBrowser().getSignUpUrl(),
				SecurityConstants.DEFAULT_LOGIN_PAGE_URL,
				SecurityConstants.DEFAULT_SESSIONINVALID_URL)
			.permitAll();
	}

}
