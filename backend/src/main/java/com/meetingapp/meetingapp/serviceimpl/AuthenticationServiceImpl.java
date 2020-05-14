package com.meetingapp.meetingapp.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.meetingapp.meetingapp.helpers.AuthenticationConstants;
import com.meetingapp.meetingapp.services.AuthenticationService;
import com.meetingapp.meetingapp.services.AuthorizationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private AuthorizationService authorizationService;
    private static final Logger LOGGER = Logger.getLogger( AuthenticationServiceImpl.class.getName() );

    public AuthenticationServiceImpl(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public String authenticateLogin(String username, String password) {
        Properties meetingAppProps = getProperties();

        if(validateCredentials(username, password, meetingAppProps)) return authorizationService.authorizeRequest(username);
        else return null;
    }

    @Override
    public String authenticateAssignRoomRequest(String email) {
        return authorizationService.authorizeRequest(email);
    }

    @Override
    public boolean validateAdminToken(String jwt) {
        Properties meetingAppProperties = getProperties();
        String tokenSubject = getTokenSubject(jwt);

        return validateAdminUsername(tokenSubject, meetingAppProperties);
    }

    @Override
    public boolean validateRoomToken(String jwt, String email) {
        String tokenSubject = getTokenSubject(jwt);

        return email != null && email.equals(tokenSubject);
    }

    private Properties getProperties() {
        Properties meetingAppProps = new Properties();
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(AuthenticationConstants.PROP_FILE_NAME);
            meetingAppProps.load(is);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING,  "FileNotFound", ex);
        }

        return meetingAppProps;
    }

    private boolean validateAdminUsername(String username, Properties meetingAppProps){
        return meetingAppProps.getProperty(AuthenticationConstants.USERNAME_FIELD_NAME).equals(username);
    }

    private boolean validateCredentials(String username, String password, Properties meetingAppProps) {
        return meetingAppProps.getProperty(AuthenticationConstants.USERNAME_FIELD_NAME).equals(username) && meetingAppProps.getProperty(AuthenticationConstants.PASSWORD_FIELD_NAME).equals(password);
    }

    @Override
    public String getTokenSubject(String jwt){
        if(jwt == null) return null;

        return JWT.require(Algorithm.HMAC512(AuthenticationConstants.SECRET.getBytes()))
                .build()
                .verify(jwt.replace(AuthenticationConstants.TOKEN_PREFIX, ""))
                .getSubject();
    }

}
