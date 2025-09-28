package com.mt.ecommerce.product.mapper;

import com.mt.ecommerce.product.entity.UserInfo;
import com.mt.ecommerce.product.model.UserInfoBO;

/** * Mapper class for converting between UserInfo entity and UserInfoBO model.
 */
public class UserMapper {


    /**     * Maps a UserInfoBO object to a UserInfo entity.
      *
      * @param userInfoBO the UserInfoBO object to be mapped
      * @return the mapped UserInfo entity
      */
    public UserInfo mapDAO(UserInfoBO userInfoBO){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoBO.getId());
        userInfo.setName(userInfoBO.getName());
        userInfo.setEmail(userInfoBO.getEmail());
        userInfo.setPassword(userInfoBO.getPassword());
        userInfo.setRoles(userInfoBO.getRoles());
        userInfo.setPhone(userInfoBO.getPhone());
        userInfo.setAddressLine1(userInfoBO.getAddressLine1());
        userInfo.setAddressLine2(userInfoBO.getAddressLine2());
        userInfo.setCity(userInfoBO.getCity());
        userInfo.setState(userInfoBO.getState());
        userInfo.setCountry(userInfoBO.getCountry());
        return userInfo;
    }

    /**     * Maps a UserInfo entity to a UserInfoBO object.
      *
      * @param userInfo the UserInfo entity to be mapped
      * @return the mapped UserInfoBO object
      */
    public UserInfoBO mapBO(UserInfo userInfo){
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setId(userInfo.getId());
        userInfoBO.setName(userInfo.getName());
        userInfoBO.setEmail(userInfo.getEmail());
        userInfoBO.setPassword(userInfo.getPassword());
        userInfoBO.setRoles(userInfo.getRoles());
        userInfoBO.setPhone(userInfo.getPhone());
        userInfoBO.setAddressLine1(userInfo.getAddressLine1());
        userInfoBO.setAddressLine2(userInfo.getAddressLine2());
        userInfoBO.setCity(userInfo.getCity());
        userInfoBO.setState(userInfo.getState());
        userInfoBO.setCountry(userInfo.getCountry());
        return userInfoBO;
    }
}
