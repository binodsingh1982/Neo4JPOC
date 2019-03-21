package com.stufusion.oauth2.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.stufusion.oauth2.entity.Role;
import com.stufusion.oauth2.entity.User;
import com.stufusion.oauth2.entity.UserRoleType;
import com.stufusion.oauth2.model.UserReq;
import com.stufusion.oauth2.repository.RoleRepository;

@Component
public class UserReqToUserConverter implements Converter<UserReq, User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User convert(UserReq userReq) {
        User user = new User();
        user.setEmail(userReq.getEmail());
        user.setFirstName(userReq.getFirstName());
        user.setLastName(userReq.getLastName());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));

        Role role = roleRepository.findByRoleName(UserRoleType.getRoleByName(userReq.getRole()).getRole());
        user.addRole(role);

        return user;
    }

}
