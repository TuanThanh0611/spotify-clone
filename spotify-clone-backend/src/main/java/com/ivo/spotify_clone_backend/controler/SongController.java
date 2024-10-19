package com.ivo.spotify_clone_backend.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivo.spotify_clone_backend.dto.request.SaveSong;
import com.ivo.spotify_clone_backend.dto.response.ReadSongInfo;
import com.ivo.spotify_clone_backend.service.SongService;
import com.ivo.spotify_clone_backend.service.UserService;
import com.ivo.spotify_clone_backend.service.impl.SongServiceImpl;
import com.ivo.spotify_clone_backend.service.impl.UserServiceImpl;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Validator;
import java.io.IOException;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SongController {
    SongServiceImpl songService;
    Validator validator;
    UserServiceImpl userService;
    ObjectMapper objectMapper=new ObjectMapper();



    public SongController(SongServiceImpl songService, Validator validator, UserServiceImpl userService) {
        this.songService = songService;
        this.validator = validator;
        this.userService = userService;
    }


    @PostMapping(value = "/songs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReadSongInfo> add(@RequestPart(name = "cover") MultipartFile cover,
                                               @RequestPart(name = "file") MultipartFile file,
                                               @RequestPart(name = "dto") String saveSongString) throws IOException {
        SaveSong saveSong = objectMapper.readValue(saveSongString, SaveSong.class);
        saveSong = new SaveSong(saveSong.title(), saveSong.author(),
                cover.getBytes(), cover.getContentType(), file.getBytes(), file.getContentType());

        Set<ConstraintViolation<SaveSong>> violations = validator.validate(saveSong);
        if (!violations.isEmpty()) {
            String violationsJoined = violations
                    .stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .collect(Collectors.joining());
            ProblemDetail validationIssue = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    "Validation errors for the fields : " + violationsJoined);
            return ResponseEntity.of(validationIssue).build();
        } else {
            return ResponseEntity.ok(songService.create(saveSong));
        }
    }




}
