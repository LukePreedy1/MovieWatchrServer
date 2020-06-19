package com.example.cs4550preedylfinalserver.repository;

import com.example.cs4550preedylfinalserver.model.Media;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends CrudRepository<Media, Integer> {
    @Query("SELECT media FROM Media media")
    public List<Media> findAllMedia();

    @Query("SELECT media FROM Media media WHERE media.id=:mediaId")
    public Media findMediaById(@Param("mediaId") int mediaId);

    @Query("SELECT media FROM Media media WHERE media.imdb=:imdb")
    public Media findMediaByImdbID(@Param("imdb") String imdbID);
}
