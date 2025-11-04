package com.smartlogistics.auth.service;

import com.smartlogistics.auth.entity.User;
import com.smartlogistics.auth.repository.UserRepository;
import com.smartlogistics.auth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;

  public User register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("USER");
    return userRepository.save(user);
  }

  public String login(String username, String password) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Invalid username or password"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("Invalid username or password");
    }

    return jwtTokenUtil.generateToken(user.getUsername(), user.getRole());
  }
}
