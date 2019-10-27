package xyz.luvtk.auth.repository;

import xyz.luvtk.auth.entity.SysClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Tank Zheng
 * @since 20190918
 * 客户端查询Repository
 */
public interface SysClientDetailRepository extends JpaRepository<SysClientDetail, String> {

    SysClientDetail findOneByClientId(String id);
}
