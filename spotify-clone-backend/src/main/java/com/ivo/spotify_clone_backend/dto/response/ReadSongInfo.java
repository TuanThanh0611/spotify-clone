package com.ivo.spotify_clone_backend.dto.response;

import com.ivo.spotify_clone_backend.entity.records.SongAuthorRE;
import com.ivo.spotify_clone_backend.entity.records.SongTitleRE;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ReadSongInfo {
    SongTitleRE title;
    SongAuthorRE author;
    @NotNull
    byte[] cover;
    @NotNull
    String coverContentType;
    @NotNull
    boolean isFavorite;
    @NotNull
    UUID publicId;

    public SongTitleRE getTitle() {
        return title;
    }

    public void setTitle(SongTitleRE title) {
        this.title = title;
    }

    public SongAuthorRE getAuthor() {
        return author;
    }

    public void setAuthor(SongAuthorRE author) {
        this.author = author;
    }

    @NotNull
    public byte[] getCover() {
        return cover;
    }

    public void setCover(@NotNull byte[] cover) {
        this.cover = cover;
    }

    public @NotNull String getCoverContentType() {
        return coverContentType;
    }

    public void setCoverContentType(@NotNull String coverContentType) {
        this.coverContentType = coverContentType;
    }

    @NotNull
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(@NotNull boolean favorite) {
        isFavorite = favorite;
    }

    public @NotNull UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(@NotNull UUID publicId) {
        this.publicId = publicId;
    }
}
