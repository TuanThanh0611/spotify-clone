package com.ivo.spotify_clone_backend.controler;

import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.repository.UserRepository;
import com.ivo.spotify_clone_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AuthController {
    UserService userService;
    ClientRegistration registration;
    public AuthController(UserService userService, ClientRegistrationRepository registrations) {
        this.userService = userService;
        this.registration = registrations.findByRegistrationId("okta");
    }
    @GetMapping("/get-authenticated-user")
    public ResponseEntity<ReadUser> getAuthenticatedUser(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            userService.synchronizeUser(user);
            ReadUser userFromAuthentication = userService.getAuthenticatedUser();
            return ResponseEntity.ok().body(userFromAuthentication);
        }
    }
}
