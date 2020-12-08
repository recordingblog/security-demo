package com.zhushan.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 通过实现SessionInformationExpiredStrategy接口,从SessionInformationExpiredEvent中获取超时事件
 */
public class ZhuShanExpiredSessionStrategy implements SessionInformationExpiredStrategy {

	@Override//并发登录导致session超时的处理策略
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
			throws IOException, ServletException {
		event.getResponse().setContentType("application/json;charset=UTF-8");
		event.getResponse().getWriter().write("并发登录,session失效");
	}

}
