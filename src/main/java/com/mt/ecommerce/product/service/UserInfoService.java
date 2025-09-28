package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.UserInfo;
import com.mt.ecommerce.product.entity.UserVendor;
import com.mt.ecommerce.product.entity.Vendor;
import com.mt.ecommerce.product.exception.MTException;
import com.mt.ecommerce.product.mapper.UserMapper;
import com.mt.ecommerce.product.mapper.VendorMapper;
import com.mt.ecommerce.product.model.Store;
import com.mt.ecommerce.product.repository.UserInfoRepository;
import com.mt.ecommerce.product.repository.UserVendorRepository;
import com.mt.ecommerce.product.repository.VendorRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing user information and authentication.
 * Implements UserDetailsService for Spring Security integration.
 */
@Service
public class UserInfoService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    private final VendorRepository vendorRepository;

    private final UserVendorRepository userVendorRepository;

    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder, VendorRepository vendorRepository, UserVendorRepository userVendorRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.vendorRepository = vendorRepository;
        this.userVendorRepository = userVendorRepository;
    }

    // Method to load user details by username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<UserInfo> userInfo = repository.findByEmail(username);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Convert UserInfo to UserDetails (UserInfoDetails)
        UserInfo user = userInfo.get();
        UserInfoDetails userInfoDetails = new UserInfoDetails(user);
        return new User(userInfoDetails.getUsername(), userInfoDetails.getPassword(), userInfoDetails.getAuthorities());
    }

    /**
     * Adds a new user to the system.
     *
     * @param userInfo the user information to add
     * @return the added UserInfo
     */
    public UserInfo addUser(UserInfo userInfo) {
        Optional<UserInfo> existingUser = repository.findByEmail(userInfo.getEmail());
        if(existingUser.isPresent()){
            throw new MTException("User Already exits");
        }
        userInfo.setRoles("ROLE_USER");
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }

    /**
     * Adds a vendor user to the system.
     *
     * @param store the store information containing user and vendor details
     * @return the added Store with user and vendor information
     */
    @Transactional
    public Store addVendorUser(Store store) {
        Optional<UserInfo> existingUser = repository.findByEmail(store.getUserInfo().getEmail());
        UserInfo userInfo = new UserMapper().mapDAO(store.getUserInfo());
        if (existingUser.isEmpty()) {
            logger.info("User Not exits will create new user {}", userInfo.getEmail());
            userInfo.setRoles("ROLE_VENDOR, ROLE_USER");
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
            userInfo = repository.save(userInfo);
        } else {
            userInfo = existingUser.get();
            userInfo.setRoles("ROLE_VENDOR, ROLE_USER");
            userInfo = repository.save(userInfo);
            logger.info("Have updated Vendor roles to User {}", userInfo.getEmail());
        }
        Vendor vendor = new VendorMapper().mapDao(store.getVendors().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Missing vendor information")));
        vendor = vendorRepository.save(vendor);
        store.setUserInfo(new UserMapper().mapBO(userInfo));
        UserVendor userVendor = new UserVendor();
        userVendor.setVendor(vendor);
        userVendor.setUserInfo(userInfo);
        this.userVendorRepository.save(userVendor);
        logger.info("Have Created Vendor Profile to User {} with vendor id {}", userInfo.getEmail(), vendor.getVendorId());
        store.setVendors(Collections.singletonList(new VendorMapper().mapBo(vendor)));
        return store;
    }

    /**
     * Retrieves the store information associated with a vendor user by their username.
     *
     * @param user the username of the vendor user
     * @return the Store information containing user and vendor details
     */
    public Store getVendorStoreByUserName(String user) {
        UserInfo userInfo = this.repository.findByEmail(user).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Store store = new Store();
        store.setUserInfo(new UserMapper().mapBO(userInfo));
        store.setVendors(userInfo.getUserVendors().stream().map(userVendor -> new VendorMapper().mapBo(userVendor.getVendor())).collect(Collectors.toList()));
        return store;
    }

    /**
     * Retrieves user information by their username (email).
     *
     * @param userName the username (email) of the user
     * @return the UserInfo associated with the given username
     */
    public UserInfo getUser(String userName){
        return this.repository.findByEmail(userName).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

