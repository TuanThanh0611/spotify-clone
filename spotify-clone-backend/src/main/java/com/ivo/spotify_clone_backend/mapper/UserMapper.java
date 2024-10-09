package com.ivo.spotify_clone_backend.mapper;

import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUser readUserDTOToUser(User entity);

}