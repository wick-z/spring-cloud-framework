package xyz.luvtk.auth.service;

import xyz.luvtk.auth.entity.SysUser;
import xyz.luvtk.auth.repository.SysUserRepository;
import xyz.luvtk.auth.utils.PasswordEncodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Tank Zheng
 * @since 20190918
 * 用户授权信息服务
 */
@Service
@Order(-1)
public class UserServiceDetail implements UserDetailsService {

    /**
     * 密码编码及匹配工具类注入
     */
	@Autowired
	private PasswordEncodeUtils passwordEncodeUtils;

    /**
     * 系统用户查询repository注入
     */
	@Autowired
	private SysUserRepository sysUserRepository;

	/**
	 * 查询用户信息
	 * @param username 用户名
	 * @return UserDetails
	 * @exception 可能会抛出用户名不存在异常
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return sysUserRepository.findByUsername(username);
	}

	/**
	 * 新增用户
	 * @param username 用户名
	 * @param password 用户名
	 * @return SysUser
	 */
	@Transactional
	public SysUser saveUser(String username, String password) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser.setPassword(passwordEncodeUtils.encode(password));
		sysUserRepository.save(sysUser);
		return sysUser;
	}
}
