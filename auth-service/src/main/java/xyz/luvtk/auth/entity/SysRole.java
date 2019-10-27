package xyz.luvtk.auth.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

/**
 * @author Tank Zheng
 * @since 20190918
 * 权限角色类
 */
@Entity
@Table(name = "tb_sys_auth_role")
@DynamicUpdate
@DynamicInsert
@Data
@NoArgsConstructor
public class SysRole implements GrantedAuthority {

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 名称
	 */
	@Column(nullable = false)
	private String name;


	@Override
	public String getAuthority() {
		return null;
	}
}
