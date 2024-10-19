package com.ivo.spotify_clone_backend.service;

import com.ivo.spotify_clone_backend.dto.request.FavoriteSongDTO;
import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.request.SongContentRequest;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.dto.response.State;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongService {
    ReadSongInfo create(SaveSong saveSong);
    List<ReadSongInfo> getAll();
    Optional<SongContentRequest> getOneByPublicId(UUID publicId);
    List<ReadSongInfo> search(String searchterm);
    State<FavoriteSongDTO,String> addOrRemoveFromFavorite(FavoriteSongDTO favoriteSongDTO, String email);
    List<ReadSongInfo> fetchFavoriteSongs(String email);
    List<ReadSongInfo> fetchFavoriteStatusForSongs(List<ReadSongInfo> songs);

}
