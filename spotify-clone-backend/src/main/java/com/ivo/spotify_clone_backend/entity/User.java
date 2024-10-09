package com.ivo.spotify_clone_backend.entity;

import com.ivo.spotify_clone_backend.enums.Subscription;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name="spotìy_user")
public class User extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_generator", allocationSize = 1)
    @Column(name = "id")
    Long id;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "email")
    String email;
    Subscription subscription=Subscription.FREE;
    @Column(name = "image_url")
    String imageUrl;
    @Override
    public Long getId() {
        return this.id;
    }


    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
