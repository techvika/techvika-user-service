package com.techvika.user.service;

import com.techvika.user.dto.UserRequest;
import com.techvika.user.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    User createUser(UserRequest request);

    User updateUser(Long id, UserRequest request);

    User patchUser(Long id, Map<String, Object> updates);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);
}
