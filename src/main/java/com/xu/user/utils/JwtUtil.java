package com.xu.user.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    /** 密钥字符串 */
    @Value("${jwt.secret}")
    private String secretStr;

    /** token过期时间 */
    @Value("${jwt.expire}")
    private Long expireTime;

    /** 请求头key */
    @Value("${jwt.header}")
    private String headerKey;

    /**
     * 生成加密密钥
     */
    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretStr);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成JWT Token（仅存储用户ID）
     * @param userId 用户唯一id
     * @return token字符串
     */
    public String createToken(Long userId) {
        return createToken(userId, new HashMap<>());
    }

    /**
     * 生成JWT Token（携带自定义扩展信息，如角色、用户名）
     * @param userId 用户id
     * @param claims 自定义业务数据
     * @return token
     */
    public String createToken(Long userId, Map<String, Object> claims) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                // 自定义业务载荷
                .addClaims(claims)
                // 主体：用户ID
                .setSubject(String.valueOf(userId))
                // 签发时间
                .setIssuedAt(now)
                // 过期时间
                .setExpiration(expireDate)
                // 签名加密
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从token中获取用户ID
     */
    public Long getUserId(String token) {
        Claims claims = getClaimsByToken(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 获取token内全部载荷信息
     */
    public Claims getClaimsByToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 校验token是否合法有效
     * @return true=有效，false=失效/伪造/格式错误
     */
    public boolean verifyToken(String token) {
        try {
            getClaimsByToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            // token已过期
        } catch (MalformedJwtException e) {
            // token格式错误
        } catch (SignatureException e) {
            // 密钥不匹配，伪造token
        } catch (IllegalArgumentException e) {
            // token为空
        } catch (Exception e) {
            // 其他异常
        }
        return false;
    }

    /**
     * 从HttpServletRequest中提取请求头里的token
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(headerKey);
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }
}