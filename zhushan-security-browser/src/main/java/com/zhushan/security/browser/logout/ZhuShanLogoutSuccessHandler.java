package com.zhushan.security.browser.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhushan.security.core.properties.SecurityProperties;
import com.zhushan.security.core.support.SimpleResponse;

/**
 * 退出登录成功处理器
 */
@Component
public class ZhuShanLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override // 退出成功后的处理逻辑
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("退出成功");
		// 获取跳转页面
		String signOuUrl = securityProperties.getBrowser().getSignOutUrl();
		// 存在跳转页面的时候跳转页面，不存在跳转页面的时候返回json
		if(StringUtils.isBlank(signOuUrl)){
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(mapper.writeValueAsString(new SimpleResponse("退出成功")));
		}else{
			response.sendRedirect(signOuUrl);
		}
	}

}
