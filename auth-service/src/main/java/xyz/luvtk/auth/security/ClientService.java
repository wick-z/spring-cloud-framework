package xyz.luvtk.auth.security;

import xyz.luvtk.auth.repository.SysClientDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

/**
 * @author Tank Zheng
 * @since 20190918
 * 客户端授权信息查询服务
 */
@Component
public class ClientService implements ClientDetailsService {

    /**
     * 注入系统客户信息Repository
     */
    private final SysClientDetailRepository repo;

    @Autowired
    public ClientService(SysClientDetailRepository repo) {
        this.repo = repo;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client detail = Client.of(repo.findOneByClientId(clientId));
        return detail;
    }
}
