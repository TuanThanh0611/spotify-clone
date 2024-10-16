package com.ivo.spotify_clone_backend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
public class FavoriteId implements Serializable {

    UUID songPublicId;

    String userEmail;

    public FavoriteId() {
    }

    public FavoriteId(UUID songPublicId, String userEmail) {
        this.songPublicId = songPublicId;
        this.userEmail = userEmail;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(songPublicId, that.songPublicId) && Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songPublicId, userEmail);
    }

}