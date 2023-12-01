package com.example.clean.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.clean.entities.User;
import com.example.clean.usecase.exception.UserAlreadyExistsException;
import com.example.clean.usecase.exception.UserValidationException;
import com.example.clean.usecase.port.UserRepository;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class UserUseCase {

    private final UserRepository repository;

    public UserUseCase(final UserRepository repository) {
        this.repository = repository;
    }

    public User create(final User user) {
        this.validateCreateUser(user);
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        var userToSave = User.builder()
            .id(UUID.randomUUID().toString())
            .email(user.getEmail())
            .lastName(user.getLastName())
            .firstName(user.getFirstName())
            .build();
        return repository.create(userToSave);
    }

    public Optional<User> findById(final String id) {
        return repository.findById(id);
    }

    public List<User> findAllUsers() {
        return repository.findAllUsers();
    }

    private void validateCreateUser(final User user) {
        if (user == null) throw new UserValidationException("User should not be null");
        if (isBlank(user.getEmail())) throw new UserValidationException("Email should not be null");
        if (isBlank(user.getFirstName())) throw new UserValidationException("First name should not be null");
        if (isBlank(user.getLastName())) throw new UserValidationException("Last name should not be null");
    }
}
