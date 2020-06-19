package com.example.cs4550preedylfinalserver.repository;

import com.example.cs4550preedylfinalserver.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    @Query("SELECT review FROM Review review")
    public List<Review> findAllReviews();

    @Query("SELECT review FROM Review review WHERE review.id=:reviewId")
    public Review findReviewById(@Param("reviewId") int reviewId);

    @Query("SELECT review FROM Review review WHERE review.writtenBy.username=:username")
    public List<Review> findAllReviewsByUser(@Param("username") String username);

    @Query("SELECT review FROM Review review WHERE review.reviewing.imdb=:imdbID")
    public List<Review> findAllReviewsOfMedia(@Param("imdbID") String imdbID);

    @Query("SELECT review FROM Review review WHERE review.writtenBy.username=:username AND review.reviewing.imdb=:imdbID")
    public List<Review> findAllReviewsOfMediaByUser(@Param("username") String username, @Param("imdbID") String imdbID);
}
