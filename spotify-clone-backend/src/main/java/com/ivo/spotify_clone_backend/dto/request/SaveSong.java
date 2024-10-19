package com.ivo.spotify_clone_backend.dto.request;

import com.ivo.spotify_clone_backend.entity.records.SongAuthorRE;
import com.ivo.spotify_clone_backend.entity.records.SongTitleRE;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SaveSong(@Valid SongTitleRE title,
                          @Valid SongAuthorRE author,
                          @NotNull byte[] cover,
                          @NotNull String coverContentType,
                          @NotNull byte[] file,
                          @NotNull String fileContentType) {
}