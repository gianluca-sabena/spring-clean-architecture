package com.example.clean.adapter;

import com.example.clean.entities.User;
import com.example.clean.usecase.port.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class DbUserRepository implements UserRepository {
    Logger logger = LoggerFactory.getLogger(DbUserRepository.class);

    private JdbcTemplate jdbcTemplate;

    private final Map<String, User> inMemoryDb = new HashMap<>();

    public DbUserRepository(JdbcTemplate jdbcTemplate) {
        logger.info("-------> DbUserRepository JDBC - {}", jdbcTemplate.toString());
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return inMemoryDb.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public User create(final User user) {
        logger.info("JDBC adapter - Create user: {}", user.getId());
        jdbcTemplate.update("INSERT INTO person(id, email, lastName, firstName) VALUES (?,?,?,?)", user.getId(), user.getEmail(), user.getLastName(), user.getFirstName());
        //inMemoryDb.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(final String id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query(
                "SELECT id, email, lastName, firstName FROM person",
                (rs, rowNum) -> User.builder().id(rs.getString("id")).email(rs.getString("email"))
                        .lastName(rs.getString("lastName"))
                        .firstName(rs.getString("firstName")).build());
    }
}
