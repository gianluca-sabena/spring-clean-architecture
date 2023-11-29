package com.example.clean.adapter;

import com.example.clean.entities.user.User;
import com.example.clean.usecase.port.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DbUserRepository implements UserRepository {
    Logger logger = LoggerFactory.getLogger(DbUserRepository.class);
    private final Map<String, User> inMemoryDb = new HashMap<>();

    public DbUserRepository() {
        logger.info("-------> DbUserRepository MEMORY");
    }

    @Override
    public User create(final User user) {
        inMemoryDb.put(user.getId(), user);
        return user;
    }
    @Override
    public Optional<User> findByEmail(final String email) {
        return inMemoryDb.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }
    @Override
    public Optional<User> findById(final String id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(inMemoryDb.values());
    }
}
