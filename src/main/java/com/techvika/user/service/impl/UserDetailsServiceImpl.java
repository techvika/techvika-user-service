package com.techvika.user.service.impl;

import com.techvika.user.entity.User;
import com.techvika.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User Details Not Found for the User : " + username));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                user.getRoles());
    }
}
