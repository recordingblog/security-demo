
package com.zhushan.security.core.social.qq.connet;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.zhushan.security.core.social.qq.api.QQ;
import com.zhushan.security.core.social.qq.api.QQImpl;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
	
	// 应用id
	private String appId;
	
	// 获取认证url
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	
	// 获取token
	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
	
	public QQServiceProvider(String appId, String appSecret) {
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}
	
	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}

}
