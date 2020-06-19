package com.example.cs4550preedylfinalserver.service;

import com.example.cs4550preedylfinalserver.model.Media;
import com.example.cs4550preedylfinalserver.model.Review;
import com.example.cs4550preedylfinalserver.model.User;
import com.example.cs4550preedylfinalserver.repository.MediaRepository;
import com.example.cs4550preedylfinalserver.repository.ReviewRepository;
import com.example.cs4550preedylfinalserver.repository.UserRepository;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    UserRepository userRepository;

    public Review createReview(String username, String imdbID, Review newReview) {
        User u = this.userRepository.findUserByUsername(username);
        Media m = this.mediaRepository.findMediaByImdbID(imdbID);
        if (u.getWatchlist().contains(m))
            u.removeFromWatchlist(m);
        newReview.setWrittenBy(u);
        newReview.setReviewing(m);
        u.addReview(newReview);
        m.addReview(newReview);
        u.addToWatched(m);
        this.reviewRepository.save(newReview);
        this.mediaRepository.save(m);
        this.userRepository.save(u);

        return newReview;
    }

    public int deleteReview(int reviewId) {
        Review r = this.reviewRepository.findReviewById(reviewId);
        User author = r.getWrittenBy();
        Media m = r.getReviewing();
        for (User u : r.getLikedBy()) {
            u.removeLikedReviews(r);
            userRepository.save(u);
        }
        author.deleteReview(r);
        m.deleteReview(r);
        this.reviewRepository.delete(r);
        this.userRepository.save(author);
        this.mediaRepository.save(m);
        return 1;
    }

    public int updateReview(String username, String imdbID, Review updatedReview, int reviewId) {
        Review oldReview = this.reviewRepository.findReviewById(reviewId);
        User u = this.userRepository.findUserByUsername(username);
        Media m = this.mediaRepository.findMediaByImdbID(imdbID);
        updatedReview.setId(reviewId);
        updatedReview.setWrittenBy(u);
        updatedReview.setReviewing(m);
        updatedReview.setEdited(true);
        u.updateReview(oldReview, updatedReview);
        m.updateReview(oldReview, updatedReview);
        this.reviewRepository.save(updatedReview);
        this.userRepository.save(u);
        this.mediaRepository.save(m);
        return 1;
    }

    public Review findReviewById(int reviewId) {
        return reviewRepository.findReviewById(reviewId);
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findAllReviews();
    }

    public List<Review> findAllReviewsByUser(String username) {
        List<Review> result = reviewRepository.findAllReviewsByUser(username);
        result.sort(new SortByLikes());
        return result;
    }

    public List<Review> findAllReviewsOfMediaByUser(String username, String imdbID) {
        List<Review> result = reviewRepository.findAllReviewsOfMediaByUser(username, imdbID);
        result.sort(new SortByLikes());
        return result;
    }

    public List<Review> findAllReviewsOfMediaByFollowing(String imdbID, String username) {
        List<User> following = this.userRepository.findUserByUsername(username).getFollows();
        List<Review> result = new ArrayList<>();
        for (User u : following) {
            result.addAll(this.reviewRepository.findAllReviewsOfMediaByUser(u.getUsername(), imdbID));
        }
        result.sort(new SortByLikes());
        return result;
    }

    public int likeReview(int reviewId, String username) {
        Review r = reviewRepository.findReviewById(reviewId);
        User u = userRepository.findUserByUsername(username);
        if (r.getLikedBy().contains(u))
            return 0;
        r.addLikedBy(u);
        u.addLikedReviews(r);
        reviewRepository.save(r);
        userRepository.save(u);
        return 1;
    }

    public int unLikeReview(int reviewId, String username) {
        Review r = reviewRepository.findReviewById(reviewId);
        User u = userRepository.findUserByUsername(username);
        if (!r.getLikedBy().contains(u))
            return 0;
        r.removeLikedBy(u);
        u.removeLikedReviews(r);
        reviewRepository.save(r);
        userRepository.save(u);
        return 1;
    }

    public List<Review> findAllReviewsOfMedia(String imdbID) {
        List<Review> result = reviewRepository.findAllReviewsOfMedia(imdbID);
        result.sort(new SortByLikes());
        return result;
    }

    public boolean isReviewLikedByUser(int reviewId, String username) {
        Review r = reviewRepository.findReviewById(reviewId);
        User u = userRepository.findUserByUsername(username);
        return u.getLikedReviews().contains(r);
    }

    public List<Review> findMostPopularReviews() {
        List<Review> result = reviewRepository.findAllReviews();
        result.sort(new SortByLikes());
        try {
            return result.subList(0, 10);
        } catch(IndexOutOfBoundsException e) {
            return result;
        }
    }

    public List<Review> findMostPopularReviewsByFollowed(String username) {
        User user = userRepository.findUserByUsername(username);
        List<Review> result = new ArrayList<>();
        for(User u : user.getFollows()) {
            result.addAll(u.getReviews());
        }
        result.sort(new SortByLikes());
        try {
            return result.subList(0, 10);
        } catch(IndexOutOfBoundsException e) {
            return result;
        }
    }
}

class SortByLikes implements Comparator<Review> {
    @Override
    public int compare(Review r1, Review r2) {
        return Integer.compare(r2.getLikedBy().size(), r1.getLikedBy().size());
    }
}
