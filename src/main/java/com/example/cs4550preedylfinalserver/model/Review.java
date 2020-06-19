package com.example.cs4550preedylfinalserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnore
    private User writtenBy;

    @ManyToOne
    private Media reviewing;

    @ManyToMany(mappedBy="likedReviews")
    private List<User> likedBy;

    @Column(length=4096)
    private String text;

    private int score;

    private Date dateWritten;

    private boolean edited;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Media getReviewing() {
        return reviewing;
    }

    public void setReviewing(Media reviewing) {
        this.reviewing = reviewing;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDateWritten() {
        return dateWritten;
    }

    public void setDateWritten(Date dateWritten) {
        this.dateWritten = dateWritten;
    }

    public User getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(User writtenBy) {
        this.writtenBy = writtenBy;
    }

    public List<User> getLikedBy() {
        return likedBy;
    }

    public void addLikedBy(User u) {
        this.likedBy.add(u);
    }

    public void removeLikedBy(User u) {
        this.likedBy.remove(u);
    }

    public void setLikedBy(List<User> likedBy) {
        this.likedBy = likedBy;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
