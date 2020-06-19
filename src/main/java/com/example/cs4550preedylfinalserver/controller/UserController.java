package com.example.cs4550preedylfinalserver.controller;

import com.example.cs4550preedylfinalserver.model.Media;
import com.example.cs4550preedylfinalserver.model.Review;
import com.example.cs4550preedylfinalserver.model.User;
import com.example.cs4550preedylfinalserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/api/users/{username}/{password}")
    public User createUser(@PathVariable("username") String username,
                           @PathVariable("password") String password,
                           @RequestBody User newUser) {
        newUser.setUsername(username);
        newUser.setPassword(password);
        return service.createUser(newUser);
    }

    @GetMapping("/api/users/{username}/credentials/{password}")
    public boolean checkUserCredentials(@PathVariable("username") String username,
                                        @PathVariable("password") String password) {
        return service.checkUserCredentials(username, password);
    }

    @GetMapping("/api/users/{username}/onWatchlist/{imdbID}")
    public boolean isMediaOnWatchlist(@PathVariable("username") String username,
                                      @PathVariable("imdbID") String imdbID) {
        return service.isMediaOnWatchlist(username, imdbID);
    }

    @GetMapping("/api/users/{username}/isWatched/{imdbID}")
    public boolean isMediaWatched(@PathVariable("username") String username,
                                  @PathVariable("imdbID") String imdbID) {
        return service.isMediaWatched(username, imdbID);
    }

    @GetMapping("/api/users/{username}")
    public User findUserByUsername(@PathVariable("username") String username) {
        return service.findUserByUsername(username);
    }

    @GetMapping("/api/users")
    public List<User> findAllUsers() {
        return service.findAllUsers();
    }

    @GetMapping("/api/users/{username}/watchlist")
    public List<Media> findMediaOnWatchlist(@PathVariable("username") String username) {
        return service.findMediaOnWatchlist(username);
    }

    @GetMapping("/api/users/{username}/watched")
    public List<Media> findWatchedMedia(@PathVariable("username") String username) {
        return service.findWatchedMedia(username);
    }

    @GetMapping("/api/users/{username}/followedBy")
    public List<User> findFollowedBy(@PathVariable("username") String username) {
        return service.findFollowedBy(username);
    }

    @GetMapping("/api/users/{username}/isFollowing/{other}")
    public boolean isXfollowingY(@PathVariable("username") String username,
                                 @PathVariable("other") String other) {
        return service.isXfollowingY(username, other);
    }

    @GetMapping("/api/users/{username}/likedReviews")
    public List<Review> findLikedReviews(@PathVariable("username") String username) {
        return service.findLikedReviews(username);
    }

    @GetMapping("/api/users/{username}/follows")
    public List<User> findFollows(@PathVariable("username") String username) {
        return service.findFollows(username);
    }

    @GetMapping("/api/users/popular")
    public List<User> findMostPopularUsers() {
        return service.findMostPopularUsers();
    }

    @DeleteMapping("/api/users/{username}")
    public int deleteUser(@PathVariable("username") String username) {
        return service.deleteUser(username);
    }


    @PutMapping("/api/users/{username}/{oldPassword}/{newPassword}")
    public int updateUser(@PathVariable("username") String username,
                          @PathVariable("oldPassword") String oldPassword,
                          @PathVariable("newPassword") String newPassword,
                          @RequestBody User updatedUser) {
        return service.updateUser(username, oldPassword, newPassword, updatedUser);
    }

    @PutMapping("/api/users/{username}/grantAdmin/{granterUsername}/{granterPassword}")
    public int grantAdminPrivileges(@PathVariable("username") String username,
                                    @PathVariable("granterUsername") String granterUsername,
                                    @PathVariable("granterPassword") String granterPassword) {
        return service.grantAdminPrivileges(username, granterUsername, granterPassword);
    }


    @PutMapping("/api/users/{username}/watchlist/{imdbID}")
    public int addToWatchlist(@PathVariable("username") String username,
                              @PathVariable("imdbID") String imdbID) {
        return service.addToWatchlist(username, imdbID);
    }

    @PutMapping("/api/users/{username}/watchlist/{imdbID}/remove")
    public int removeFromWatchlist(@PathVariable("username") String username,
                                   @PathVariable("imdbID") String imdbID) {
        return service.removeFromWatchlist(username, imdbID);
    }

    @PutMapping("/api/users/{username}/watched/{imdbID}")
    public int addToWatched(@PathVariable("username") String username,
                            @PathVariable("imdbID") String imdbID) {
        int result = service.addToWatched(username, imdbID);
        try {service.removeFromWatchlist(username, imdbID);} catch(Exception ignored) {}
        return result;
    }

    @PutMapping("/api/users/{username}/follow/{following}")
    public int followUser(@PathVariable("username") String username,
                          @PathVariable("following") String following) {
        return service.followUser(username, following);
    }

    @PutMapping("/api/users/{username}/unFollow/{unFollowing}")
    public int unFollowUser(@PathVariable("username") String username,
                            @PathVariable("unFollowing") String unFollowing) {
        return service.unFollowUser(username, unFollowing);
    }
}
