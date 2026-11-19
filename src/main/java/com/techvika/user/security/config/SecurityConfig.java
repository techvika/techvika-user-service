package com.techvika.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain basicAuthentication(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/**")
             //   .addFilterBefore(new CustomFilter(), BasicAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults()) // Username &Password
                .authorizeHttpRequests((authorize)
                        -> authorize.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public SecurityFilterChain oauth(HttpSecurity http) throws Exception {
        http.securityMatcher("/oauth/**")
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()) // Username &Password
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("techvika")
                .password(passwordEncoder().encode("$2a$12$9ES5zGwHCz2k51KBgdIlRu.6lEICkjcbKsuRuT/FgbbSkMZRqJKaq"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        //return new BCryptPasswordEncoder();
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
