package com.smartlogistics.auth.service;

import com.smartlogistics.auth.entity.User;
import com.smartlogistics.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setActive(true);
    return userRepository.save(user);
  }
}
