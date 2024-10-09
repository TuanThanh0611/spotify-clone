package com.ivo.spotify_clone_backend.repository;

import com.ivo.spotify_clone_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUsersByEmail(String email);
}
