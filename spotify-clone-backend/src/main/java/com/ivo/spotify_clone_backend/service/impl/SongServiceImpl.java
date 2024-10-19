package com.ivo.spotify_clone_backend.service.impl;

import com.ivo.spotify_clone_backend.dto.request.FavoriteSongDTO;
import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.request.SongContentRequest;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.dto.response.State;
import com.ivo.spotify_clone_backend.entity.Song;
import com.ivo.spotify_clone_backend.entity.SongContent;
import com.ivo.spotify_clone_backend.repository.FavoriteRepository;
import com.ivo.spotify_clone_backend.repository.SongContentRepository;
import com.ivo.spotify_clone_backend.repository.SongRepository;
import com.ivo.spotify_clone_backend.service.SongService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SongServiceImpl implements SongService {
    SongRepository songRepository;
    SongContentRepository songContentRepository;
    UserServiceImpl userService;
    FavoriteRepository favoriteRepository;
    public SongServiceImpl( SongRepository songRepository,
                       SongContentRepository songContentRepository,
                           UserServiceImpl userService, FavoriteRepository favoriteRepository) {
        this.songRepository = songRepository;
        this.songContentRepository = songContentRepository;
        this.userService = userService;
        this.favoriteRepository = favoriteRepository;
    }


    @Override
    public ReadSongInfo create(SaveSong saveSong) {
        return null;
    }

    @Override
    public List<ReadSongInfo> getAll() {
        return List.of();
    }

    @Override
    public Optional<SongContentRequest> getOneByPublicId(UUID publicId) {
        return Optional.empty();
    }

    @Override
    public List<ReadSongInfo> search(String searchterm) {
        return List.of();
    }

    @Override
    public State<FavoriteSongDTO, String> addOrRemoveFromFavorite(FavoriteSongDTO favoriteSongDTO, String email) {
        return null;
    }

    @Override
    public List<ReadSongInfo> fetchFavoriteSongs(String email) {
        return List.of();
    }

    @Override
    public List<ReadSongInfo> fetchFavoriteStatusForSongs(List<ReadSongInfo> songs) {
        return List.of();
    }
}
