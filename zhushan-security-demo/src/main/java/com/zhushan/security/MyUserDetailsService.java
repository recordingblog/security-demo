/**
 * 
 */
package com.zhushan.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import com.zhushan.dto.User;
import com.zhushan.service.UserService;


@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("表单登录用户名:" + username);
		return buildUser(username);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		logger.info("社交登录用户Id:" + userId);
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String userId) {
		User user = userService.findUserById(userId);
		String password = passwordEncoder.encode(user.getPassword());
		logger.info("数据库密码是:"+password);
		logger.info("当前用户权限:"+user.getRole());
		return new SocialUser(userId, password,
				true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
	}

}
