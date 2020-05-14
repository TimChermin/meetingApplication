package com.meetingapp.meetingapp.services;

public interface AuthenticationService {
    /***
     * Authenticates login request
     * @param username : filled in username
     * @param password : filled in password
     * @return : when successful, returns JWT
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException when credentials not correct
     */
    String authenticateLogin(String username, String password);

    /***
     * Authenticates assign room request
     * @param email : room email
     * @return : when successful, returns JWT containing email and name
     */
    String authenticateAssignRoomRequest(String email);

    boolean validateAdminToken(String jwt);
    boolean validateRoomToken(String jwt, String email);

    String getTokenSubject(String jwt);
}
