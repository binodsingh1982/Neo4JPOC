package com.stufusion.oauth2.converter;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.stufusion.oauth2.entity.User;
import com.stufusion.oauth2.model.UserResp;

@Component
public class UserToUserRespConverter implements Converter<User, UserResp> {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public UserResp convert(User user) {
        Map<Long, String> permissions = user.getPermissions().stream().filter(Objects::nonNull)
                .collect(Collectors.toMap((p) -> p.getId(), (p) -> p.getPermissionname(), (v1, v2) -> v1 + "," + v2));

        Map<Long, String> roles = user.getRoles().stream().filter(Objects::nonNull)
                .collect(Collectors.toMap((r) -> r.getId(), (r) -> r.getRoleName()));

        Map<String, String> authProviders = user.getUserAuthProviders().stream().filter(Objects::nonNull)
                .collect(Collectors.toMap((a) -> a.getAuthProviderType().getName(), a -> a.getAuthProviderId()));

        UserResp userResp = new UserResp();
        userResp.setEmail(user.getEmail());
        userResp.setEnabled(user.getEnabled());
        userResp.setFirstName(user.getFirstName());
        userResp.setId(user.getId());
        userResp.setLastName(user.getLastName());
        userResp.setPermissions(permissions);
        userResp.setRoles(roles);
        userResp.setToken(user.getVerificationToken() != null ? user.getVerificationToken().getToken() : null);
        userResp.setTokenExpiry(user.getVerificationToken() != null
                ? formatter.format(user.getVerificationToken().getExpiryDate()) : null);
        userResp.setUserId(user.getUserId());
        userResp.setUserType(user.getUserType());
        userResp.setAuthProviders(authProviders);
        return userResp;
    }

}
