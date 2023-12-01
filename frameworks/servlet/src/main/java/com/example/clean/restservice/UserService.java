package com.example.clean.restservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.clean.adapter.DbUserRepository;
import com.example.clean.entities.User;
import com.example.clean.usecase.UserUseCase;

@Service
public class UserService {
    private UserUseCase userUseCase;

    public UserService(DbUserRepository dbUserRepository) {
        this.userUseCase = new UserUseCase(dbUserRepository);
    }
    public User create(User user){
        return userUseCase.create(user);

    }
    public User getUserById(String userId){
        return userUseCase.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public List<User> allUsers() {
        return new ArrayList<>(userUseCase.findAllUsers());
    }
}
