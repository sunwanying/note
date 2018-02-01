package cn.sunguolei.note.security;

import cn.sunguolei.note.utils.UserUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static cn.sunguolei.note.security.SecurityConstants.SECRET;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 通过 Authorization 头判断请求头中是否带有 token
//        String headerToken = request.getHeader(HEADER_STRING);

        // 如果 header 和 cookie 中的 token 都不存在的话，就走其他的过滤器，让用户登录
//        if ( headerToken == null || !headerToken.startsWith(TOKEN_PREFIX)) {
        String token = UserUtil.getTokenFromCookie(request);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
//        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        // 先通过 Authorization header 方式获取 token
//        String token = request.getHeader(HEADER_STRING);

        // 如果通过 header 拿不到 token，就通过 cookie 拿 token
//        if (token == null) {
        String token = UserUtil.getTokenFromCookie(request);
//        }

        if (token != null) {
            // parse the token.
            String username = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
//                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
