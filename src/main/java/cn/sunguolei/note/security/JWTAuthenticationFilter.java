package cn.sunguolei.note.security;

import cn.sunguolei.note.domain.TokenInfo;
import cn.sunguolei.note.domain.User;
import cn.sunguolei.note.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static cn.sunguolei.note.security.SecurityConstants.EXPIRATION_TIME;
import static cn.sunguolei.note.security.SecurityConstants.SECRET;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            System.out.println("this is debug breakpoint.");

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws JsonProcessingException {

        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

//        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

        // 写 token 到 response 的 cookie 里
        response = UserUtil.setTokenToCookie(response, token);

        // 设置返回结果
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setLogin(false);
        tokenInfo.setResultCode(200);
        tokenInfo.setUsername(username);

        String resultString = new ObjectMapper().writeValueAsString(tokenInfo);

        // 将 JWT 写入 body
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
