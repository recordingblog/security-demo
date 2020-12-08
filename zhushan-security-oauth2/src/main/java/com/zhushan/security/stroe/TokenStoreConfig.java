package com.zhushan.security.stroe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.zhushan.security.app.jwt.ZhuShanJwtTokenEnhancer;
import com.zhushan.security.core.properties.SecurityProperties;

@Configuration
public class TokenStoreConfig {
	
	@Autowired// 用于创建TokenStore
	private RedisConnectionFactory redisConnectionFactory;
	
	@Bean // 创建TokenStore并存储令牌，将生产的令牌放置redis中
	@ConditionalOnProperty(
		prefix="zhushan.security.oauth2",name="storeType",havingValue="redis"
	)
	public TokenStore getRedisStore(){
		return new RedisTokenStore(redisConnectionFactory);	
	}
	
	@Configuration
	@ConditionalOnProperty( // 通过检查zhushan.security.oauth2的后缀来决定那个配置生效
		prefix="zhushan.security.oauth2",name="storeType",havingValue="jwt",
		matchIfMissing = true // 设置为ture的时候,如果不写默认生效
	)
	public static class JwtTokenConfig{
		
		@Autowired
		private SecurityProperties securityProperties;
		
		@Bean // 使用jwt来替换令牌
		public TokenStore JwtTokenStore(){
			return new JwtTokenStore(jwtJwtAccessTokenConverter());
		}
		
		@Bean
		public JwtAccessTokenConverter jwtJwtAccessTokenConverter(){
			// 指定一个秘钥进行签名
			JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
			accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
			return accessTokenConverter;
		}
		
		@Bean // 令牌改造
		//@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
		@Qualifier("jwtTokenEnhancer")
		@Primary
		public TokenEnhancer JwtTokenEnhancer(){
			return new ZhuShanJwtTokenEnhancer();
		}
		
	}
}
