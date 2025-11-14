package com.smartlogistics.gateway.filter;

import com.smartlogistics.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

  @Autowired private JwtUtil jwtUtil;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getURI().getPath();

    System.out.println("🔍 [JWT Filter] Path: " + path);
    System.out.println(
        "🔍 [JWT Filter] Incoming Authorization Header: "
            + request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

    // Skip public endpoints
    if (path.startsWith("/auth") || path.contains("/actuator")) {
      return chain.filter(exchange);
    }

    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      System.out.println("❌ [JWT Filter] Missing or invalid Authorization header");
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    String token = authHeader.substring(7);
    boolean valid = jwtUtil.isTokenValid(token);

    if (!valid) {
      System.out.println("❌ [JWT Filter] Token validation failed!");
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    Claims claims = jwtUtil.extractAllClaims(token);
    String username = claims.getSubject();
    String role = claims.get("role", String.class);

    System.out.println(
        "✅ [JWT Filter] Token accepted for user: " + username + " with role: " + role);

    ServerHttpRequest modifiedRequest =
        request.mutate().header("X-User-Name", username).header("X-User-Role", role).build();

    exchange.getResponse().getHeaders().add("X-Gateway-Debug", "Token accepted");
    return chain.filter(exchange.mutate().request(modifiedRequest).build());
  }
}
