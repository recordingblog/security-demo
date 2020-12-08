
package com.zhushan.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 服务提供商相关配置
 * appId  secret 封装在SocialProperties中
 */
public class QQProperties extends SocialProperties {
	
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
}
