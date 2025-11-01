package com.smartlogistics.gateway.filter;

import com.smartlogistics.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    // Skip public endpoints
    if (request.getURI().getPath().contains("/auth")
        || request.getURI().getPath().contains("/actuator")) {
      return chain.filter(exchange);
    }

    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    String token = authHeader.substring(7);
    if (!jwtUtil.isTokenValid(token)) {
      exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    Claims claims = jwtUtil.extractAllClaims(token);
    String username = claims.getSubject();
    String role = claims.get("role", String.class);

    // Forward user info downstream as headers
    ServerHttpRequest modifiedRequest =
        exchange
            .getRequest()
            .mutate()
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .build();

    return chain.filter(exchange.mutate().request(modifiedRequest).build());
  }
}
