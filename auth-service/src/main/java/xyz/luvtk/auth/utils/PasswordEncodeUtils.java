package xyz.luvtk.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Tank Zheng
 * @since 20190918
 * Spring 默认的加解密工具类
 */
@Component
public class PasswordEncodeUtils {

	/**
	 * 注入密码编码器
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String encode(String password) {
		return passwordEncoder.encode(password);
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
