package com.techvika.user.config.security;

import com.techvika.user.component.AfterBasicAuthFilter;
import com.techvika.user.component.BeforeBasicAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain basicAuthentication(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/v1/**")
                .addFilterBefore(new BeforeBasicAuthFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AfterBasicAuthFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()) // Username &Password
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/**")
                                .hasAnyRole("ADMIN", "USER"));

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

   // @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("techvika")

                .password("{bcrypt}$2a$12$9ES5zGwHCz2k51KBgdIlRu.6lEICkjcbKsuRuT/FgbbSkMZRqJKaq")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
