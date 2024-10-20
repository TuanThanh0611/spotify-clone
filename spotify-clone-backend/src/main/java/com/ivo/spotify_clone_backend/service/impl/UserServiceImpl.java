package com.ivo.spotify_clone_backend.service.impl;

import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.entity.User;
import com.ivo.spotify_clone_backend.mapper.UserMapper;
import com.ivo.spotify_clone_backend.repository.UserRepository;
import com.ivo.spotify_clone_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper mapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.mapper = userMapper;
    }

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

    public void updateUser(User user) {
        Optional<User> userToUpdateOpt = userRepository.findUsersByEmail(user.getEmail());
        if (userToUpdateOpt.isPresent()) {
            User userToUpdate = userToUpdateOpt.get();
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setImageUrl(user.getImageUrl());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setFirstName(user.getFirstName());
            userRepository.saveAndFlush(userToUpdate);
        }
    }

    public User mapOauth2AttributesToUser(Map<String, Object> attributes) {
        User user = new User();
        String sub = String.valueOf(attributes.get("sub"));

        String username = null;

        if (attributes.get("preferred_username") != null) {
            username = ((String) attributes.get("preferred_username")).toLowerCase();
        }

        if (attributes.get("given_name") != null) {
            user.setFirstName((String) attributes.get("given_name"));
        } else if (attributes.get("name") != null) {
            user.setFirstName((String) attributes.get("name"));
        }

        if (attributes.get("family_name") != null) {
            user.setLastName((String) attributes.get("family_name"));
        }

        if (attributes.get("email") != null) {
            user.setEmail((String) attributes.get("email"));
        } else if (sub.contains("|") && (username != null && username.contains("@"))) {
            user.setEmail(username);
        } else {
            user.setEmail(sub);
        }

        if (attributes.get("picture") != null) {
            user.setImageUrl((String) attributes.get("picture"));
        }

        return user;
    }

    public Optional<ReadUser> getByEmail(String email) {
        Optional<User> oneByEmail = userRepository.findUsersByEmail(email);
        return oneByEmail.map(mapper::readUserDTOToUser);
    }

    public boolean isAuthenticated() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }


}
