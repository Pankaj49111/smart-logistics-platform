package com.smartlogistics.auth.controller;

import com.smartlogistics.auth.entity.LoginRequest;
import com.smartlogistics.auth.entity.User;
import com.smartlogistics.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public User register(@RequestBody User user) {
    return authService.register(user);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest loginRequest) {
    return authService.login(loginRequest.getUsername(), loginRequest.getPassword());
  }

  @GetMapping("/health")
  public String health() {
    return "Auth Service is running ✅";
  }
}
