package com.example.cs4550preedylfinalserver.controller;

import com.example.cs4550preedylfinalserver.model.Review;
import com.example.cs4550preedylfinalserver.model.User;
import com.example.cs4550preedylfinalserver.service.ReviewService;
import com.example.cs4550preedylfinalserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ReviewController {
    @Autowired
    ReviewService service;

    @Autowired
    UserService userService;

    @PostMapping("/api/users/{username}/media/{imdbID}/reviews")
    public Review CreateReview(@PathVariable("username") String username,
                               @PathVariable("imdbID") String imdbID,
                               @RequestBody Review review) {

        Review r = this.service.createReview(username, imdbID, review);
        try {
            userService.removeFromWatchlist(username, imdbID);
            userService.addToWatched(username, imdbID);
        } catch(Exception e) {}
        return r;
    }

    @PutMapping("/api/users/{username}/media/{imdbID}/reviews/{reviewId}")
    public int UpdateReview(@PathVariable("username") String username,
                            @PathVariable("imdbID") String imdbID,
                            @PathVariable("reviewId") int reviewId,
                            @RequestBody Review review) {
        return this.service.updateReview(username, imdbID, review, reviewId);
    }

    @PutMapping("/api/reviews/{reviewId}/like/{username}")
    public int LikeReview(@PathVariable("reviewId") int reviewId,
                          @PathVariable("username") String username) {
        return this.service.likeReview(reviewId, username);
    }

    @PutMapping("/api/reviews/{reviewId}/unLike/{username}")
    public int unLikeReview(@PathVariable("reviewId") int reviewId,
                            @PathVariable("username") String username){
        return this.service.unLikeReview(reviewId, username);
    }

    @DeleteMapping("/api/reviews/{reviewId}")
    public int DeleteReview(@PathVariable("reviewId") int reviewId) {
        return this.service.deleteReview(reviewId);
    }

    @GetMapping("/api/reviews")
    public List<Review> FindAllReviews() {
        return this.service.findAllReviews();
    }

    @GetMapping("/api/reviews/{reviewId}/author")
    public String GetUsernameOfAuthor(@PathVariable("reviewId") int reviewId) {
        Review r = this.service.findReviewById(reviewId);
        return r.getWrittenBy().getUsername();
    }

    @GetMapping("/api/reviews/{reviewId}/user")
    public User GetAuthor(@PathVariable("reviewId") int reviewId) {
        Review r = this.service.findReviewById(reviewId);
        return r.getWrittenBy();
    }

    @GetMapping("/api/reviews/{reviewId}")
    public Review findReviewById(@PathVariable("reviewId") int reviewId) {
        return this.service.findReviewById(reviewId);
    }

    @GetMapping("/api/users/{username}/reviews")
    public List<Review> findAllReviewsByUser(@PathVariable("username") String username) {
        return this.service.findAllReviewsByUser(username);
    }

    @GetMapping("/api/media/{imdbID}/reviews/{username}")
    public List<Review> findAllReviewsOfMediaByUser(@PathVariable("username") String username,
                                                    @PathVariable("imdbID") String imdbID) {
        return this.service.findAllReviewsOfMediaByUser(username, imdbID);
    }

    @GetMapping("/api/media/{imdbID}/reviews")
    public List<Review> findAllReviewsOfMedia(@PathVariable("imdbID") String imdbID) {
        return this.service.findAllReviewsOfMedia(imdbID);
    }


    @GetMapping("/api/media/{imdbID}/reviews/{username}/following")
    public List<Review> findAllReviewsOfMediaByFollowing(@PathVariable("imdbID") String imdbID,
                                                         @PathVariable("username") String username) {
        return this.service.findAllReviewsOfMediaByFollowing(imdbID, username);
    }

    @GetMapping("/api/reviews/{reviewId}/likedBy/{username}")
    public boolean isReviewLikedByUser(@PathVariable("reviewId") int reviewId,
                                       @PathVariable("username") String username) {
        return this.service.isReviewLikedByUser(reviewId, username);
    }

    @GetMapping("/api/reviews/popular")
    public List<Review> findMostPopularReviews() {
        return this.service.findMostPopularReviews();
    }

    @GetMapping("/api/reviews/popular/{username}")
    public List<Review> findMostPopularReviewsByFollowed(@PathVariable("username") String username) {
        return this.service.findMostPopularReviewsByFollowed(username);
    }
}
