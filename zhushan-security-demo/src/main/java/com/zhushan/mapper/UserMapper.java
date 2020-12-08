package com.zhushan.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zhushan.dto.User;
@Mapper
public interface UserMapper {
	
	@Select("select * from zhushan_user where username = #{userid}")
	public User findUserById(String userId);
	

}
