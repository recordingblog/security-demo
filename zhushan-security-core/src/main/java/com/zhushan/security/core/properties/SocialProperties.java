
package com.zhushan.security.core.properties;

/**
 * 社交登录配置
 */
public class SocialProperties {
	
	// 默认拦截的请求
	private String filterProcessesUrl = "/auth";
	
	// QQ配置
	private QQProperties qq = new QQProperties();
	
	// 微信配置
	private WeixinProperties weixin = new WeixinProperties();

	public QQProperties getQq() {
		return qq;
	}

	public void setQq(QQProperties qq) {
		this.qq = qq;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public WeixinProperties getWeixin() {
		return weixin;
	}

	public void setWeixin(WeixinProperties weixin) {
		this.weixin = weixin;
	}
	
}
