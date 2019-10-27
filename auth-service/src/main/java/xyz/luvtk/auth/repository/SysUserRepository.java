package xyz.luvtk.auth.repository;

import xyz.luvtk.auth.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Tank Zheng
 * @since 20190918
 * 权限用户查询Repository
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

	 SysUser findByUsername(String username);
}
