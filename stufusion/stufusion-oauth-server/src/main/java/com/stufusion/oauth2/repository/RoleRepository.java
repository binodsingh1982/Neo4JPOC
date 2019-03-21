package com.stufusion.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stufusion.oauth2.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String Role);
}
