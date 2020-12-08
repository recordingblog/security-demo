package com.zhushan.security.app.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
// 改造令牌信息
public class ZhuShanJwtTokenEnhancer implements TokenEnhancer{
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
			OAuth2Authentication authentication) {
		Map<String,Object> info = new HashMap<>();
		info.put("company", "zhushan");//被 添加的附加信息
		// 对令牌添加附加信息
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
