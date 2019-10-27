package xyz.luvtk.auth.feign;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AuthFeignConfiguration {
    /**
     * 认证客户端id
     */
    @Value("${oauth2.client.clientId}")
    private String clientId;
    /**
     * 客户端密钥
     */
    @Value("${oauth2.client.clientSecret}")
    private String clientSecret;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(clientId, clientSecret);
    }

    @Bean
    public Logger.Level defaultLevel() {
        return Logger.Level.FULL;
    }
}
