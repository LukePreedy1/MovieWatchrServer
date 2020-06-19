package com.example.cs4550preedylfinalserver.service;

import com.example.cs4550preedylfinalserver.model.Media;
import com.example.cs4550preedylfinalserver.model.User;
import com.example.cs4550preedylfinalserver.repository.MediaRepository;
import com.example.cs4550preedylfinalserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MediaService {
    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    UserRepository userRepository;

    public Media createMedia(Media newMedia) {
        return this.mediaRepository.save(newMedia);
    }

    public List<Media> findAllMedia() {
        return this.mediaRepository.findAllMedia();
    }

    public Media findMediaById(int mediaId) {
        return this.mediaRepository.findMediaById(mediaId);
    }

    public Media findMediaByImdbID(String imdbID) {
        return this.mediaRepository.findMediaByImdbID(imdbID);
    }

    // Limits it to the 10 most popular media.
    public List<Media> getMostPopularMedia() {
        List<Media> result = this.mediaRepository.findAllMedia();
        result.sort(new SortByMostPopular());
        try {
            return result.subList(0, 10);
        } catch(IndexOutOfBoundsException e) {
            return result;
        }
    }

    public List<Media> getMostPopularMediaByFollowed(String username) {
        User u = userRepository.findUserByUsername(username);
        List<Media> result = new ArrayList<>();
        for (User user : u.getFollows())
            for (Media m : user.getWatched())
                if (!result.contains(m))
                    result.add(m);
        result.sort(new SortByMostPopular());
        try {
            return result.subList(0, 10);
        } catch(IndexOutOfBoundsException e) {
            return result;
        }
    }

    public int deleteMediaById(int mediaId) {
        Media m = this.findMediaById(mediaId);
        this.mediaRepository.delete(m);
        return 1;
    }

    public int updateMedia(int mediaId, Media updatedMedia) {
        updatedMedia.setId(mediaId);
        this.mediaRepository.save(updatedMedia);
        return 1;
    }
}

class SortByMostPopular implements Comparator<Media> {
    @Override
    public int compare(Media m1, Media m2) {
        return Integer.compare(m2.getWatchedBy().size(), m1.getWatchedBy().size());
    }
}
