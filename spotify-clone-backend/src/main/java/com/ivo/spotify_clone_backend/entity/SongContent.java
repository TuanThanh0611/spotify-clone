package com.ivo.spotify_clone_backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "song_content")
public class SongContent implements Serializable {

    @Id
    @Column(name = "song_id")
    Long songId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    Song song;

    @Lob
    @Column(name = "file", nullable = false)
    byte[] file;

    @Column(name = "file_content_type")
    String fileContentType;
}