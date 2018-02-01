package cn.sunguolei.note.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        // 先通过 Authorization header 方式获取 token
        String token = request.getHeader(HEADER_STRING);

        // 如果通过 header 拿不到 token，就通过 cookie 拿 token
        if (token == null) {
            token = UserUtil.getTokenFromCookie(request);
        }

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
    public static String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            // 遍历所有的 cookie，查找 key 为 token 的 cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
            return token;
        }
        return null;
    }

    /**
     * 往 response 中的 cookie 写入 token
     * @param response 返回的响应
     */
    public static HttpServletResponse setTokenToCookie(HttpServletResponse response, String token) {

        // token 存放到 cookie 中，并设置到期时间为 30 天
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setMaxAge(30 * 24 * 60 * 60);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);

        return response;
    }
}
