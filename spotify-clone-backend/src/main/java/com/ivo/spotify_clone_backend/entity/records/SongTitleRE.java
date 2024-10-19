package com.ivo.spotify_clone_backend.entity.records;

import jakarta.validation.constraints.NotBlank;

public record SongTitleRE(@NotBlank String value) {
}