package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing user-related operations.
 * Provides endpoints for validating user roles and fetching user-specific data.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserInfoService service;

    public UserController(UserInfoService service) {
        this.service = service;
    }

    /**
     * Endpoint to validate if the authenticated user has either ROLE_VENDOR or ROLE_ADMIN.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @GetMapping("/vendor")
    public void validVendorOrAdminUser(){}

    /**
     * Endpoint to validate if the authenticated user has ROLE_ADMIN.
     * Accessible only by users with ROLE_ADMIN.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public void validAdminUser(){}

    /**
     * Endpoint to validate if the authenticated user has ROLE_USER.
     * Accessible only by users with ROLE_USER.
     */
    @GetMapping("")
    public void validateUserRole(){

    }

    /**
     * Endpoint to fetch the store information associated with the authenticated vendor user.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param userDetails the authenticated user's details
     * @return ResponseEntity containing the store information or an error message
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @GetMapping(value = "/getStore", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateAndGetToken(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Authenticating user: {} is fetching token for sigin", userDetails.getUsername());
        try{
            return ResponseEntity.ok(service.getVendorStoreByUserName(userDetails.getUsername()));
        } catch (Exception exception){
            logger.error("Authentication failed for user: {}. Error: {}", userDetails.getUsername(), exception.getMessage());
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
