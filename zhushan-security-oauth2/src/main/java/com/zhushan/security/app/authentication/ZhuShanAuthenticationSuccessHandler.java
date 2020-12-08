
package com.zhushan.security.app.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhushan.security.core.properties.OAuth2ClientProperties;
import com.zhushan.security.core.properties.SecurityProperties;

@Component("AuthenticationSuccessHandler")
public class ZhuShanAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired // 用作json转换
	private ObjectMapper objectMapper;

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired // 获取第三方应用的配置信息
	private ClientDetailsService clientDetailsService;
	
	@Autowired // 用来创建accesstoken
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	
	private String clientId;
	
	private String clientSecret;
	
	
	/*@Autowired // 获取默认配置 
	private ClientDetails clientDetails;*/
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	
	@SuppressWarnings("unchecked")
	@Override 
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 不知道咋搞,只能提供固定的了,正常的流程是把clientId,clientSecret挂到请求头作为第三方信息
		
		logger.info("登录成功");
		
		// 从请求头中获取
		/*String header = request.getHeader("Authorization");
		
		if (header == null || !header.startsWith("Basic ")) {
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}
		
		// 获取clientId和clientSecret 从请求头中去获取
		String[] tokens = extractAndDecodeHeader(header, request);
		
		assert tokens.length == 2;
		
		String clientId = tokens[0];
		String clientSecret = tokens[1];
		*/
		
		// 从配置文件中读取,使用自定义的生成逻辑,怎么区分要使用哪个clients的配置？
		if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
			// 循环出每一个OAuth2ClientProperties 进行配置
			for(OAuth2ClientProperties config :securityProperties.getOauth2().getClients()){
				this.clientId = config.getClientId();
				this.clientSecret = config.getClientSecret();
			}
		}
		
		// 使用默认的生成逻辑
		/*String clientId = clientDetails.getClientId();
		String clientSecret = clientDetails.getClientSecret();*/
		
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		
		if(clientDetails==null){
			throw new UnapprovedClientAuthenticationException("clientId对应的第三方客户端信息不存在:"+clientId);
		}else if(!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)){
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配"+clientSecret);
		}
		
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(),"custom");
	
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		
		// 创建令牌
		OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		
		response.setContentType("application/json;charset=UTF-8");
		// 返回令牌信息
		response.getWriter().write(objectMapper.writeValueAsString(accessToken));
	
	}
	
	@SuppressWarnings("unused")
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {
		// 去掉basic
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		
		try {
			// 进行base64解码
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}
		String token = new String(decoded,"UTF-8");
		
		int delim = token.indexOf(":");

		if (delim == -1) {

			throw new BadCredentialsException("Invalid basic authentication token");
		}
		// 返回 clientid跟clientSecret
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
