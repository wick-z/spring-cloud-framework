package xyz.luvtk.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * @author Tank Zheng
 * @since 20190918
 * JWT token储存配置，采用公钥进行加密
 *
 */
@Configuration
public class JwtConfig {

	/**
	 * 注入jwt转换器
	 */
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;


	@Bean
	@Qualifier("tokenStore")
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer(@Value("${jwt.key.public}")String publicKey) {
		JwtAccessTokenConverter converter =  new JwtAccessTokenConverter();
		converter.setVerifierKey(publicKey);
		return converter;
	}
}
