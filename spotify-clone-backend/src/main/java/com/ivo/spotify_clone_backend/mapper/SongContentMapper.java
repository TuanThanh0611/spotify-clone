package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.request.SongContentRequest;
import com.ivo.spotify_clone_backend.entity.SongContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongContentMapper {


    SongContentRequest songContentToSongContentRequest(SongContent songContent);

    SongContent saveSongToSong(SaveSong songDTO);
}