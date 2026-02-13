package com.auth.controller;

import com.auth.exception.BadRequestException;
import com.auth.model.JwtTokenResponse;
import com.auth.model.LogiRequest;
import com.auth.model.User;
import com.auth.model.UserDto;
import com.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    public final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        UserDto userDto = userService.saveUser(user);
        return new ResponseEntity<>(userDto,HttpStatus.CREATED);
    }

    @PostMapping("/generate-token")
    public JwtTokenResponse generateToken(@RequestBody LogiRequest loginRequest){
//        JwtTokenResponse jwtTokenResponse = userService.generateToken(loginRequest.getUserName());
       // Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPasswords()));
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPasswords()));

            if(authentication.isAuthenticated()){
                return userService.generateToken(loginRequest.getUsername());
            }else{
                throw new BadRequestException("bad credential");
            }

        }catch (Exception e){
            throw new BadRequestException("wrong Credentials");
        }
    }
}
