package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.request.SongContentRequest;
import com.ivo.spotify_clone_backend.entity.SongContent;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-20T11:29:57+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class SongContentMapperImpl implements SongContentMapper {

    @Override
    public SongContentRequest songContentToSongContentRequest(SongContent songContent) {
        if ( songContent == null ) {
            return null;
        }

        SongContentRequest songContentRequest = new SongContentRequest();

        return songContentRequest;
    }

    @Override
    public SongContent saveSongToSong(SaveSong songDTO) {
        if ( songDTO == null ) {
            return null;
        }

        SongContent songContent = new SongContent();

        byte[] file = songDTO.file();
        if ( file != null ) {
            songContent.setFile( Arrays.copyOf( file, file.length ) );
        }
        songContent.setFileContentType( songDTO.fileContentType() );

        return songContent;
    }
}
