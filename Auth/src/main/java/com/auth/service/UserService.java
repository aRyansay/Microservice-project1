package com.auth.service;

import com.auth.Util.JwtUtil;
import com.auth.model.JwtTokenResponse;
import com.auth.model.User;
import com.auth.model.UserDto;
import com.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserDto saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        return new UserDto(save.getId(),save.getUserName(),save.getEmail(),save.getRoles());
    }
    public JwtTokenResponse generateToken(String username){
        String token = jwtUtil.generateToken(username);
        JwtTokenResponse jwt = new JwtTokenResponse();
        jwt.setToken(token);
        jwt.setType("Bearer");
        jwt.setValidatity(jwtUtil.extractExpiration(token).toString());
        return jwt;
    }

}
