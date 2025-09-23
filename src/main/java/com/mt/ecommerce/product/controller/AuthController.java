package com.mt.ecommerce.product.controller;


import com.mt.ecommerce.product.config.JwtService;
import com.mt.ecommerce.product.entity.UserInfo;
import com.mt.ecommerce.product.model.AuthRequest;
import com.mt.ecommerce.product.model.Store;
import com.mt.ecommerce.product.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
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
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    private final UserInfoService service;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping( value = "/user", consumes = "application/json")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        try {
            logger.info("Attempting to add new user with email: {}", userInfo.getEmail());
            return ResponseEntity.ok(service.addUser(userInfo));
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while adding user: {}", e.getMessage());
            return ResponseEntity.badRequest().body("User Already Exists!");
        } catch (Exception exception){
            logger.info("Exception occurred while adding user: {}", exception.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred: " + exception.getMessage());
        }
    }

    @PostMapping(value = "/user/vendor", consumes = "application/json", produces = {"application/json", MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> addVendor(@RequestBody Store store){
        try {
            logger.info("Attempting to add new user with email: {}", store.getUserInfo().getEmail());
            return ResponseEntity.ok(service.addVendorUser(store));
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while adding user: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Store Name Already Exists!");
        } catch (Exception exception){
            logger.info("Exception occurred while adding user: {}", exception.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred: " + exception.getMessage());
        }
    }


    @PostMapping("/token")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        logger.info("Authenticating user: {} is fetching token for sigin", authRequest.getUsername());
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(jwtService.generateToken(authRequest.getUsername()));
            } else {
                logger.error("Authentication failed for user: {}", authRequest.getUsername());
                return ResponseEntity.internalServerError().body("Invalid Username or Password");
            }
        } catch (Exception exception){
            logger.error("Authentication failed for user: {}. Error: {}", authRequest.getUsername(), exception.getMessage());
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping(value = "/token/vendor", produces = {"application/json", MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> authenticateAndGetTokenForVendor(@RequestBody AuthRequest authRequest) {
        logger.info("Authenticating user: {} is fetching token for sigin", authRequest.getUsername());
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                Store store  = service.getVendorStoreByUserName(authRequest.getUsername());
                store.setToken(jwtService.generateToken(authRequest.getUsername()));
                return ResponseEntity.ok(store);
            } else {
                logger.error("Authentication failed for user: {}", authRequest.getUsername());
                return ResponseEntity.internalServerError().body("Invalid Username or Password");
            }
        } catch (Exception exception){
            logger.error("Authentication failed for user: {}. Error: {}", authRequest.getUsername(), exception.getMessage());
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

}
