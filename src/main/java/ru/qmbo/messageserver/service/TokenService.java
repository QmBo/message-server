package ru.qmbo.messageserver.service;

import com.sun.istack.NotNull;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.qmbo.messageserver.model.User;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TokenService
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@Service
public class TokenService {
    @Value("${secret.for.token}")
    private String secret;

    private final Map<String, String> capacity = new HashMap<>();

    /**
     * Gets new token.
     *
     * @param user the user
     * @return the token
     */
    public String getToken(User user) {
        return this.generateToken(user.getName());
    }

    private String generateToken(String userName) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(this.secret));
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant());
        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(key)
                    .setSubject(userName)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
        }
        this.capacity.put(userName, token);
        return token;
    }

    /**
     * Check token.
     *
     * @param token the token
     * @param name  the name
     * @return the access
     */
    public boolean checkToken(@NotNull String token, @NotNull String name) {
        boolean result = false;
        String find = this.capacity.get(name);
        if (find != null && find.equals(token)) {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(this.secret));
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
            if (body.getExpiration().after(new Date(System.currentTimeMillis()))
                    && body.getSubject().equals(name)) {
                result = true;
            }
        }
        return result;
    }
}
