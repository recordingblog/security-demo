package com.zhushan.security.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import com.zhushan.security.core.properties.OAuth2ClientProperties;
import com.zhushan.security.core.properties.SecurityProperties;

@Configuration
@EnableAuthorizationServer
/**
 * OAuth2认证服务器配置
 * AuthorizationServerConfigurerAdapter:继承这个适配器以后,可以添加一些自定义的配置
 * 当不继承AuthorizationServerConfigurerAdapter的时候,容器会自己寻找AuthenticationManager和UserDetailsService
 * --
 */
public class ZhuShanAuthenticationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired // 认证管理器
	private AuthenticationManager authenticationManager;
	
	@Autowired // 用户详情
	private UserDetailsService userDetailsService;
	
	@Autowired 
	SecurityProperties securityProperties;
	
	@Autowired // 用来将AccessToken存储进redis中
	private TokenStore tokenStore;
	
	@Autowired(required=false)// 使用jwt替换默认令牌
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired(required=false)
	private TokenEnhancer jwtTokenEnhancer;
	
	@Override // 端点配置
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints// 配置入口点
			.tokenStore(tokenStore) // 设置令牌存储
			.authenticationManager(authenticationManager) // 设置认证管理器
			.userDetailsService(userDetailsService); // 设置用户详情
		if(jwtAccessTokenConverter!=null&&jwtTokenEnhancer!=null){
			// 当jwtAccessTokenConverter!=null的时候,使用JwtAccessTokenConverter来替换令牌
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();// 令牌改造链
			List<TokenEnhancer> enhancers = new ArrayList<>();
			enhancers.add(jwtTokenEnhancer);
			enhancers.add(jwtAccessTokenConverter);
			// 将jwtAccessTokenConverter的JWT签名和jwtTokenEnhancer令牌改造放入TokenEnhancerChain中
			enhancerChain.setTokenEnhancers(enhancers);
		endpoints
			.tokenEnhancer(enhancerChain);
			/*.accessTokenConverter(jwtAccessTokenConverter);*/
				
		}
	}
	
	@Override // 第三方客户端相关配置
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
		// 向内存中存储   clients.jdbc(dataSource) 向数据库存储
		InMemoryClientDetailsServiceBuilder inMemory = clients.inMemory();
		// 检查是否配置了OAuth2ClientProperties
		if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
			// 循环出每一个OAuth2ClientProperties 进行配置
			for(OAuth2ClientProperties config :securityProperties.getOauth2().getClients()){
				inMemory
					.withClient(config.getClientId())
					.secret(config.getClientSecret())
					.accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())// token的有效时间
					.authorizedGrantTypes("password","refresh_token")// 获取accesstoken的方式
					.scopes("all","read","write");// 获取accesstoken所拥有的权限
			}
		}	
	}
	
	@Override // 安全性配置
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		
	}
}	
