package com.techvika.user.mapping;

import com.techvika.user.dto.UserResponse;
import com.techvika.user.entity.User;

public class UserMapping {

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .address(user.getAddress())
                .kycStatus(user.getKycStatus())
                .build();
    }
}