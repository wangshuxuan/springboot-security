package com.wsx.example.dao;

import java.util.List;

import com.wsx.example.domain.Permission;

/**
 * 
 * @author wsx
 *
 */
public interface PermissionDao {
	public List<Permission> findAll();
    public List<Permission> findByAdminUserId(int userId);
}
