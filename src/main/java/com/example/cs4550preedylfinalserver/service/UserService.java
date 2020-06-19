package com.example.cs4550preedylfinalserver.service;

import com.example.cs4550preedylfinalserver.model.Media;
import com.example.cs4550preedylfinalserver.model.Review;
import com.example.cs4550preedylfinalserver.model.User;
import com.example.cs4550preedylfinalserver.repository.MediaRepository;
import com.example.cs4550preedylfinalserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaRepository mediaRepository;

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public boolean checkUserCredentials(String username, String password) {
        User u;
        try {
            u = userRepository.findUserByUsername(username);
        } catch (Exception e) {     // If you can't find the user with the given username, return false.
            return false;
        }
        return password.compareTo(u.getPassword()) == 0;
    }

    public boolean isMediaOnWatchlist(String username, String imdbID) {
        User u = userRepository.findUserByUsername(username);
        Media m = mediaRepository.findMediaByImdbID(imdbID);
        return u.getWatchlist().contains(m);
    }

    public boolean isMediaWatched(String username, String imdbID) {
        User u = userRepository.findUserByUsername(username);
        Media m = mediaRepository.findMediaByImdbID(imdbID);
        return u.getWatched().contains(m);
    }

    public int deleteUser(String username) {
        User u = userRepository.findUserByUsername(username);
        userRepository.delete(u);
        return 1;
    }

    public int updateUser(String username, String oldPassword, String newPassword, User updatedUser) {
        if (this.checkUserCredentials(username, oldPassword)) {
            updatedUser.setUsername(username);
            updatedUser.setPassword(newPassword);
            userRepository.save(updatedUser);
            return 1;
        } else return 0;
    }

    public int grantAdminPrivileges(String username, String granterUsername, String granterPassword) {
        User granter = userRepository.findUserByUsername(granterUsername);
        User u = userRepository.findUserByUsername(username);
        if (this.checkUserCredentials(granterUsername, granterPassword) && granter.isAdmin()) {
            u.setAdmin(true);
            userRepository.save(u);
            return 1;
        }
        return 0;
    }

    public int addToWatchlist(String username, String imdbID) {
        User u = userRepository.findUserByUsername(username);
        Media m = mediaRepository.findMediaByImdbID(imdbID);
        if (!u.getWatchlist().contains(m)) {
            u.addToWatchlist(m);
            m.addToOnWatchlist(u);
            userRepository.save(u);
            mediaRepository.save(m);
        }
        return 1;
    }

    public int removeFromWatchlist(String username, String imdbID) {
        User u = userRepository.findUserByUsername(username);
        Media m = mediaRepository.findMediaByImdbID(imdbID);
        if (u.getWatchlist().contains(m)) {
            u.removeFromWatchlist(m);
            m.removeFromOnWatchlist(u);
            userRepository.save(u);
            mediaRepository.save(m);
        }
        return 1;
    }

    public int addToWatched(String username, String imdbID) {
        User u = userRepository.findUserByUsername(username);
        Media m = mediaRepository.findMediaByImdbID(imdbID);
        if (!u.getWatched().contains(m)) {
            u.addToWatched(m);
            m.addToWatchedBy(u);
            userRepository.save(u);
            mediaRepository.save(m);
        }
        return 1;
    }

    public List<Media> findMediaOnWatchlist(String username) {
        return userRepository.findUserByUsername(username).getWatchlist();
    }

    public List<User> findFollows(String username) {
        return userRepository.findUserByUsername(username).getFollows();
    }

    public List<Media> findWatchedMedia(String username) {
        return userRepository.findUserByUsername(username).getWatched();
    }

    public List<User> findFollowedBy(String username) {
        return userRepository.findUserByUsername(username).getFollowedBy();
    }

    public List<Review> findLikedReviews(String username) {
        return userRepository.findUserByUsername(username).getLikedReviews();
    }

    public int followUser(String username, String following) {
        User u = userRepository.findUserByUsername(username);
        User f = userRepository.findUserByUsername(following);
        u.addFollows(f);
        f.addFollowedBy(u);
        userRepository.save(u);
        userRepository.save(f);
        return 1;
    }

    public int unFollowUser(String username, String unFollowing) {
        User u = userRepository.findUserByUsername(username);
        User f = userRepository.findUserByUsername(unFollowing);
        u.removeFollows(f);
        f.removeFollowedBy(u);
        userRepository.save(u);
        userRepository.save(f);
        return 1;
    }

    public boolean isXfollowingY(String username, String other) {
        User x = userRepository.findUserByUsername(username);
        User y = userRepository.findUserByUsername(other);
        return x.getFollows().contains(y);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    // Limits to the top 10 most popular
    public List<User> findMostPopularUsers() {
        List<User> result = userRepository.findAllUsers();
        result.sort(new SortByPopularity());
        try {
            return result.subList(0, 10);
        } catch (IndexOutOfBoundsException e) {
            return result;
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
}

class SortByPopularity implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        return Integer.compare(u2.getFollowedBy().size(), u1.getFollowedBy().size());
    }
}