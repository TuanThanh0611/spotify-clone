package com.ivo.spotify_clone_backend.service.impl;

import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.entity.User;
import com.ivo.spotify_clone_backend.mapper.UserMapper;
import com.ivo.spotify_clone_backend.repository.UserRepository;
import com.ivo.spotify_clone_backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper mapper;

    @Override
    public void synchronizeUser(OAuth2User oAuth2User) {
        Map<String,Object> attributes=oAuth2User.getAttributes();
        User user=mapOauth2AttributesToUser(attributes);
        Optional<User> existingUser=userRepository.findUsersByEmail(user.getEmail());
        if(existingUser.isPresent()){
            if (attributes.get("updated_at") != null) {
                Instant dbLastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate;
                if(attributes.get("updated_at") instanceof Instant) {
                    idpModifiedDate = (Instant) attributes.get("updated_at");
                } else {
                    idpModifiedDate = Instant.ofEpochSecond((Integer) attributes.get("updated_at"));
                }
                if(idpModifiedDate.isAfter(dbLastModifiedDate)) {
                    updateUser(user);
                }

        }

    }}

    @Override
    public ReadUser getAuthenticatedUser() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = mapOauth2AttributesToUser(principal.getAttributes());
        return mapper.readUserDTOToUser(user);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User mapOauth2AttributesToUser(Map<String, Object> attributes) {
        return null;
    }

    @Override
    public Optional<ReadUser> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }


}
