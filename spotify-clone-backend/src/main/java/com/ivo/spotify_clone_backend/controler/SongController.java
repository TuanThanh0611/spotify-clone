package com.ivo.spotify_clone_backend.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivo.spotify_clone_backend.dto.request.FavoriteSongDTO;
import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.request.SongContentRequest;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.dto.response.ReadUser;
import com.ivo.spotify_clone_backend.dto.response.State;
import com.ivo.spotify_clone_backend.entity.SongContent;
import com.ivo.spotify_clone_backend.enums.StatusNotification;
import com.ivo.spotify_clone_backend.service.SongService;
import com.ivo.spotify_clone_backend.service.UserService;
import com.ivo.spotify_clone_backend.service.impl.SongServiceImpl;
import com.ivo.spotify_clone_backend.service.impl.UserServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SongController {

    SongServiceImpl songService;

    Validator validator;

    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SongController(SongServiceImpl songService, Validator validator, UserService userService) {
        this.songService = songService;
        this.validator = validator;
        this.userService = userService;
    }

    @PostMapping(value = "/songs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReadSongInfo> add(@RequestPart(name = "cover") MultipartFile cover,
                                               @RequestPart(name = "file") MultipartFile file,
                                               @RequestPart(name = "dto") String saveSongDTOString) throws IOException {
        SaveSong saveSongDTO = objectMapper.readValue(saveSongDTOString, SaveSong.class);
        saveSongDTO = new SaveSong(saveSongDTO.title(), saveSongDTO.author(),
                cover.getBytes(), cover.getContentType(), file.getBytes(), file.getContentType());

        Set<ConstraintViolation<SaveSong>> violations = validator.validate(saveSongDTO);
        if (!violations.isEmpty()) {
            String violationsJoined = violations
                    .stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .collect(Collectors.joining());
            ProblemDetail validationIssue = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    "Validation errors for the fields : " + violationsJoined);
            return ResponseEntity.of(validationIssue).build();
        } else {
            return ResponseEntity.ok(songService.create(saveSongDTO));
        }
    }

    @GetMapping("/songs")
    public ResponseEntity<List<ReadSongInfo>> getAll() {
        return ResponseEntity.ok(songService.getAll());
    }

    @GetMapping("/songs/get-content")
    public ResponseEntity<SongContentRequest> getOneByPublicId(@RequestParam UUID publicId) {
        Optional<SongContentRequest> songContentByPublicId = songService.getOneByPublicId(publicId);
        return songContentByPublicId.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "UUID Unknown")).build());
    }

    @GetMapping("/songs/search")
    public ResponseEntity<List<ReadSongInfo>> search(@RequestParam String term) {
        return ResponseEntity.ok(songService.search(term));
    }

    @PostMapping("/songs/like")
    public ResponseEntity<FavoriteSongDTO> addOrRemoveFromFavorite(@Valid @RequestBody FavoriteSongDTO favoriteSongDTO) {
        ReadUser userFromAuthentication = userService.getAuthenticatedUser();
        State<FavoriteSongDTO, String> favoriteSongResponse = songService.addOrRemoveFromFavorite(favoriteSongDTO, userFromAuthentication.email());

        if(favoriteSongResponse.getStatus().equals(StatusNotification.ERROR)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, favoriteSongResponse.getError());
            return ResponseEntity.of(problemDetail).build();
        } else {
            return ResponseEntity.ok(favoriteSongResponse.getValue());
        }
    }

    @GetMapping("/songs/like")
    public ResponseEntity<List<ReadSongInfo>> fetchFavoriteSongs() {
        ReadUser userFromAuthentication = userService.getAuthenticatedUser();
        return ResponseEntity.ok(songService.fetchFavoriteSongs(userFromAuthentication.email()));
    }
}