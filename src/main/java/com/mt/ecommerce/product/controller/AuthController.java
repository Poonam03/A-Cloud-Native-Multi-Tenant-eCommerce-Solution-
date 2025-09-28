package com.mt.ecommerce.product.controller;


import com.mt.ecommerce.product.config.JwtService;
import com.mt.ecommerce.product.entity.UserInfo;
import com.mt.ecommerce.product.model.AuthRequest;
import com.mt.ecommerce.product.model.Store;
import com.mt.ecommerce.product.model.UserInfoBO;
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
/**
 * Controller for handling authentication and user management.
 * Provides endpoints for user registration, vendor registration, and token generation.
 */
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

    /**
     * Endpoint to register a new user.
     * Accepts a UserInfo object in the request body and returns the created user or an error message.
     *
     * @param userInfo the user information to register
     * @return ResponseEntity with the created user or an error message
     */
    @PostMapping( value = "/user", consumes = "application/json")
    public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
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

    /**
     * Endpoint to register a new vendor user along with their store information.
     * Accepts a Store object in the request body and returns the created store or an error message.
     *
     * @param store the store information along with user details to register
     * @return ResponseEntity with the created store or an error message
     */
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


    /** * Endpoint to authenticate a user and generate a JWT token.
     * Accepts an AuthRequest object in the request body and returns a UserInfoBO with the token or an error message.
     *
     * @param authRequest the authentication request containing username and password
     * @return ResponseEntity with UserInfoBO containing the token or an error message
     */
    @PostMapping("/token")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        logger.info("Authenticating user: {} is fetching token for sigin", authRequest.getUsername());
        try{
            UserInfo userInfo  = this.service.getUser(authRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                UserInfoBO userInfoBO = new UserInfoBO();
                userInfoBO.setId(userInfo.getId());
                userInfoBO.setName(userInfo.getName());
                userInfoBO.setEmail(userInfo.getEmail());
                userInfoBO.setAddressLine1(userInfo.getAddressLine1());
                userInfoBO.setAddressLine2(userInfo.getAddressLine2());
                userInfoBO.setCity(userInfo.getCity());
                userInfoBO.setState(userInfo.getState());
                userInfoBO.setCountry(userInfo.getCountry());
                userInfoBO.setZipCode(userInfo.getZipCode());
                userInfoBO.setPhone(userInfo.getPhone());
                userInfoBO.setRoles(userInfo.getRoles());
                userInfoBO.setToken(jwtService.generateToken(authRequest.getUsername()));
                return ResponseEntity.ok(userInfoBO);
            } else {
                logger.error("Authentication failed for user: {}", authRequest.getUsername());
                return ResponseEntity.internalServerError().body("Invalid Username or Password");
            }
        } catch (Exception exception){
            logger.error("Authentication failed for user: {}. Error: {}", authRequest.getUsername(), exception.getMessage());
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    /** * Endpoint to authenticate a vendor user and generate a JWT token.
     * Accepts an AuthRequest object in the request body and returns a Store with the token or an error message.
     *
     * @param authRequest the authentication request containing username and password
     * @return ResponseEntity with Store containing the token or an error message
     */
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
