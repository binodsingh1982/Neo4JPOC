package com.stufusion.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stufusion.oauth2.entity.VerificationToken;

public interface PermissionRepository extends JpaRepository<VerificationToken, Long> {

}
