package com.example.cs4550preedylfinalserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(unique=true)
    private String username;

    @JsonIgnore // Don't want to send passwords
    private String password;

    private boolean isAdmin;

    @JsonIgnore
    @ManyToMany(mappedBy="followedBy")
    private List<User> follows;
    // Need to JsonIgnore both of these, otherwise if two users follow each other
    // it creates an infinite loop.
    @JsonIgnore
    @ManyToMany
    private List<User> followedBy;

    @OneToMany(mappedBy="writtenBy")
    @JsonIgnore
    private List<Review> reviews;

    @ManyToMany
    @JsonIgnore
    private List<Review> likedReviews;

    public void addReview(Review newReview) {
        this.reviews.add(newReview);
    }

    public void deleteReview(Review deletedReview) {
        this.reviews.remove(deletedReview);
    }

    public void updateReview(Review oldReview, Review updatedReview) {
        this.reviews.remove(oldReview);
        this.reviews.add(updatedReview);
    }

    @ManyToMany(mappedBy = "onWatchlist")
    private List<Media> watchlist;

    @ManyToMany(mappedBy = "watchedBy")
    private List<Media> watched;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Media> getWatchlist() {
        return watchlist;
    }

    public void addToWatchlist(Media m) {
        this.watchlist.add(m);
    }

    public void removeFromWatchlist(Media m) {
        this.watchlist.remove(m);
    }

    public void setWatchlist(List<Media> watchlist) {
        this.watchlist = watchlist;
    }

    public List<Media> getWatched() {
        return watched;
    }

    public void addToWatched(Media m) {
        this.watched.add(m);
    }

    public void setWatched(List<Media> watched) {
        this.watched = watched;
    }

    public List<User> getFollows() {
        return follows;
    }

    public void addFollows(User u) {
        this.follows.add(u);
    }

    public void removeFollows(User u) {
        this.follows.remove(u);
    }

    public void setFollows(List<User> follows) {
        this.follows = follows;
    }

    public List<User> getFollowedBy() {
        return followedBy;
    }

    public void addFollowedBy(User u) {
        this.followedBy.add(u);
    }

    public void removeFollowedBy(User u) {
        this.followedBy.remove(u);
    }

    public void setFollowedBy(List<User> followedBy) {
        this.followedBy = followedBy;
    }

    public List<Review> getLikedReviews() {
        return likedReviews;
    }

    public void addLikedReviews(Review r) {
        this.likedReviews.add(r);
    }

    public void removeLikedReviews(Review r) {
        this.likedReviews.remove(r);
    }

    public void setLikedReviews(List<Review> likedReviews) {
        this.likedReviews = likedReviews;
    }
}
