package com.example.cs4550preedylfinalserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "media")
@Inheritance(strategy = InheritanceType.JOINED)
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String imdb;

    @OneToMany(mappedBy = "reviewing")
    @JsonIgnore
    private List<Review> reviews;

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

    @ManyToMany
    @JsonIgnore
    @JoinTable(name="media_on_watchlist",
            joinColumns=@JoinColumn(name="watchlist_id"),
            inverseJoinColumns=@JoinColumn(name="on_watchlist_username"))
    private List<User> onWatchlist;

    @ManyToMany
    @JsonIgnore
    private List<User> watchedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<User> getOnWatchlist() {
        return onWatchlist;
    }

    public void addToOnWatchlist(User u) {
        this.onWatchlist.add(u);
    }

    public void removeFromOnWatchlist(User u) {
        this.onWatchlist.remove(u);
    }

    public void setOnWatchlist(List<User> onWatchlist) {
        this.onWatchlist = onWatchlist;
    }

    public List<User> getWatchedBy() {
        return watchedBy;
    }

    public void addToWatchedBy(User u) {
        this.watchedBy.add(u);
    }

    public void setWatchedBy(List<User> watchedBy) {
        this.watchedBy = watchedBy;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }
}
