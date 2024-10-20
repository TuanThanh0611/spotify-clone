package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.entity.Song;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-20T11:29:57+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class SongMapperImpl implements SongMapper {

    @Override
    public Song saveSongToSong(SaveSong saveSongDTO) {
        if ( saveSongDTO == null ) {
            return null;
        }

        Song song = new Song();

        song.setTitle( songTitleVOToString( saveSongDTO.title() ) );
        song.setAuthor( songAuthorVOToString( saveSongDTO.author() ) );
        byte[] cover = saveSongDTO.cover();
        if ( cover != null ) {
            song.setCover( Arrays.copyOf( cover, cover.length ) );
        }
        song.setCoverContentType( saveSongDTO.coverContentType() );

        return song;
    }

    @Override
    public ReadSongInfo songToReadSongInfo(Song song) {
        if ( song == null ) {
            return null;
        }

        ReadSongInfo readSongInfo = new ReadSongInfo();

        readSongInfo.setTitle( stringToSongTitleVO( song.getTitle() ) );
        readSongInfo.setAuthor( stringToSongAuthorVO( song.getAuthor() ) );
        byte[] cover = song.getCover();
        if ( cover != null ) {
            readSongInfo.setCover( Arrays.copyOf( cover, cover.length ) );
        }
        readSongInfo.setCoverContentType( song.getCoverContentType() );
        readSongInfo.setPublicId( song.getPublicId() );

        return readSongInfo;
    }
}
