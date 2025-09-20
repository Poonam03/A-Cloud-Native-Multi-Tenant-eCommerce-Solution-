package com.mt.ecommerce.product.controller;


import com.mt.ecommerce.product.config.JwtService;
import com.mt.ecommerce.product.entity.UserInfo;
import com.mt.ecommerce.product.model.AuthRequest;
import com.mt.ecommerce.product.service.UserInfoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserInfoService service;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping( value = "/user", consumes = "application/json")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }


    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}
