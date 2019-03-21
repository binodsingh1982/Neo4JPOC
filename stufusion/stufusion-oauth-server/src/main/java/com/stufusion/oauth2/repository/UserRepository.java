package com.stufusion.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stufusion.oauth2.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    
    User findByEmail(String email);

}
