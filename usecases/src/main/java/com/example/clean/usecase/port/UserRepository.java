package com.example.clean.usecase.port;

import java.util.List;
import java.util.Optional;

import com.example.clean.entities.User;

public interface UserRepository {

    User create(User user);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    List<User> findAllUsers();
}
