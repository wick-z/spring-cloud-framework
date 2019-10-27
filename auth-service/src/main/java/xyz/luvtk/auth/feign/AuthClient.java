package xyz.luvtk.auth.feign;

import xyz.luvtk.auth.dto.GenericResponse;
import xyz.luvtk.auth.dto.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Tank Zheng
 * @since 20190918
 * Feign客户端
 */
@FeignClient(value = "auth-service", configuration = AuthFeignConfiguration.class)
public interface AuthClient {

	@PostMapping(value = "/oauth/token")
    GenericResponse<TokenDto> getToken(@RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       @RequestParam("grant_type") String type);

	@RequestMapping(method = RequestMethod.POST, value = "/oauth/token",
			consumes = "application/x-www-form-urlencoded", produces = "application/json")
	GenericResponse<TokenDto> refreshToken(@RequestParam("refresh_token") String refreshToken,
                                           @RequestParam("grant_type") String grantType,
                                           @RequestParam("client_id") String clientId,
                                           @RequestParam("client_secret") String clientSecret);
}
