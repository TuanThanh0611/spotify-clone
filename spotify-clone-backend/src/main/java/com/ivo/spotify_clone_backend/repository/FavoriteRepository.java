package com.ivo.spotify_clone_backend.repository;

import com.ivo.spotify_clone_backend.entity.Favorite;
import com.ivo.spotify_clone_backend.entity.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findAllByUserEmailAndSongPublicIdIn(String email, List<UUID> songPublicIds);
}