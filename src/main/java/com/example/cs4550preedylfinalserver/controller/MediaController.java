package com.example.cs4550preedylfinalserver.controller;

import com.example.cs4550preedylfinalserver.model.Media;
import com.example.cs4550preedylfinalserver.service.MediaService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MediaController {
    @Autowired
    MediaService service;

    @PostMapping("/api/media")
    public Media createMedia(@RequestBody Media newMedia) {
        return this.service.createMedia(newMedia);
    }

    @GetMapping("/api/media")
    public List<Media> findAllMedia() {
        return this.service.findAllMedia();
    }

    @GetMapping("/api/media/{mediaId}")
    public Media findMediaById(@PathVariable("mediaId") int mediaId) {
        return this.service.findMediaById(mediaId);
    }

    @GetMapping("/api/media/{imdbID}/imdb")
    public Media findMediaByImdbID(@PathVariable("imdbID") String imdbID) {
        return this.service.findMediaByImdbID(imdbID);
    }

    @GetMapping("/api/media/popular")
    public List<Media> getMostPopularMedia() {
        return service.getMostPopularMedia();
    }

    @GetMapping("/api/media/popular/{username}")
    public List<Media> getMostPopularMediaByFollowed(@PathVariable("username") String username) {
        return service.getMostPopularMediaByFollowed(username);
    }

    @DeleteMapping("/api/media/{mediaId}")
    public int deleteMediaById(@PathVariable("mediaId") int mediaId) {
        return this.service.deleteMediaById(mediaId);
    }

    @PutMapping("/api/media/{mediaId}")
    public int updateMedia(@PathVariable("mediaId") int mediaId,
                           @RequestBody Media updatedMedia) {
        return this.service.updateMedia(mediaId, updatedMedia);
    }
}
