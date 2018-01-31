package cn.sunguolei.note.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static cn.sunguolei.note.security.SecurityConstants.*;

public class UserUtil {

    private static Logger logger = LoggerFactory.getLogger(UserUtil.class);

    /**
     * 获取用户登录信息
     *
     * @param request http 请求
     * @return 用户登录状态和用户信息
     */
    public static Map<String, String> getUserIdentity(HttpServletRequest request) {

        Map<String, String> userInfoMap = new HashMap<>();

        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // parse the token.
            String username;
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            logger.debug(body.toString());
            username = body.getSubject();

            if (username != null) {
                userInfoMap.put("isLogin", "true");
                userInfoMap.put("username", username);
            }
        } else {
            userInfoMap.put("isLogin", "false");
        }
        // 返回用户登录状态和用户信息
        return userInfoMap;
    }

    /**
     * 从 request 中获取 token
     *
     * @param request http 请求
     * @return 返回 token 或者 null
     */
    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        // 遍历所有的 cookie，查找 key 为 token 的 cookie
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = TOKEN_PREFIX + cookie.getValue();
                break;
            }
        }

        return token;
    }
}
