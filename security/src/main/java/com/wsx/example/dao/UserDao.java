package com.wsx.example.dao;

import com.wsx.example.domain.SysUser;

/**
 * 
 * @author wsx
 *
 */

public interface UserDao {
	public SysUser findByUserName(String username);
}
