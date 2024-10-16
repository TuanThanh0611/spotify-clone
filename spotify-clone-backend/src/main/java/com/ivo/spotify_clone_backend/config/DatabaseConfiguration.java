package com.ivo.spotify_clone_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({ "com.ivo.spotify_clone_backend.repository",})
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration {
}