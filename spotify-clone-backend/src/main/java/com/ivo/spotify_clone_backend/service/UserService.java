package com.ivo.spotify_clone_backend.service;


import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.entity.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    void synchronizeUser(OAuth2User oAuth2User);
    ReadUser getAuthenticatedUser();
    void updateUser(User user);
    User mapOauth2AttributesToUser(Map<String, Object> attributes);
    Optional<ReadUser> getByEmail(String email);
    boolean isAuthenticated();


}
