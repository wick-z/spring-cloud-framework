package xyz.luvtk.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author Tank Zheng
 * @since 20190918
 * 配置客户端查询AUTH构造器
 */
@Component
public class ClientServiceBuilder extends ClientDetailsServiceBuilder<ClientServiceBuilder> {
    /**
     * 客户端授权查询服务注入
     */
    @Autowired
    private ClientService clientDetailsService;

    @Override
    protected ClientDetailsService performBuild() {
        return clientDetailsService;
    }
}
