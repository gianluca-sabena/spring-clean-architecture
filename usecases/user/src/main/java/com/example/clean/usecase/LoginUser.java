package com.example.clean.usecase;

import com.example.clean.entities.user.User;
import com.example.clean.usecase.exception.NotAllowedException;
import com.example.clean.usecase.port.UserRepository;

public final class LoginUser {

    private final UserRepository userRepository;

    public LoginUser(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(final String email, final String password) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new NotAllowedException("Not allowed"));
        var hashedPassword = (email + password);
        if (!user.getPassword().equals(hashedPassword)) throw new NotAllowedException("Not allowed");
        return user;
    }
}
