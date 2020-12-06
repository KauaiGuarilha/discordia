package com.discordia.model.service;

import com.discordia.model.entity.User;
import com.discordia.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired private UserRepository repository;

    public User saveUser(User use){
        try {
            User userSave = repository.save(use);
            return userSave;
        } catch (Exception e) {
            throw new RuntimeException("Could not save user.");
        }
    }

    public User returnUser(String userName){
        try {
            User user = repository.findByUser(userName);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Could not return user.");
        }
    }

    public List<User> returnListUser(){
        return repository.findAll();
    }

    public void deleteUser(String id){
        repository.deleteById(UUID.fromString(id));
    }

    public User update(User user, String id){
        Optional<User> optional = repository.findById(UUID.fromString(id));

        if (optional.isPresent()){
            User db = optional.get();
            db.setName(user.getName());
            db.setPassword(user.getPassword());
            db.setMobileToken(user.getMobileToken());

            return repository.save(db);
        }
        return null;
    }
}
