package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.entity.Song;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-19T21:31:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class SongMapperImpl implements SongMapper {

    @Override
    public Song saveSongDTOToSong(SaveSong saveSongDTO) {
        if ( saveSongDTO == null ) {
            return null;
        }

        Song song = new Song();

        return song;
    }

    @Override
    public ReadSongInfo songToReadSongInfoDTO(Song song) {
        if ( song == null ) {
            return null;
        }

        ReadSongInfo readSongInfo = new ReadSongInfo();

        return readSongInfo;
    }
}
