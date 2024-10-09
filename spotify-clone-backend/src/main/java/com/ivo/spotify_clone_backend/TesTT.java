package com.ivo.spotify_clone_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TesTT {

    @Autowired
    public TesTT(Dotenv dotenv) {
        String auth0ClientId = dotenv.get("AUTH0_CLIENT_ID");
        System.out.println("AUTH0_CLIENT_ID: " + auth0ClientId);  // Check if the value is loaded correctly
    }
}