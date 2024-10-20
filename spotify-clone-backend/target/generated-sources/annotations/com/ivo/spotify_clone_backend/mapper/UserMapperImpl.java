package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-20T11:29:57+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public ReadUser readUserDTOToUser(User entity) {
        if ( entity == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String email = null;
        String imageUrl = null;

        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        imageUrl = entity.getImageUrl();

        ReadUser readUser = new ReadUser( firstName, lastName, email, imageUrl );

        return readUser;
    }
}
