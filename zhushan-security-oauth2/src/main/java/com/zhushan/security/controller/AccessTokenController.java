package com.zhushan.security.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("store")
public class AccessTokenController {
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired 
	ClientDetailsService clientDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("findTokensByClientId") // 查询令牌
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId){
		return tokenStore.findTokensByClientId(clientId);
	}
	
	@RequestMapping("findTokensByClientIdAndUserName") // 查询令牌
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId,String userName){
		return tokenStore.findTokensByClientIdAndUserName(clientId, userName);
	}
	
	@RequestMapping("readAccessToken")// 读取令牌
	public OAuth2AccessToken readAccessToken(String tokenValue){
		return tokenStore.readAccessToken(tokenValue);
	}
	
	@RequestMapping("readRefreshToken") // 读取刷新令牌
	public OAuth2RefreshToken readRefreshToken(String tokenValue){
		return tokenStore.readRefreshToken(tokenValue);
	}
	
	@RequestMapping("getUserDetails") // 获取用户的账号密码认证信息权限等
	public UserDetails getUserDetails(String username){
		return userDetailsService.loadUserByUsername(username);
	}
	
	@RequestMapping("getClientDetails") // 获取第三方客户端信息
	public ClientDetails getClientDetails(String clientId){
		return clientDetailsService.loadClientByClientId(clientId);
	}
	
}
