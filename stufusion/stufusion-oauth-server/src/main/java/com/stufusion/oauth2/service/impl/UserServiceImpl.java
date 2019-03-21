package com.stufusion.oauth2.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.stufusion.email.client.commons.EmailType;
import com.stufusion.email.client.commons.SubjectType;
import com.stufusion.email.client.dto.EmailRequestData;
import com.stufusion.email.client.dto.EmailResponseData;
import com.stufusion.email.client.service.SendMailService;
import com.stufusion.oauth2.entity.User;
import com.stufusion.oauth2.entity.UserRoleType;
import com.stufusion.oauth2.exception.ApiBusinessException;
import com.stufusion.oauth2.exception.ApiSystemException;
import com.stufusion.oauth2.exception.Codes;
import com.stufusion.oauth2.exception.NotFoundException;
import com.stufusion.oauth2.exception.RequestValidationException;
import com.stufusion.oauth2.model.UserReq;
import com.stufusion.oauth2.model.UserResp;
import com.stufusion.oauth2.repository.UserRepository;
import com.stufusion.oauth2.service.TokenVerificationService;
import com.stufusion.oauth2.service.UserService;
import com.stufusion.oauth2.validation.EmailValidator;
import com.stufusion.oauth2.validation.PasswordValidator;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${user.resistration.confirmation.URL}")
    private String confirmationUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private TokenVerificationService tokenVerificationService;

    @Override
    public Long createUser(UserReq userReq) {

        validateParameters(userReq);

        if (userRepository.existsByEmail(userReq.getEmail())) {
            throw new ApiBusinessException(Codes.User.USER_ALREADY_EXITS,
                    "User already Exists with email " + userReq.getEmail());
        }

        User user = conversionService.convert(userReq, User.class);

        return userRepository.save(user).getId();
    }

    @Override
    public Long createUserWithEmailVerification(UserReq userReq) {
        Long userId = createUser(userReq);
        User user = userRepository.findOne(userId);
        String token = tokenVerificationService.create(user.getId());
        //sendConfirmationRegistrationEmail(user, token);
        return userId;
    }

    public List<UserResp> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().filter(Objects::nonNull).map(e -> conversionService.convert(e, UserResp.class))
                .collect(Collectors.toList());
    }

    public UserResp getUser(Long id) {
        if (id == null || id == 0) {
            throw new RequestValidationException("user Id is null or zero");
        }
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new NotFoundException("User for given id not found " + id);
        }

        return conversionService.convert(user, UserResp.class);
    }

    public UserResp getUser(String email) {
        if (email == null || email.isEmpty()) {
            throw new RequestValidationException("user email is null or empty");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User for given email not found " + email);
        }

        return conversionService.convert(user, UserResp.class);
    }

    /**
     * @param user
     * @param token
     */
    private void sendConfirmationRegistrationEmail(User user, String token) {

        String confirmationUrl = UriComponentsBuilder.fromHttpUrl(this.confirmationUrl).buildAndExpand(token)
                .toUriString();
        EmailRequestData emailRequestData = populateRequestParam(user, confirmationUrl);
        EmailResponseData emailResponseData = sendMailService.sendMail(emailRequestData);
        if (!emailResponseData.isStatus()) {
            throw new ApiSystemException(emailResponseData.getDescription());
        }
    }

    /**
     * @param user
     * @param confirmationUrl
     * @return
     */
    private EmailRequestData populateRequestParam(User user, String confirmationUrl) {
        EmailRequestData emailRequestData = new EmailRequestData();
        List<String> mailerList = new ArrayList<String>();
        mailerList.add(user.getEmail());
        emailRequestData.setSendEmailTo(mailerList);
        emailRequestData.setSubject(SubjectType.WELCOME_SUBJECT);
        Map<String, String> map = new HashMap<String, String>();
        map.put("FirstName", user.getFirstName());
        map.put("LastName", user.getLastName());
        map.put("ActivationURL", confirmationUrl);
        emailRequestData.setPlaceHolders(map);
        emailRequestData.setEmailType(EmailType.WELCOME_MAIL);
        return emailRequestData;
    }

    private void validateParameters(UserReq userReq) {
        if (userReq.getEmail() == null && EmailValidator.validate(userReq.getEmail()) || userReq.getPassword() == null
                || userReq.getRole() == null) {
            throw new RequestValidationException("User values details are invalid or null or empty");
        }

        if (!PasswordValidator.validate(userReq.getPassword())) {
            throw new RequestValidationException(Codes.User.INVALID_PASSWORD,
                    "Password must contain 1 Numeric, 1alphanumeric, 1 special character and atleast 8 digits");
        }

        try {
            UserRoleType.getRoleByName(userReq.getRole());
        } catch (ApiSystemException e) {
            throw new RequestValidationException("Invalid role name " + userReq.getRole());
        }

    }

    /**
     * @param grantedAuthorities
     * @return
     */
    public List<String> getPermission(Collection<GrantedAuthority> grantedAuthorities) {
        List<String> permissions = new ArrayList<String>();
        if (grantedAuthorities != null) {
            for (GrantedAuthority auth : grantedAuthorities) {
                permissions.add(auth.getAuthority());
            }
        }
        return permissions;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User [" + email + "] not found.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
    }

}
