/**
 * 
 */
package com.zhushan.service.impl;

import org.springframework.stereotype.Service;

import com.zhushan.service.HelloService;


@Service
public class HelloServiceImpl implements HelloService {

	/* (non-Javadoc)
	 * @see com.zhushan.service.HelloService#greeting(java.lang.String)
	 */
	@Override
	public String greeting(String name) {
		System.out.println("greeting");
		return "hello "+name;
	}

}
