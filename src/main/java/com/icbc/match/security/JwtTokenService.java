package com.icbc.match.security;

import com.icbc.match.entry.SecurityUser;
import com.icbc.match.exception.TokenExpireException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.icbc.match.config.SecurityConstants.*;


/**
 * JwtToken生成解析服务
 */
@Service
public class JwtTokenService {

    /**
     * JwtToken生成服务
     *
     * @param user 传入user对象
     * @return 返回token字符串
     */
    public String generateToken(SecurityUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_UUID, user.getUuid());

        return TOKEN_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

    /**
     * 解析token，转化为User对象
     *
     * @param token token字符串
     * @return User对象
     */
    public SecurityUser getUserFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }

        String uuid = (String) claims.get(CLAIM_KEY_UUID);

        try {
            SecurityUser user = new SecurityUser();
            user.setUuid(uuid);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public Claims getClaimsFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            throw new TokenExpireException(e.getMessage(), e.fillInStackTrace());
        } catch (Exception e) {
            return null;
        }
    }

}