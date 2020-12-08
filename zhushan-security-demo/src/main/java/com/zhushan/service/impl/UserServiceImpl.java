package com.zhushan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhushan.dto.User;
import com.zhushan.mapper.UserMapper;
import com.zhushan.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserById(String userId) {
		return userMapper.findUserById(userId);
	}

}
