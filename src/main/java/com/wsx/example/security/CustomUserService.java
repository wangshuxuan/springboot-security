package com.wsx.example.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wsx.example.dao.PermissionDao;
import com.wsx.example.dao.UserDao;
import com.wsx.example.domain.Permission;
import com.wsx.example.domain.SysRole;
import com.wsx.example.domain.SysUser;

/**
 * 
 * @author wsx
 *
 */

@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户
		SysUser user = userDao.findByUserName(username);
		if (user != null) {
			List<Permission> permissions  = permissionDao.findByAdminUserId(user.getId());
			List<GrantedAuthority> authorities = new ArrayList<>();
			for (Permission permission : permissions) {
				if (permission != null && permission.getName()!=null) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
					 //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行权限验证时会使用GrantedAuthority 对象。
					authorities.add(grantedAuthority);
				}
			}
			return new User(user.getUsername(), user.getPassword(), authorities);
		}else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
	}
}
