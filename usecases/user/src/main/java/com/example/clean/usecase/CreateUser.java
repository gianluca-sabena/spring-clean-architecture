package com.example.clean.usecase;

import java.util.UUID;

import com.example.clean.entities.user.User;
import com.example.clean.usecase.exception.UserAlreadyExistsException;
//import com.example.clean.usecase.port.IdGenerator;
//import com.example.clean.usecase.port.PasswordEncoder;
import com.example.clean.usecase.port.UserRepository;
import com.example.clean.usecase.validator.UserValidator;

public final class CreateUser {

    private final UserRepository repository;

    public CreateUser(final UserRepository repository) {
        this.repository = repository;
    }

    public User create(final User user) {
        UserValidator.validateCreateUser(user);
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        var userToSave = User.builder()
            .id(UUID.randomUUID().toString())
            .email(user.getEmail())
            .password(user.getPassword())
            .lastName(user.getLastName())
            .firstName(user.getFirstName())
            .build();
        return repository.create(userToSave);
    }
}
