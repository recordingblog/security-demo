package com.zhushan.security.core.properties;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2Properties {
	
	private OAuth2ClientProperties [] clients = {};
	
	// jwt的秘钥,在令牌发出和接收的时候会通过该秘钥进行验签,别人一旦知道,就可以通过该秘钥签发JWT令牌,如果秘钥丢了,就可以随意进入你的系统
	private String jwtSigningKey = "zhushan";

	public OAuth2ClientProperties[] getClients() {
		return clients;
	}

	public void setClients(OAuth2ClientProperties[] clients) {
		this.clients = clients;
	}

	public String getJwtSigningKey() {
		return jwtSigningKey;
	}

	public void setJwtSigningKey(String jwtSigningKey) {
		this.jwtSigningKey = jwtSigningKey;
	}
}
