package com.ivo.spotify_clone_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "favorite_song")
@IdClass(FavoriteId.class)
public class Favorite implements Serializable {

    @Id
    private UUID songPublicId;

    @Id
    @Column(name = "user_email")
    private String userEmail;

}