package com.techvika.user.repository;

import com.techvika.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // find role by its name
    Optional<Role> findByName(String name);
}
