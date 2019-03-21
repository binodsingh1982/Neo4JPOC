package com.stufusion.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stufusion.oauth2.entity.User;
import com.stufusion.oauth2.entity.UserAuthProvider;

public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {


}
