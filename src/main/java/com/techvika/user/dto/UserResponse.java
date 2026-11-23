package com.techvika.user.dto;

import com.techvika.user.entity.KycStatus;
import com.techvika.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String address;
    private KycStatus kycStatus;
    private String panNumber;
    private Set<Role> roles;
}