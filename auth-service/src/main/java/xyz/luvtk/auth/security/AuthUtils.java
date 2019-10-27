package xyz.luvtk.auth.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tank Zheng
 * @since 20190918
 * 授权信息解析工具类
 */
public final class AuthUtils {
    private AuthUtils() {
    }
    public static Set<String> commaSeperatedStringToSet(String str) {
        return str == null ? null : Sets.newHashSet(StringUtils.split(str, ","));
    }

    public static Set<GrantedAuthority> commaSeperatedStringToAuthorities(String str) {
        if (str == null) {
            return null;
        } else {
            return Lists.newArrayList(StringUtils.split(str, ",")).stream().map(
                    SimpleGrantedAuthority::new
            ).collect(Collectors.toSet());
        }
    }
}
