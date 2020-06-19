package com.example.cs4550preedylfinalserver.repository;

import com.example.cs4550preedylfinalserver.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    @Query("SELECT user FROM User user")
    public List<User> findAllUsers();

    @Query("SELECT user FROM User user WHERE user.username=:username")
    public User findUserByUsername(@Param("username") String username);
}
