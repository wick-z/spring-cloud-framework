package xyz.luvtk.auth.security;

import xyz.luvtk.auth.entity.SysClientDetail;
import xyz.luvtk.auth.utils.JsonMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Tank Zheng
 * @since 20190918
 * 客户端信息
 */
public class Client implements ClientDetails {
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 资源id集合
     */
    private Set<String> resourceIds;
    /**
     * 是否需要客户端密钥
     */
    private boolean secretRequired;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 是否在作用域内
     */
    private boolean scoped;
    /**
     * 作用域
     */
    private Set<String> scope;
    /**
     * 授权类型
     */
    private Set<String> authorizedGrantTypes;
    /**
     * 跳转的url
     */
    private Set<String> registeredRedirectUri;
    /**
     * 授权
     */
    private Collection<GrantedAuthority> authorities;
    /**
     * access token有效期
     */
    private Integer accessTokenValiditySeconds;
    /**
     * refresh token有效期
     */
    private Integer refreshTokenValiditySeconds;
    /**
     * 自动通过的作用域
     */
    private Set<String> autoApprovedScopes;
    /**
     * 额外信息
     */
    private Map<String, Object> additionalInformation;

    @SuppressWarnings("unchecked")
    public static Client of(SysClientDetail entity) {
        Client detail = new Client();
        detail.clientId = entity.getClientId();
        detail.resourceIds = AuthUtils.commaSeperatedStringToSet(entity.getResourceIds());
        detail.secretRequired = entity.getSecretRequired();
        detail.clientSecret = entity.getClientSecret();
        detail.scoped = entity.getScoped();
        detail.scope = AuthUtils.commaSeperatedStringToSet(entity.getScope());
        detail.authorizedGrantTypes = AuthUtils.commaSeperatedStringToSet(entity.getAuthorizedGrantTypes());
        detail.registeredRedirectUri = AuthUtils.commaSeperatedStringToSet(entity.getRegisteredRedirectUri());
        detail.authorities = AuthUtils.commaSeperatedStringToAuthorities(entity.getAuthorities());
        detail.accessTokenValiditySeconds = entity.getAccessTokenValiditySeconds();
        detail.refreshTokenValiditySeconds = entity.getRefreshTokenValiditySeconds();
        detail.autoApprovedScopes = AuthUtils.commaSeperatedStringToSet(entity.getAutoApprove());
        detail.additionalInformation = JsonMapper.defaultMapper().fromJson(entity.getAdditionalInformation(), Map.class);
        return detail;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return secretRequired;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return scoped;
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return autoApprovedScopes != null && autoApprovedScopes.contains(scope);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }
}
