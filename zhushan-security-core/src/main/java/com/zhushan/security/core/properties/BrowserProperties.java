
package com.zhushan.security.core.properties;

/**
 * 浏览器相关配置
 */
public class BrowserProperties {
	
	// 默认注册页
	private String signUpUrl = "/zhushan-signUp.html";
	
	// 退出成功后跳转页
	private String signOutUrl;
	
	// 退出登录请求地址
	private String loginOutUrl = "/signOut";
	
	public String getLoginOutUrl() {
		return loginOutUrl;
	}

	public void setLoginOutUrl(String loginOutUrl) {
		this.loginOutUrl = loginOutUrl;
	}

	// 默认登录页
	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	// 默认响应类型
	private LoginResponseType loginType = LoginResponseType.JSON;
	
	// 记住密码的失效时间
	private int rememberMeSeconds = 3600;

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginResponseType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginResponseType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}

	public String getSignOutUrl() {
		return signOutUrl;
	}

	public void setSignOutUrl(String signOutUrl) {
		this.signOutUrl = signOutUrl;
	}

}
