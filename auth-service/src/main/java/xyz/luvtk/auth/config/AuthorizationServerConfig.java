package xyz.luvtk.auth.config;

import xyz.luvtk.auth.security.ClientServiceBuilder;
import xyz.luvtk.auth.service.UserServiceDetail;
import xyz.luvtk.auth.utils.JWTTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Tank Zheng
 * @since 20190918
 * 授权服务相关配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 认证管理器，验证token等
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用户信息加密私钥
     */
    @Value("${jwt.key.private}")
    private String privateKey;

    /**
     * 用户信息验证接口，封装用户名密码、是否过期、是否可用等信息.
     */
    @Autowired
    private ClientServiceBuilder clientServiceBuilder;
    /**
     * 注入密码编码器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注入用户信息查询服务
     */
    @Autowired
    private UserServiceDetail userServiceDetail;
    /**
     * 配置客户端详细信息，这里使用数据库作为存储
     * @param clients 客户端配置
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.setBuilder(clientServiceBuilder);
    }

    /**
     * 配置授权配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder)
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置授权认证的信息处理
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        endpoints.tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .reuseRefreshTokens(true)
                .authenticationManager(authenticationManager)
                .userDetailsService(userServiceDetail);
    }

    /**
     * 配置jwt token的解密转换
     * @return 转换器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(JWTTokenBuilder.parseKeyPair(privateKey));
        return jwtAccessTokenConverter;
    }

    /**
     * 配置jwt内部转换存储
     * @return 存储
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}
