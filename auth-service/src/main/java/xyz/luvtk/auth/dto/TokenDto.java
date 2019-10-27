package xyz.luvtk.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tank Zheng
 * @since
 * 20190918
 * 令牌信息
 */
public class TokenDto implements Serializable {
  /**
   * 访问令牌
   **/
  private String accessToken = null;
  /**
   * 刷新令牌
   **/
  private String refreshToken = null;
  /**
   * 令牌类型
   **/
  private String tokenType = null;
  /**
   * access_token x秒后过期
   **/
  private Integer expiresIn = null;
  /**
   * refresh_token x秒后过期
   **/
  private Integer reExpiresIn = null;
  /**
   * 令牌作用域
   **/
  private Set<String> scope = new HashSet<String>();
  /**
   * 令牌id
   **/
  private String jti = null;
  /**
   * 访问令牌
   * @param accessToken 访问令牌
   * @return tokenDto
   **/
  public TokenDto accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  @JsonProperty("access_token")
  public String getAccessToken() {
    return accessToken;
  }
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  /**
   * 刷新令牌
   * @param refreshToken 刷新令牌
   * @return tokenDto
   **/
  public TokenDto refreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }
  @JsonProperty("refresh_token")
  public String getRefreshToken() {
    return refreshToken;
  }
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
  /**
   * 令牌类型
   * @param tokenType 令牌类型
   * @return tokenDto
   **/
  public TokenDto tokenType(String tokenType) {
    this.tokenType = tokenType;
    return this;
  }
  @JsonProperty("token_type")
  public String getTokenType() {
    return tokenType;
  }
  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  /**
   * access_token x秒后过期
   * @param expiresIn access_token x秒后过期
   * @return tokenDto
   **/
  public TokenDto expiresIn(Integer expiresIn) {
    this.expiresIn = expiresIn;
    return this;
  }
  @JsonProperty("expires_in")
  public Integer getExpiresIn() {
    return expiresIn;
  }
  public void setExpiresIn(Integer expiresIn) {
    this.expiresIn = expiresIn;
  }
  /**
   * refresh_token x秒后过期
   * @param reExpiresIn  refresh_token x秒后过期
   * @return tokenDto
   **/
  public TokenDto reExpiresIn(Integer reExpiresIn) {
    this.reExpiresIn = reExpiresIn;
    return this;
  }
  @JsonProperty("re_expires_in")
  public Integer getReExpiresIn() {
    return reExpiresIn;
  }
  public void setReExpiresIn(Integer reExpiresIn) {
    this.reExpiresIn = reExpiresIn;
  }
  /**
   * 令牌作用域
   * @param scope 令牌作用域
   * @return tokenDto
   **/
  public TokenDto scope(Set<String> scope) {
    this.scope = scope;
    return this;
  }
  @JsonProperty("scope")
  public Set<String> getScope() {
    return scope;
  }
  public void setScope(Set<String> scope) {
    this.scope = scope;
  }
  /**
   * 令牌id
   * @param jti 令牌id
   * @return tokenDto
   **/
  public TokenDto jti(String jti) {
    this.jti = jti;
    return this;
  }
  @JsonProperty("jti")
  public String getJti() {
    return jti;
  }
  public void setJti(String jti) {
    this.jti = jti;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenDto token = (TokenDto) o;
    return Objects.equals(accessToken, token.accessToken)
            && Objects.equals(refreshToken, token.refreshToken)
            && Objects.equals(tokenType, token.tokenType)
            && Objects.equals(expiresIn, token.expiresIn)
            && Objects.equals(reExpiresIn, token.reExpiresIn)
            && Objects.equals(scope, token.scope)
            && Objects.equals(jti, token.jti);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessToken, refreshToken, tokenType, expiresIn, reExpiresIn, scope, jti);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenDto {\n");
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
    sb.append("    tokenType: ").append(toIndentedString(tokenType)).append("\n");
    sb.append("    expiresIn: ").append(toIndentedString(expiresIn)).append("\n");
    sb.append("    reExpiresIn: ").append(toIndentedString(reExpiresIn)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    jti: ").append(toIndentedString(jti)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   * @param o 对象
   * @return string
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

