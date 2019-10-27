package xyz.luvtk.auth.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Tank Zheng
 * @since 2019.09.18
 * 客户端信息
 */
@Entity
@Table(name = "tb_sys_client_detail")
@Data
public class SysClientDetail implements Serializable {

    /**
     * id
     */
    @Id
    private Integer id;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 资源id
     */
    private String resourceIds;
    /**
     * 是否需要客户端密钥
     */
    private Boolean secretRequired;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 是否授权范围
     */
    private Boolean scoped;
    /**
     * 授权范围
     */
    private String scope;
    /**
     * 授权类型
     */
    private String authorizedGrantTypes;
    /**
     * 注册的跳转url
     */
    private String registeredRedirectUri;
    /**
     * 授权信息
     */
    private String authorities;
    /**
     * access token有效时间
     */
    private Integer accessTokenValiditySeconds;
    /**
     * refresh token有效时间
     */
    private Integer refreshTokenValiditySeconds;
    /**
     * 自动通过范围
     */
    private String autoApprove;
    /**
     * 额外的信息
     */
    private String additionalInformation;

}
