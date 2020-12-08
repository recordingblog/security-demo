
package com.zhushan.security.core.social.qq.connet;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.zhushan.security.core.social.qq.api.QQ;
/**
 * 链接工厂
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
