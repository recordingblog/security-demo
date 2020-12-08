
package com.zhushan.security.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 获取用户信息接口实现
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
	
	// 提交access_token获取openid
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	// 携带授权 通过openid 获取对用的用户信息
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	private String appId;
	
	private String openId;
	
	// 使用默认实现
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public QQImpl(String accessToken, String appId) {
		// 将请求需要的一些数据加入到请求url中，默认的是放入请求头
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		// 替换%s
		String url = String.format(URL_GET_OPENID, accessToken);
		// 发起请求
		String result = getRestTemplate().getForObject(url, String.class);
		
		System.out.println(result);
		// 截取openid
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}
	
	/* (non-Javadoc)
	 * @see com.zhushan.security.core.social.qq.api.QQ#getUserInfo()
	 */
	@Override // 获取用户信息
	public QQUserInfo getUserInfo() {
		
		// 通过应用id,openid 去请求用户信息
		String url = String.format(URL_GET_USERINFO, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);
		
		System.out.println(result);
		
		QQUserInfo userInfo = null;
		try {
			// 读取结果封装进QQUserInfo
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}

}
