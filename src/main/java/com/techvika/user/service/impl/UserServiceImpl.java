package com.techvika.user.service.impl;

import com.techvika.user.dto.KycRequest;
import com.techvika.user.dto.UserRequest;
import com.techvika.user.dto.UserResponse;
import com.techvika.user.entity.KycStatus;
import com.techvika.user.entity.User;
import com.techvika.user.exception.EmailAlreadyExistsException;
import com.techvika.user.repository.UserRepository;
import com.techvika.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.techvika.user.mapping.UserMapping.toUserResponse;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public UserResponse  createUser(UserRequest request) {
        //Save User
        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .kycStatus(KycStatus.PENDING)
                .build();
      User saved = userRepository.save(user);

      // Send Kyc
      KycRequest kycRequest = new KycRequest(saved.getId(), request.getPanNumber());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<KycRequest> entity = new HttpEntity<>(kycRequest, headers);
        restTemplate.postForEntity(
              "http://localhost:8081/api/kychttp://localhost:8081/api/kyc",
              entity,
              Void.class);

        return toUserResponse(saved);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("User not found"));

        String newEmail = request.getEmail();
        if (newEmail != null && !newEmail.equals(user.getEmail())) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("Email already in use");
            }
            user.setEmail(newEmail);
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobile(request.getMobile());
        user.setAddress(request.getAddress());

        return userRepository.save(user);
    }


    @Override
    @Transactional
    public User patchUser(Long id, Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("User not found"));

        if (updates.containsKey("firstName")) {
            user.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            user.setLastName((String) updates.get("lastName"));
        }
        if (updates.containsKey("email")) {
            String email = (String) updates.get("email");
            if (email != null && !email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email already in use");
            }
            user.setEmail(email);
        }
        if (updates.containsKey("mobile")) {
            user.setMobile((String) updates.get("mobile"));
        }
        if (updates.containsKey("address")) {
            user.setAddress((String) updates.get("address"));
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new java.util.NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }
}
