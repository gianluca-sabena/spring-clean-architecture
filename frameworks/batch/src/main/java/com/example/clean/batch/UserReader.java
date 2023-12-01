package com.example.clean.batch;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.clean.adapter.DbUserRepository;
import com.example.clean.usecase.FindUser;
import com.example.clean.usecase.port.UserRepository;
import com.example.clean.entities.user.User;

@Component
public class UserReader implements ItemReader<User> {
    @Autowired
	private DbUserRepository dbUserRepository;

    //private final DbUserRepository userRepository = new DbUserRepository();
    private final FindUser findUser;
    List<User> items;

    public UserReader(DbUserRepository dbUserRepository) {
        this.dbUserRepository = dbUserRepository;
        this.findUser = new FindUser(dbUserRepository);
        this.items = findUser.findAllUsers();
    }

    public User read() throws Exception, UnexpectedInputException,
       NonTransientResourceException, ParseException {

        if (!items.isEmpty()) {
            return items.remove(0);
        }
        return null;
    }
}