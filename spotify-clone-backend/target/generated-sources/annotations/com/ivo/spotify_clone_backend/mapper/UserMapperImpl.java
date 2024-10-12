package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-10T14:58:51+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public ReadUser readUserDTOToUser(User entity) {
        if ( entity == null ) {
            return null;
        }

        ReadUser readUser = new ReadUser();

        return readUser;
    }
}
