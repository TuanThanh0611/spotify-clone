package com.ivo.spotify_clone_backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Table(name = "song")
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "songSequenceGenerator")
    @SequenceGenerator(name = "songSequenceGenerator", sequenceName = "song_generator", allocationSize = 1)
    @Column(name = "id")
    Long id;

    @UuidGenerator
    @Column(name = "public_id", nullable = false)
    UUID publicId;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "author", nullable = false)
    String author;

    @Lob
    @Column(name = "cover", nullable = false)
    byte[] cover;

    @Column(name = "cover_content_type", nullable = false)
    String coverContentType;
}