package com.meetingapp.meetingapp.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.meetingapp.meetingapp.helpers.AuthenticationConstants;
import com.meetingapp.meetingapp.services.AuthorizationService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public String authorizeRequest(String subject) {
        return createToken(subject);
    }

    private String createToken(String subject) {
        String token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(AuthenticationConstants.SECRET.getBytes()));

        return AuthenticationConstants.TOKEN_PREFIX + token;
    }
}
