package com.freedom.mybatis.mapper;

import com.freedom.mybatis.domain.User;

public interface UserMapper {
	User findByUserId(int id);
	User findByUserName(String username);
}
