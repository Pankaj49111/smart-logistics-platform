package com.smartlogistics.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  private static final String SECRET =
      "smartlogistics-secret-key-smartlogistics-secret-key"; // min 32 chars

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateToken(String username, String role) {
    return Jwts.builder()
        .setSubject(username)
        .claim("role", role)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }
}
