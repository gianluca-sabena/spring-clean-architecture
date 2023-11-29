package com.example.clean.restservice;

import com.example.clean.adapter.DbUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.clean.entities.user.User;
import com.example.clean.usecase.CreateUser;
import com.example.clean.usecase.FindUser;

@RestController
public class SpringUserController {
    @Autowired
    private DbUserRepository dbUserRepository;

    @Autowired
    public SpringUserController(final DbUserRepository dbUserRepositoryJdbc) {
        this.dbUserRepository = dbUserRepositoryJdbc;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User createUser(@RequestBody final User user) {
        CreateUser x = new CreateUser(dbUserRepository);
        return x.create(user);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable("userId") final String userId) {
        FindUser x = new FindUser(dbUserRepository);
        return x.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUsers() {
        FindUser x = new FindUser(dbUserRepository);
        return x.findAllUsers();
    }
}
