package com.smartlogistics.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String SECRET;

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean isTokenValid(String token) {
    try {
      Claims claims = extractAllClaims(token);
      System.out.println("✅ [JWT] Token valid until " + claims.getExpiration());
      return !claims.getExpiration().before(new Date());
    } catch (Exception e) {
      System.out.println(
          "❌ [JWT] Validation failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
      return false;
    }
  }

  public String getUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public String getRole(String token) {
    return extractAllClaims(token).get("role", String.class);
  }
}
