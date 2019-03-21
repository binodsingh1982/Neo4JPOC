package com.stufusion.oauth2.service.impl;

import java.util.Calendar;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stufusion.oauth2.entity.User;
import com.stufusion.oauth2.entity.VerificationToken;
import com.stufusion.oauth2.exception.ApiBusinessException;
import com.stufusion.oauth2.exception.Codes;
import com.stufusion.oauth2.exception.RequestValidationException;
import com.stufusion.oauth2.repository.TokenVerificationRepository;
import com.stufusion.oauth2.repository.UserRepository;
import com.stufusion.oauth2.service.TokenVerificationService;

@Service
@Transactional
public class TokenVerificationServiceImpl implements TokenVerificationService {

    @Value("${verificationEmail.expiryTimeInMinutes}")
    private Integer expiryInMin;

    @Autowired
    private TokenVerificationRepository tokenVerificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String create(@NotNull Long userId) {

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(userRepository.findOne(userId));
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(expiryInMin));
        tokenVerificationRepository.save(verificationToken);
        return token;
    }

    @Override
    public boolean verifyToken(@NotNull String token) {
        VerificationToken verificationToken = tokenVerificationRepository.findByToken(token);
        if (verificationToken == null) {
            throw new RequestValidationException("Verification token doesn't exist.");
        }

        if (verificationToken.getExpiryDate() != null) {
            Calendar cal = Calendar.getInstance();
            if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                throw new ApiBusinessException(Codes.Token.TOKEN_EXPIRED, "Verification token has expired ");
            }
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }

}
