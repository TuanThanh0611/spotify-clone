package com.ivo.spotify_clone_backend.entity.records;


import jakarta.validation.constraints.NotBlank;

public record SongAuthorRE(@NotBlank String value) {
}