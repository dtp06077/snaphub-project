package project.server.security.constants;

/** 지정해야 할 것
 * - 로그인 필터 경로
 * - 토큰 헤더
 * - 토큰 헤더의 접두사(prefix)
 * - 토큰 타입
 */
public class JwtConstants {

    public static final String AUTH_LOGIN_URL = "/login";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String TOKEN_TYPE = "JWT";
}
