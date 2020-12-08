
package com.zhushan.security.browser;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.zhushan.security.browser.support.SocialUserInfo;
import com.zhushan.security.core.properties.SecurityConstants;
import com.zhushan.security.core.properties.SecurityProperties;
import com.zhushan.security.core.support.SimpleResponse;

/**
 * 浏览器身份认证控制
 */
@RestController
public class BrowserSecurityController {
	
	// 日志
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// 缓存
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	// 重定向策略
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired // 配置项
	private SecurityProperties securityProperties;
	
	@Autowired //服务提供商工具类
	private ProviderSignInUtils providerSignInUtils;

	/**
	 * 当需要身份认证时，跳转到这里
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是:"+targetUrl);
			// 判断请求后缀
			if(StringUtils.endsWithIgnoreCase(targetUrl, "1.html")){
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
	}
	
	/**
	 * 当session失效后执行
	 * @return
	 */
	@GetMapping(SecurityConstants.DEFAULT_SESSIONINVALID_URL)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public SimpleResponse SessionInvalid(){
		String message = "session失效";
		return new SimpleResponse(message);
	}
	
	@GetMapping("/social/user")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		SocialUserInfo userInfo = new SocialUserInfo();
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());
		return userInfo;
	}

}
