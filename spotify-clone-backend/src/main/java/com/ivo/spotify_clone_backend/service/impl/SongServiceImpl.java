package com.ivo.spotify_clone_backend.service.impl;

import com.ivo.spotify_clone_backend.dto.request.FavoriteSongDTO;
import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.request.SongContentRequest;
import com.ivo.spotify_clone_backend.dto.request.StateBuilder;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.dto.response.State;
import com.ivo.spotify_clone_backend.entity.FavoriteId;
import com.ivo.spotify_clone_backend.entity.Song;
import com.ivo.spotify_clone_backend.entity.SongContent;
import com.ivo.spotify_clone_backend.mapper.SongContentMapper;
import com.ivo.spotify_clone_backend.mapper.SongMapper;
import com.ivo.spotify_clone_backend.repository.FavoriteRepository;
import com.ivo.spotify_clone_backend.repository.SongContentRepository;
import com.ivo.spotify_clone_backend.repository.SongRepository;
import com.ivo.spotify_clone_backend.service.SongService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ivo.spotify_clone_backend.entity.Favorite;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SongServiceImpl implements SongService {
    @Autowired
    SongRepository songRepository;
    @Autowired
    SongMapper mapper;
    @Autowired
    SongContentMapper songContentMapper;
    @Autowired
    SongContentRepository songContentRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    public SongServiceImpl( SongRepository songRepository,
                       SongContentRepository songContentRepository,
                           UserServiceImpl userService, FavoriteRepository favoriteRepository) {
        this.songRepository = songRepository;
        this.songContentRepository = songContentRepository;
        this.userService = userService;
        this.favoriteRepository = favoriteRepository;
    }

    public ReadSongInfo create(SaveSong saveSongDTO) {
        Song song = mapper.saveSongToSong(saveSongDTO);
        Song songSaved = songRepository.save(song);

        SongContent songContent = songContentMapper.saveSongToSong(saveSongDTO);
        songContent.setSong(songSaved);

        songContentRepository.save(songContent);
        return mapper.songToReadSongInfo(songSaved);
    }


    public List<ReadSongInfo> getAll() {
        // Tìm tất cả các bài hát từ cơ sở dữ liệu
        List<Song> songs = songRepository.findAll();

        // Kiểm tra danh sách không null và ánh xạ sang DTO
        List<ReadSongInfo> allSongs = songs.stream()
                .filter(Objects::nonNull) // Loại bỏ các giá trị null nếu có
                .map(song -> {
                    try {
                        return mapper.songToReadSongInfo(song);
                    } catch (Exception e) {
                        // Xử lý ngoại lệ ánh xạ nếu có
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Loại bỏ các kết quả null từ ánh xạ
                .toList();

        // Kiểm tra nếu người dùng đã xác thực thì lấy trạng thái yêu thích
        if (userService.isAuthenticated()) {
            return fetchFavoriteStatusForSongs(allSongs);
        }

        return allSongs;
    }

    public Optional<SongContentRequest> getOneByPublicId(UUID publicId) {
        Optional<SongContent> songByPublicId = songContentRepository.findOneBySongPublicId(publicId);
        return songByPublicId.map(songContentMapper::songContentToSongContentRequest);
    }

    public List<ReadSongInfo> search(String searchTerm) {
        List<ReadSongInfo> searchedSongs = songRepository.findByTitleOrAuthorContaining(searchTerm)
                .stream()
                .map(mapper::songToReadSongInfo)
                .collect(Collectors.toList());

        if(userService.isAuthenticated()) {
            return fetchFavoriteStatusForSongs(searchedSongs);
        } else {
            return searchedSongs;
        }
    }

    public State<FavoriteSongDTO, String> addOrRemoveFromFavorite(FavoriteSongDTO favoriteSongDTO, String email) {
        StateBuilder<FavoriteSongDTO, String> builder = State.builder();
        Optional<Song> songToLikeOpt = songRepository.findOneByPublicId(favoriteSongDTO.publicId());
        if (songToLikeOpt.isEmpty()) {
            return builder.forError("Song public id doesn't exist").build();
        }

        Song songToLike = songToLikeOpt.get();

        ReadUser userWhoLikedSong = userService.getByEmail(email).orElseThrow();

        if (favoriteSongDTO.favorite()) {
            Favorite favorite = new Favorite();
            favorite.setSongPublicId(songToLike.getPublicId());
            favorite.setUserEmail(userWhoLikedSong.email());
            favoriteRepository.save(favorite);
        } else {
            FavoriteId favoriteId = new FavoriteId(songToLike.getPublicId(), userWhoLikedSong.email());
            favoriteRepository.deleteById(favoriteId);
            favoriteSongDTO = new FavoriteSongDTO(false, songToLike.getPublicId());
        }

        return builder.forSuccess(favoriteSongDTO).build();
    }

    public List<ReadSongInfo> fetchFavoriteSongs(String email) {
        return songRepository.findAllFavoriteByUserEmail(email)
                .stream()
                .map(mapper::songToReadSongInfo)
                .toList();
    }



    public List<ReadSongInfo> fetchFavoriteStatusForSongs(List<ReadSongInfo> songs) {
        ReadUser authenticatedUser = userService.getAuthenticatedUser();

        List<UUID> songPublicIds = songs.stream().map(ReadSongInfo::getPublicId).toList();

        List<UUID> userFavoriteSongs = favoriteRepository.findAllByUserEmailAndSongPublicIdIn(authenticatedUser.email(), songPublicIds)
                .stream().map(Favorite::getSongPublicId).toList();

        return songs.stream().peek(song -> {
            if (userFavoriteSongs.contains(song.getPublicId())) {
                song.setFavorite(true);
            }
        }).toList();
    }
}