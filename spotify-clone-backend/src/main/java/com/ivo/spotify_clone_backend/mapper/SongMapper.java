package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.entity.Song;
import com.ivo.spotify_clone_backend.entity.records.SongAuthorRE;
import com.ivo.spotify_clone_backend.entity.records.SongTitleRE;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {


    Song saveSongToSong(SaveSong saveSongDTO);


    ReadSongInfo songToReadSongInfo(Song song);
    default SongTitleRE stringToSongTitleVO(String title){
        return new SongTitleRE(title);
    }

    default SongAuthorRE stringToSongAuthorVO(String author){
        return new SongAuthorRE(author);
    }

    default String songTitleVOToString(SongTitleRE title) {
        return title.value();
    }

    default String songAuthorVOToString(SongAuthorRE author) {
        return author.value();
    }


}